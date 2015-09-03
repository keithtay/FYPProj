package com.example.keith.fyp.models;

/**
 * Created by Sutrisno on 3/9/2015.
 */
public class Patient {
    private String name;
    private String nric;
    private int photoId;

    public Patient(String name, String nric, int photoId) {
        this.name = name;
        this.nric = nric;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public int getPhotoId() {
        return photoId;
    }
}
