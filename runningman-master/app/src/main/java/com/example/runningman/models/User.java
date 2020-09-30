package com.example.runningman.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String email;
    private String user_id;
    private String username;
    private String extraInfo;

    public User(){

    }

    public User(String email, String user_id, String username, String extraInfo) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.extraInfo = extraInfo;
    }

    protected User(Parcel in) {
        email = in.readString();
        user_id = in.readString();
        username = in.readString();
        extraInfo = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(user_id);
        parcel.writeString(username);
        parcel.writeString(extraInfo);
    }
}
