package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends Fragment {
    FloatingActionButton fab;
    ActionBar actionBar;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView hGPB;
    private FirebaseAuth mAuth;
    private ArrayList<EventModal> eventModalArrayList;
    private EventAdapter eventAdapter;
    private RelativeLayout homeCL;
    Toolbar toolbar08;

    public HomeActivity(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home,container,false);
        fab=(FloatingActionButton) view.findViewById(R.id.fab01);
        toolbar08=(Toolbar) view.findViewById(R.id.toolbar08);
        toolbar08.setTitle("Home");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),addEventActivity.class);
                startActivity(intent);
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FirebaseApp.initializeApp(/*context*/getActivity().getApplicationContext());
        /*FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());*/
        // initializing all our variables
        recyclerView = (RecyclerView) view.findViewById(R.id.idRV01);
        hGPB=(ImageView)view.findViewById(R.id.homeGoProfileBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        eventModalArrayList = new ArrayList<>();
        // on below line we are getting database reference
        databaseReference = firebaseDatabase.getReference("event");

        // on below line initializing our adapter class.
        eventAdapter = new EventAdapter(eventModalArrayList, getActivity().getApplicationContext(), this::onEventClick);
        // setting layout malinger to recycler view on below line
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //setting adapter to recycler view on below line
        recyclerView.setAdapter(eventAdapter);
        // on below line calling a method to fetch food from database
        getEvents();
        hGPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ProfileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getEvents(){
        // on below line clearing our list
        eventModalArrayList.clear();
        // on below line we are calling add child event listener method to read the data
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // adding snapshot to our array list on below line
                eventModalArrayList.add(snapshot.getValue(EventModal.class));
                // notifying our adapter that data has changed
                eventAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onEventClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(eventModalArrayList.get(position));
    }

    private void displayBottomSheet(EventModal eventModal) {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(getActivity(), R.style.Theme_INTIansBlood);
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, homeCL);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();
        TextView eventName=layout.findViewById(R.id.idEventName);
        TextView eventLoc=layout.findViewById(R.id.idEventLoc);
        TextView eventOrg=layout.findViewById(R.id.idEventOrg);
        TextView eventDate=layout.findViewById(R.id.idEventDate);
        ImageView eventImg=layout.findViewById(R.id.idEventImg);


        eventName.setText(eventModal.getEventName());
        eventLoc.setText(eventModal.getEventLocation());
        eventOrg.setText(eventModal.getEventOrg());
        eventDate.setText(eventModal.getEventDate());
        Picasso.get().load(eventModal.getProfileUrl()).into(eventImg);

        Button editBut=layout.findViewById(R.id.idBtnEdit);
        Button viewDetBut=layout.findViewById(R.id.idBtnViewDetails);

        viewDetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(eventModal.getProfileUrl()));
                startActivity(i);
            }
        });
        String currentUserId=mAuth.getCurrentUser().getUid();
        String checkName=eventModal.getEventName();
        String checkID=eventModal.getUserID();

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkID.equals(currentUserId)){
                    Intent i = new Intent(getActivity(), editEventActivity.class);
                    i.putExtra("event", eventModal);
                    startActivity(i);
                }else {
                    Toast.makeText(getActivity(),"You don't have the credential to edit this" , Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(),checkID , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}