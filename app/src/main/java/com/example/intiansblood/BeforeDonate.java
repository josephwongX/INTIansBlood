package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class BeforeDonate extends AppCompatActivity {
    Toolbar toolbar05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_donate);
        toolbar05=findViewById(R.id.toolbar05);
        setSupportActionBar(toolbar05);
        getSupportActionBar().setTitle("Before Donate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}