package com.example.organ_donation_application;

public class BloodDonorList
{
    private int id;
    private String name;
    private String age;

    private String mobile;
    private String email;
    private String bloodGroup;

    public BloodDonorList(int id, String name, String age, String mobile, String email, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.bloodGroup = bloodGroup;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
