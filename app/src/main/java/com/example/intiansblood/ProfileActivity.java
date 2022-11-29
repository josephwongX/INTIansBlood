package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar13;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AccModal accModal=new AccModal();
        toolbar13=(Toolbar) findViewById(R.id.toolbar13);
        setSupportActionBar(toolbar13);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView userName=findViewById(R.id.userName);
        TextView userBloodType=findViewById(R.id.userBloodType);
        TextView goProfileDet=findViewById(R.id.goProfileDet);
        CircleImageView userProfilePic=findViewById(R.id.userProfilePic);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String currentUserId=mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(currentUserId).child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    DataSnapshot dataSnapshot=task.getResult();
                    String username=String.valueOf(dataSnapshot.child("fullName").getValue());
                    String bloodtpye=String.valueOf(dataSnapshot.child("bloodgroup").getValue());
                    String profileImgUrl=String.valueOf(dataSnapshot.child("profileImgUrl").getValue());

                    userName.setText(username);
                    userBloodType.setText(bloodtpye);
                    Picasso.get().load(profileImgUrl).into(userProfilePic);
                }
            }
        });
        goProfileDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this,"", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}