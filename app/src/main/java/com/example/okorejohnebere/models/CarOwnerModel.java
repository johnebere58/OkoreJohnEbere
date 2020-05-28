package com.example.okorejohnebere.models;

public class CarOwnerModel {

    String first_name;
    String last_name;
    String email;
    String country;
    String car_model;
    String car_model_year;
    String car_color;
    String gender;
    String job_title;
    String bio;

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
}
