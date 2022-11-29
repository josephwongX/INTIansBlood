package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;

public class TermsAndServices extends AppCompatActivity {
    Toolbar toolbar02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_services);

        toolbar02=(Toolbar) findViewById(R.id.toolbar02);
        setSupportActionBar(toolbar02);
        getSupportActionBar().setTitle("Terms of Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PDFView pdfView=findViewById(R.id.pdfView);
        pdfView.fromAsset("TermsOfService.pdf").load();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}