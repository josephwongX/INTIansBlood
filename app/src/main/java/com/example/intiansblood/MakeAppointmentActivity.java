package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MakeAppointmentActivity extends AppCompatActivity {
    Toolbar toolbar15;
    TextInputEditText fullName,age;
    TextView appoinmentDate,appointmentTime;
    FirebaseAuth mAuth;
    ProgressDialog loader;
    DatabaseReference userDatabaseRef;
    FirebaseDatabase firebaseDatabase;
    Button btnDate,btnTime,submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        toolbar15=findViewById(R.id.toolbar15);
        setSupportActionBar(toolbar15);
        getSupportActionBar().setTitle("Make Appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fullName=findViewById(R.id.makeAppointFullName);
        age=findViewById(R.id.makeAppointAge);
        appoinmentDate=findViewById(R.id.makeAppointDate);
        btnDate=findViewById(R.id.makeAppointDateBtn);
        appointmentTime=findViewById(R.id.makeAppointTime);
        btnTime=findViewById(R.id.makeAppointTimeBtn);
        submitBtn=findViewById(R.id.submitBtn);
        loader=new ProgressDialog(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        userDatabaseRef=firebaseDatabase.getReference("appointment");
        mAuth=FirebaseAuth.getInstance();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname=fullName.getText().toString().trim();
                String yo=age.getText().toString().trim();
                String appointDate=appoinmentDate.getText().toString().trim();
                String appointTime=appointmentTime.getText().toString().trim();

                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(MakeAppointmentActivity.this, "Full Name as I.C is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(yo)){
                    Toast.makeText(MakeAppointmentActivity.this, "Age is needed to make an appointment", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(appointDate)){
                    Toast.makeText(MakeAppointmentActivity.this, "Date is needed to make an appointment", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(appointTime)){
                    Toast.makeText(MakeAppointmentActivity.this, "Time is needed to make an appointment", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    loader.setMessage("Making an appointment for you../nPlease be patient");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String currentUserId=mAuth.getCurrentUser().getUid();
                    AppointmentModal appointmentModal=new AppointmentModal(fullname,yo,appointDate,appointTime);
                    userDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            userDatabaseRef.child(currentUserId).setValue(appointmentModal);
                            Toast.makeText(MakeAppointmentActivity.this, "Appointment booked", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MakeAppointmentActivity.this,AppointmentActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MakeAppointmentActivity.this, "Fail to make appointment..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
    private void setTime(){
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String showTime=i+" : "+i1;
                appointmentTime.setText(showTime);
            }
        },hour,min,true);
        timePickerDialog.show();
    }
    private void setDate(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String showDate=dayOfMonth+" /"+(month+1)+" /"+year;
                appoinmentDate.setText(showDate);
            }
        },year,month,date);
        datePickerDialog.show();
    }
}