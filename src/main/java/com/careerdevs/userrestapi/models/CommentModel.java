package com.careerdevs.userrestapi.models;


/*
{"id":2127,
"post_id":2094,
"name":"Tanushri Panicker",
"email":"tanushri_panicker@johnson-gerlach.co",
"body":"Autem nemo in."}


*/


import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentModel {

    private int id;

    @JsonProperty("post_id")
    private int postId;

    private String name;
    private String email;
    private String body;


    public CommentModel() {
    }


    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id=" + id +
                ", post_id=" + postId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
