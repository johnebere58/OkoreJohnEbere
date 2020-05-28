package com.example.okorejohnebere.models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterModel {
    String avatar;
    String createdAt;
    String fullName;
    String gender;
    JSONArray colors;
    JSONArray countries;
    ArrayList<CarOwnerModel> carList;

    public void setCarList(ArrayList<CarOwnerModel> carList) {
        this.carList = carList;
    }
    public ArrayList<CarOwnerModel> getCarList() {
        return carList;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setColors(JSONArray colors) {
        this.colors = colors;
    }

    public void setCountries(JSONArray countries) {
        this.countries = countries;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public JSONArray getColors() {
        return colors;
    }

    public JSONArray getCountries() {
        return countries;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }
}
