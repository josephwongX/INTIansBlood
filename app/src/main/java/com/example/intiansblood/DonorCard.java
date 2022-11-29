package com.example.intiansblood;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class DonorCard extends AppCompatActivity {
    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    Toolbar toolbar16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_card);
        toolbar16=(Toolbar) findViewById(R.id.toolbar16);
        setSupportActionBar(toolbar16);
        getSupportActionBar().setTitle("Donor Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardView = findViewById(R.id.donorCard);
        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);
        arrow.setOnClickListener(view -> {


            if (hiddenView.getVisibility() == View.VISIBLE) {


                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_forward);
            }


            else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.arrow_forward);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }
}