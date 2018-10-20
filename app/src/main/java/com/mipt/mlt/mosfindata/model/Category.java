package com.mipt.mlt.mosfindata.model;

import com.mipt.mlt.mosfindata.R;

public class Category {

    private int photo;
    private String title;
    private String des;

    public Category(String title, String des) {
        this.title = title;
        this.des = des;
        this.photo = R.drawable.pet;
    }

    public Category(int photo, String title, String des) {
        this.photo = photo;
        this.title = title;
        this.des = des;
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
}
