package com.mipt.mlt.mosfindata.model;

import android.graphics.Color;

import com.mipt.mlt.mosfindata.R;

public class Category {

    private int photo;
    private String title;
    private String des;
    private int bgColor;

    public Category(String title, String des) {
        this.title = title;
        this.des = des;
        this.photo = R.drawable.pet;
        this.bgColor = Color.parseColor("#dfe6e9");
    }

    public Category(int photo, String title, String des) {
        this.photo = photo;
        this.title = title;
        this.des = des;
        this.bgColor = Color.parseColor("#dfe6e9");
    }

    public Category(String title, String des, int photo,  int bgColor) {
        this.photo = photo;
        this.title = title;
        this.des = des;
        this.bgColor = bgColor;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
