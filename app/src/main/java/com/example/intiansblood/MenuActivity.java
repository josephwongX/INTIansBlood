package com.example.intiansblood;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuActivity extends Fragment {
    ListView lv_menulist;
    ArrayList<String> list;
    ArrayAdapter<String> arrayAdapter;
    CardView beforeDonate,atTheCentre,afterDonate;
    MediaPlayer mediaPlayer;
    Toolbar toolbar10;

    public MenuActivity(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_menu,container,false);
        lv_menulist=(ListView)view.findViewById(R.id.lV01);
        beforeDonate=(CardView)view.findViewById(R.id.beforeDonate);
        atTheCentre=(CardView)view.findViewById(R.id.atTheCentre);
        afterDonate=(CardView)view.findViewById(R.id.afterDonate);
        toolbar10=(Toolbar)view.findViewById(R.id.toolbar11);
        toolbar10.setTitle("Menu");

        showMenuOnListView();
        beforeDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer=MediaPlayer.create(getActivity().getBaseContext(),R.raw.tap01);
                mediaPlayer.start();
                Intent intent=new Intent(getActivity(), BeforeDonate.class);

                startActivity(intent);
            }
        });
        atTheCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer=MediaPlayer.create(getActivity().getBaseContext(),R.raw.tap02);
                mediaPlayer.start();
                Intent intent=new Intent(getActivity(),AtTheCentre.class);
                Snackbar.make(view, "Opened At The Centre", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });
        afterDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer=MediaPlayer.create(getActivity().getBaseContext(),R.raw.tap03);
                mediaPlayer.start();
                Intent intent=new Intent(getActivity(),AfterDonate.class);
                Snackbar.make(view, "Opened After Donate", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });
        return view;
    }
    public void showMenuOnListView(){
        list= new ArrayList<String>();
        list.add("Profile");
        list.add("Donor Card");
        list.add("FAQs");
        list.add("Terms of Service");
        list.add("Privacy Policy");
        list.add("Logout");
        arrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
        lv_menulist.setAdapter(arrayAdapter);

        lv_menulist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(position==0){
                    //profile
                    startActivity(new Intent(getActivity(), ProfileActivity.class));
                }else if(position==1){
                    //donor card
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }else if (position==2){
                    //FAQs
                    startActivity(new Intent(getActivity(), FAQsActivity.class));
                }else if (position==3){
                    //Terms of Service
                    startActivity(new Intent(getActivity(), TermsAndServices.class));
                }else if (position==4){
                    //Privacy Policy
                    startActivity(new Intent(getActivity(), PrivacyPolicy.class));
                }else if (position==5){
                    //logout
                    mediaPlayer=MediaPlayer.create(getActivity().getBaseContext(),R.raw.splash);
                    mediaPlayer.start();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}