package com.example.intiansblood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private ArrayList<EventModal> eventRVModalArrayList;
    private Context context;
    private EventClickInterface eventClickInterface;
    int lastPos = -1;

    // creating a constructor
    public EventAdapter(ArrayList<EventModal> eventRVModalArrayList, Context context, EventClickInterface eventClickInterface) {
        this.eventRVModalArrayList = eventRVModalArrayList;
        this.context = context;
        this.eventClickInterface = eventClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line
        EventModal eventRVModal = eventRVModalArrayList.get(position);
        holder.eventTV.setText(eventRVModal.getEventName());
        holder.organizerTV.setText(eventRVModal.getEventOrg());

        Picasso.get().load(eventRVModal.getProfileUrl()).into(holder.eventIV);
        // adding animation to recycler view item on below line
        setAnimation(holder.itemView, position);
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_01));
        holder.eventTV.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_02));
        holder.eventIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventClickInterface.onFoodClick(position);
            }
        });


    }

    private  void setAnimation (View itemView, int position){
        if (position > lastPos){
            //on below line we are setting animation
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos=position;
        }
    }

    @Override
    public int getItemCount() {
        return eventRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //creating variable for our image view and text view on below line
        private ImageView eventIV;
        private TextView eventTV, organizerTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all out variables on below line
            eventIV = itemView.findViewById(R.id.idIV01);
            eventTV = itemView.findViewById(R.id.idTV01);
            organizerTV = itemView.findViewById(R.id.idTV02);
        }
    }

    // creating an interface for on click
    public interface  EventClickInterface{
        void onFoodClick(int Position);
    }
}
