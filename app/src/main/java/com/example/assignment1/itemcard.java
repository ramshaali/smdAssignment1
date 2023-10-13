package com.example.assignment1;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class itemcard implements Parcelable {


    private Bitmap img;
    private String name;
    private String price;
    private int views;
    private String date;

    public itemcard(Bitmap img, String name, String price, int views, String date) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.views = views;
        this.date = date;
    }

    protected itemcard(Parcel in) {
        img = in.readParcelable(Bitmap.class.getClassLoader());
        name = in.readString();
        price = in.readString();
        views = in.readInt();
        date = in.readString();
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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(img, i);
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeInt(views);
        parcel.writeString(date);
    }
}
