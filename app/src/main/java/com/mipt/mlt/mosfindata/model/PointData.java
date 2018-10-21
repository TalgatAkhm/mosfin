package com.mipt.mlt.mosfindata.model;

import com.google.android.gms.maps.model.LatLng;

public class PointData {

    private LatLng location;

    private int intensity;

    public PointData(LatLng location, int intensity) {
        this.location = location;
        this.intensity = (int) Math.floor(intensity/100d * 255);
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = (int) Math.floor(intensity/100d * 255);
    }
}
