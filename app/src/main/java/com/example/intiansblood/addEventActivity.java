package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class addEventActivity extends AppCompatActivity {
    private Button eventDate,create;
    private TextInputEditText eventName,eventLocation,eventOrg;
    private CircleImageView eventProfile;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    DatabaseReference userDatabaseRef;
    FirebaseDatabase firebaseDatabase;
    private Uri resultUri;
    TextView eDate;
    Toolbar toolbar11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        toolbar11=(Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar11);
        getSupportActionBar().setTitle("Create new event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventDate=findViewById(R.id.eventDate);
        create=findViewById(R.id.create);
        eventName=findViewById(R.id.eventName);
        eventLocation=findViewById(R.id.eventLocation);
        eventOrg=findViewById(R.id.eventOrg);
        eventProfile=findViewById(R.id.eventprofile);
        eDate=findViewById(R.id.eDate);
        loader=new ProgressDialog(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        userDatabaseRef=firebaseDatabase.getReference("event");
        mAuth=FirebaseAuth.getInstance();

        MaterialDatePicker materialDatePicker=MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build();

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        eDate.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });

        eventProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ename=eventName.getText().toString().trim();
                String eloc=eventLocation.getText().toString().trim();
                String eorg=eventOrg.getText().toString().trim();
                String edate=eDate.getText().toString().trim();

                if (TextUtils.isEmpty(ename)){
                    eventName.setError("Event name is required");
                    return;
                }
                if (TextUtils.isEmpty(eloc)){
                    eventLocation.setError("Event Location is required");
                    return;
                }
                if (TextUtils.isEmpty(eorg)){
                    eventOrg.setError("Event Organizer is required");
                    return;
                }
                if (TextUtils.isEmpty(edate)){
                    eDate.setError("Event date is required");
                    return;
                }else {
                    loader.setMessage("Creating new event...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String currentUserId=mAuth.getCurrentUser().getUid();

                    if (resultUri==null){
                        EventModal eventModal=new EventModal(ename,eloc,eloc,edate,currentUserId);
                        //firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("event").child(currentUserId).setValue(eventModal);
                        //userDatabaseRef.child("").setValue(eventModal);
                        userDatabaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userDatabaseRef.child(ename).setValue(eventModal);
                                Toast.makeText(addEventActivity.this,"Event Created Succesfully...",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(addEventActivity.this,MainActivity.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(addEventActivity.this,"Fail to create..",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (resultUri!=null){
                        StorageReference filePath= FirebaseStorage.getInstance().getReference().child("event image").child(ename);
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
                                            EventModal eventModal=new EventModal(ename,eloc,eorg,edate,currentUserId,imageUrl);
                                            //userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                            //userDatabaseRef.child("users").setValue(accModal);
                                            //Toast.makeText(addEventActivity.this,"Created Successfully",Toast.LENGTH_SHORT).show();
                                            //startActivity(new Intent(addEventActivity.this, LoginActivity.class));
                                            userDatabaseRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    userDatabaseRef.child(ename).setValue(eventModal);
                                                    Toast.makeText(addEventActivity.this,"Event Created Succesfully...",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(addEventActivity.this,MainActivity.class));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(addEventActivity.this,"Fail to create..",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null){
            resultUri=data.getData();
            eventProfile.setImageURI(resultUri);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}