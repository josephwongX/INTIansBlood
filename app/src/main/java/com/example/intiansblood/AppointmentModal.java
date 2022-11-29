package com.example.intiansblood;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentModal implements Parcelable {
    private String fullName,age,appointmentDate,appointmentTime;
    public AppointmentModal(String fName,String yo,String aDate,String aTime){
        this.fullName=fName;
        this.age=yo;
        this.appointmentDate=aDate;
        this.appointmentTime=aTime;
    }
    protected AppointmentModal(Parcel in) {
        fullName=in.readString();
        age=in.readString();
        appointmentDate=in.readString();
        appointmentTime=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(fullName);
        dest.writeString(age);
        dest.writeString(appointmentDate);
        dest.writeString(appointmentTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppointmentModal> CREATOR = new Creator<AppointmentModal>() {
        @Override
        public AppointmentModal createFromParcel(Parcel in) {
            return new AppointmentModal(in);
        }

        @Override
        public AppointmentModal[] newArray(int size) {
            return new AppointmentModal[size];
        }
    };

    public String getfName() {
        return fullName;
    }

    public void setfName(String fName) {
        this.fullName = fName;
    }

    public String getaDate() {
        return appointmentDate;
    }

    public void setaDate(String aDate) {
        this.appointmentDate = aDate;
    }

    public String getaTime() {
        return appointmentTime;
    }

    public void setaTime(String aTime) {
        this.appointmentTime = aTime;
    }

    public String getYo() {
        return age;
    }

    public void setYo(String yo) {
        this.age = yo;
    }
}
