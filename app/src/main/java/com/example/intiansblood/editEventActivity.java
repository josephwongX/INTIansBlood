package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class editEventActivity extends AppCompatActivity {
    private TextInputEditText edtEventName,edtEventLoc,edtEventOrg;
    TextView edtEventDate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EventModal eventModal;
    String eventName;
    Toolbar toolbar12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        toolbar12=findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar12);
        getSupportActionBar().setTitle("Edit Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnUpdate=findViewById(R.id.idBtnUpdate);
        Button btnDlt=findViewById(R.id.idBtnDelete);
        edtEventName=findViewById(R.id.idEdtEventName);
        edtEventLoc=findViewById(R.id.idEdtEventLoc);
        edtEventOrg=findViewById(R.id.idEdtEventOrg);
        edtEventDate=findViewById(R.id.idEdtEventDate);
        firebaseDatabase = FirebaseDatabase.getInstance();
        eventModal=getIntent().getParcelableExtra("event");

        if (eventModal!=null){
            edtEventName.setText(eventModal.getEventName());
            edtEventLoc.setText(eventModal.getEventLocation());
            edtEventOrg.setText(eventModal.getEventOrg());
            edtEventDate.setText(eventModal.getEventDate());
        }
        databaseReference = firebaseDatabase.getReference("event").child(eventName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ename=edtEventName.getText().toString().trim();
                String eloc=edtEventLoc.getText().toString().trim();
                String eorg=edtEventOrg.getText().toString().trim();
                String edate=edtEventDate.getText().toString().trim();

                Map<String, Object> map = new HashMap<>();
                map.put("eventName", ename);
                map.put("eventLocation", eloc);
                map.put("eventOrg", eorg);
                map.put("eventDate", edate);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(editEventActivity.this, "Food Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(editEventActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(editEventActivity.this, "Fail to update", Toast.LENGTH_SHORT).show();
                    }
                });
                btnDlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.removeValue();
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}