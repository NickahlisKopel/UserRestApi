package com.careerdevs.userrestapi.models;


/*
{
   "id":4235,
   "name": "Dhanu Agarwal",
   "email":"dhanu_agarwal@macejkovic.name",
   "gender":"male",
   "status":"active"
* */




public class UserModel {

    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;

    //DO NOT DELETE OR CHANGE
    public UserModel() {
    }

    public UserModel(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String generateReport () {
        return name + " is currently " + status +". You can contact them at: "+ email;
    }
}
