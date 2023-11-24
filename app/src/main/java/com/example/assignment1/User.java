package com.example.assignment1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class User implements Parcelable {
   String name;
   String Email;

    public User( ) {
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    String Contact;
   String id;

   String displaypic;

   String coverpic;

   String pass;

    public String getDisplaypic() {
        return displaypic;
    }

    public void setDisplaypic(String displaypic) {
        this.displaypic = displaypic;
    }

    public String getCoverpic() {
        return coverpic;
    }

    public void setCoverpic(String coverpic) {
        this.coverpic = coverpic;
    }

    public User(String name, String email, String contact, String id) {
        this.name = name;
        this.Email = email;
        this.Contact = contact;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User();
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        //parcel.writeParcelable(img, i);
        parcel.writeString(name);

        parcel.writeString(Email);
        parcel.writeString(Contact);
        parcel.writeString(id);
        parcel.writeString(pass);
        parcel.writeString(displaypic);
        parcel.writeString(coverpic);


    }


}
