package com.careerdevs.userrestapi.models;

/*
{
    "id": 2072,
    "user_id": 4201,
    "title": "Aduro vilicus aperte sequi acerbitas subseco crinis ultra voluptatem degenero.",
    "due_on": "2022-04-04T00:00:00.000+05:30",
    "status": "pending"
  }



*/


import com.fasterxml.jackson.annotation.JsonProperty;

public class TodosModel {

    private int id;

    @JsonProperty("user_id")
    private int userId;

    private String title;
    private String due_on;
    private String status;

    public TodosModel() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDue_on() {
        return due_on;
    }

    public String getStatus() {
        return status;
    }
}
