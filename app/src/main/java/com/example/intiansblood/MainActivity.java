package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView=findViewById(R.id.botNavView);
        bottomNavView.setOnNavigationItemSelectedListener(this);
        bottomNavView.setSelectedItemId(R.id.home);
    }
    HomeActivity homeActivity=new HomeActivity();
    AppointmentActivity appointmentActivity=new AppointmentActivity();
    LocationActivity locationActivity=new LocationActivity();
    MenuActivity menuActivity=new MenuActivity();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                mediaPlayer=MediaPlayer.create(MainActivity.this.getBaseContext(),R.raw.tap03);
                mediaPlayer.start();
                getSupportFragmentManager().beginTransaction().replace(R.id.FL, homeActivity).commit();
                return  true;
            case R.id.appointment:
                mediaPlayer=MediaPlayer.create(MainActivity.this.getBaseContext(),R.raw.tap01);
                mediaPlayer.start();
                getSupportFragmentManager().beginTransaction().replace(R.id.FL, appointmentActivity).commit();
                return  true;
            case R.id.location:
                getSupportFragmentManager().beginTransaction().replace(R.id.FL, locationActivity).commit();
                return  true;
            case R.id.more:
                mediaPlayer=MediaPlayer.create(MainActivity.this.getBaseContext(),R.raw.tap02);
                mediaPlayer.start();
                getSupportFragmentManager().beginTransaction().replace(R.id.FL, menuActivity).commit();
                return  true;
        }
        return false;
    }

}