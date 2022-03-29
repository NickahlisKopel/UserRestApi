package com.careerdevs.userrestapi.models;


/*
{
    "id": 2083,
    "user_id": 4204,
    "title": "Cumque decet uberrime sumo voluptas sto antepono recusandae speciosus solio verecundia advenio consuasor civitas absens caveo verbera dolores.",
    "body": "Vomer ceno laborum. Dolorum subvenio sed. Sequi vomica canto. Thesis vorago ea. Vulpes articulus ubi. Uter sollicito avarus. Communis amo abscido. Trans magnam aro. Cursim aer aestus. Cruentus terror beatae. Tabella defessus carmen. Catena terebro urbanus. Acidus deprimo clementia. Abutor strenuus exercitationem. Creptio amplitudo ultra."
  }



*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostModel {

    private int id;

    @JsonProperty("user_id")
    private int userId;

    private String title;
    private String body;

    public PostModel() {
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

    public String getBody() {
        return body;
    }
}

