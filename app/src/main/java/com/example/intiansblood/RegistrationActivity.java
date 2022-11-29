package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    private TextView backButton;
    private TextInputEditText registerFullName,registerEmail,registerPhoneNumber,registerPassword;
    private Spinner spinner;
    private Button registerButton;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private Uri resultUri;
    private CircleImageView profile_image;
    CheckBox tac01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerFullName=findViewById(R.id.registerFullName);
        registerEmail=findViewById(R.id.registerEmail);
        registerPhoneNumber=findViewById(R.id.registerPhoneNumber);
        registerPassword=findViewById(R.id.registerPassword);
        spinner=findViewById(R.id.bloodGroup);
        registerButton=findViewById(R.id.registerButton);
        profile_image=findViewById(R.id.profile_image);
        tac01=findViewById(R.id.tac01);
        loader=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        Boolean isCheck= false;
        if (tac01.isChecked()){
            isCheck=true;
        }

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        Boolean finalIsCheck = isCheck;
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=registerEmail.getText().toString().trim();
                String password=registerPassword.getText().toString().trim();
                String fullName=registerFullName.getText().toString().trim();
                String phoneNumber=registerPhoneNumber.getText().toString().trim();
                String bloodGroup=spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(email)){
                    registerEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    registerPassword.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(fullName)){
                    registerFullName.setError("Full Name is required");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)){
                    registerPhoneNumber.setError("Phone Number is required");
                    return;
                }
                if (bloodGroup.equals("Select Your Blood Group")){
                    Toast.makeText(RegistrationActivity.this,"Select Your Blood Group",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (finalIsCheck){
                    Toast.makeText(RegistrationActivity.this,"Agreement is not checked",Toast.LENGTH_LONG).show();
                }
                else {
                    loader.setMessage("Creating an account for you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                String error=task.getException().toString();
                                Toast.makeText(RegistrationActivity.this,"Error :"+error,Toast.LENGTH_LONG).show();
                            }else {
                                String currentUserId=mAuth.getCurrentUser().getUid();

                                if (resultUri==null){
                                    AccModal accModal=new AccModal(fullName,email,phoneNumber,bloodGroup);
                                    userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                    userDatabaseRef.child("users").setValue(accModal);
                                    Toast.makeText(RegistrationActivity.this,"Created Successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                    finish();
                                }

                                if (resultUri!=null){
                                    StorageReference filePath= FirebaseStorage.getInstance().getReference().child("profile images").child(currentUserId);
                                    Bitmap bitmap=null;
                                    try {
                                        bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
                                    byte[] data=byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask=filePath.putBytes(data);
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if (taskSnapshot.getMetadata()!=null && taskSnapshot.getMetadata().getReference()!=null){
                                                Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl=uri.toString();
                                                        AccModal accModal=new AccModal(fullName,email,phoneNumber,bloodGroup,imageUrl);
                                                        userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                                        userDatabaseRef.child("users").setValue(accModal);
                                                        Toast.makeText(RegistrationActivity.this,"Created Successfully",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }


                                /*FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();//userDatabaseRef.setValue(currentUserId);

                                HashMap userInfo=new HashMap();
                                userInfo.put("id",currentUserId);
                                userInfo.put("name",fullName);
                                userInfo.put("email",email);
                                userInfo.put("phonenumber",phoneNumber);
                                userInfo.put("bloodgroup",spinner);*/

                                /*userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegistrationActivity.this,"Created Successfully",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(RegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                        //finish();
                                        //loader.dismiss();
                                    }
                                });
                                if (resultUri!=null){
                                    final StorageReference filePath= FirebaseStorage.getInstance().getReference().child("profile images").child(currentUserId);
                                    Bitmap bitmap=null;
                                    try {
                                        bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
                                    byte[] data=byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask=filePath.putBytes(data);
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if (taskSnapshot.getMetadata()!=null && taskSnapshot.getMetadata().getReference()!=null){
                                                Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl=uri.toString();
                                                        Map newImageMap= new HashMap();
                                                        newImageMap.put("profilepicturesurl",imageUrl);
                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(RegistrationActivity.this,"ImageURL Added",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                        //finish();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }*/
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null){
            resultUri=data.getData();
            profile_image.setImageURI(resultUri);
        }
    }
}