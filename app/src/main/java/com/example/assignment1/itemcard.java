package com.example.assignment1;

public class itemcard {


    private int img;
    private String name;
    private String price;
    private int views;
    private String date;

    public itemcard(int img, String name, String price, int views, String date) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.views = views;
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
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
}
