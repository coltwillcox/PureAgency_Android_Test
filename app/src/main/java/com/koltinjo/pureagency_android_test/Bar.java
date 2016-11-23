package com.koltinjo.pureagency_android_test;

/**
 * Created by colt on 23.11.2016.
 */

public class Bar {

    private String name;
    private String address;
    private String image;
    private double latitude;
    private double longitude;

    public Bar() {

    }

    public Bar(String name, String address, String image, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}