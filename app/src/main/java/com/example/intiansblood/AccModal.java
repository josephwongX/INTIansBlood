package com.example.intiansblood;

import android.os.Parcel;
import android.os.Parcelable;

public class AccModal implements Parcelable {
    private String fullName,email,phoneNumber,profileImgUrl,bloodgroup;
public AccModal(){

}
public AccModal(String fullName,String email,String phoneNumber,String bloodgroup){
    this.fullName=fullName;
    this.email=email;
    this.phoneNumber=phoneNumber;
    this.bloodgroup=bloodgroup;
}
public AccModal(String fullName,String email,String phoneNumber,String bloodgroup,String profileImgUrl){
    this.fullName=fullName;
    this.email=email;
    this.phoneNumber=phoneNumber;
    this.bloodgroup=bloodgroup;
    this.profileImgUrl=profileImgUrl;
}
    protected AccModal(Parcel in) {
        fullName=in.readString();
        email=in.readString();
        phoneNumber=in.readString();
        profileImgUrl=in.readString();
        bloodgroup=in.readString();
    }

    public static final Creator<AccModal> CREATOR = new Creator<AccModal>() {
        @Override
        public AccModal createFromParcel(Parcel in) {
            return new AccModal(in);
        }

        @Override
        public AccModal[] newArray(int size) {
            return new AccModal[size];
        }
    };

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
        parcel.writeString(bloodgroup);
        parcel.writeString(profileImgUrl);
    }
}
