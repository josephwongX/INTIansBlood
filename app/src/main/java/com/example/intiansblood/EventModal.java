package com.example.intiansblood;

import android.os.Parcel;
import android.os.Parcelable;

public class EventModal implements Parcelable {
    private String eventName,eventLocation,eventOrg,eventDate,profileUrl,userID;

    public EventModal(){}

    public EventModal(String eventName, String eventLocation, String eventOrg, String eventDate,String userID) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventOrg = eventOrg;
        this.eventDate = eventDate;
        this.userID = userID;
    }

    public EventModal(String eventName, String eventLocation, String eventOrg, String eventDate, String userID,String profileUrl) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventOrg = eventOrg;
        this.eventDate = eventDate;
        this.userID = userID;
        this.profileUrl = profileUrl;
    }

    protected EventModal(Parcel in) {
        eventName=in.readString();
        eventLocation=in.readString();
        eventOrg=in.readString();
        eventDate=in.readString();
        userID=in.readString();
        profileUrl=in.readString();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventOrg() {
        return eventOrg;
    }

    public void setEventOrg(String eventOrg) {
        this.eventOrg = eventOrg;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public static final Creator<EventModal> CREATOR = new Creator<EventModal>() {
        @Override
        public EventModal createFromParcel(Parcel in) {
            return new EventModal(in);
        }

        @Override
        public EventModal[] newArray(int size) {
            return new EventModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventName);
        parcel.writeString(eventLocation);
        parcel.writeString(eventOrg);
        parcel.writeString(eventDate);
        parcel.writeString(userID);
        parcel.writeString(profileUrl);
    }
}
