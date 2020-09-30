package com.example.runningman.models;

public class UserLocation {
    private double lat;
    private double lng;
    public UserLocation(){

    }

    public UserLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
