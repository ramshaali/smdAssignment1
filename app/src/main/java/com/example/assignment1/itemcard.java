package com.example.assignment1;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class itemcard implements Parcelable {


    private String img;
    private String name;
    private String price;
    private int views;
    private String date;

    private String ownerid;
    private String id;

    public itemcard(String img, String name, String price, int views, String date, String ownerid) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.views = views;
        this.date = date;
        this.ownerid=ownerid;
    }

    protected itemcard(Parcel in) {
        //img = in.readParcelable(Bitmap.class.getClassLoader());
        img=in.readString();
        name = in.readString();
        price = in.readString();
        views = in.readInt();
        date = in.readString();
        ownerid=in.readString();
        id=in.readString();
    }

    public static final Creator<itemcard> CREATOR = new Creator<itemcard>() {
        @Override
        public itemcard createFromParcel(Parcel in) {
            return new itemcard(in);
        }

        @Override
        public itemcard[] newArray(int size) {
            return new itemcard[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        //parcel.writeParcelable(img, i);
        parcel.writeString(img);

        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeInt(views);
        parcel.writeString(date);
        parcel.writeString(ownerid);
        parcel.writeString(id);

    }
}
