package com.example.organ_donation_application;
public class BloodBank {
    private int id;
    private String name;
    private String imageUrl;

    private String mobile_number;
    private String latitude;
    private String longitude;

    public BloodBank(int id, String name, String imageUrl, String mobile_number, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.mobile_number = mobile_number;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
