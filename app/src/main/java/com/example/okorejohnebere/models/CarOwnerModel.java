package com.example.okorejohnebere.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CarOwnerModel implements Parcelable {

    private String first_name;
    private String last_name;
    private String email;
    private String country;
    private String car_model;
    private String car_model_year;
    private String car_color;
    private String gender;
    private String job_title;
    private String bio;

    public CarOwnerModel(){

    }

    public CarOwnerModel(Parcel source) {
        first_name = source.readString();
        last_name = source.readString();
        email = source.readString();
        country = source.readString();
        car_model = source.readString();
        car_model_year = source.readString();
        car_color = source.readString();
        gender = source.readString();
        job_title = source.readString();
        bio = source.readString();
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public void setCar_model_year(String car_model_year) {
        this.car_model_year = car_model_year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBio() {
        return bio;
    }

    public String getCar_color() {
        return car_color;
    }

    public String getCar_model() {
        return car_model;
    }

    public String getCar_model_year() {
        return car_model_year;
    }

    public String getCountry() {
        return country;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getLast_name() {
        return last_name;
    }


    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(country);
        dest.writeString(car_model);
        dest.writeString(car_model_year);
        dest.writeString(car_color);
        dest.writeString(gender);
        dest.writeString(job_title);
        dest.writeString(bio);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public CarOwnerModel createFromParcel(Parcel in) {
            return new CarOwnerModel(in);
        }

        public CarOwnerModel[] newArray(int size) {
            return new CarOwnerModel[size];
        }
    };
}
