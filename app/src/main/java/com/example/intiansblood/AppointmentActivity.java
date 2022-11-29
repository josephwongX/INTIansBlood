package com.example.intiansblood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class AppointmentActivity extends Fragment {
    Toolbar toolbar09;
    Button makeAppointmentBtn;
    public AppointmentActivity(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_appointment,container,false);
        toolbar09=(Toolbar) view.findViewById(R.id.toolbar09);
        toolbar09.setTitle("Appointment");
        makeAppointmentBtn=(Button) view.findViewById(R.id.makeAppointmentBtn);
        makeAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MakeAppointmentActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}