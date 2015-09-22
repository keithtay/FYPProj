package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class Schedule {
    private ArrayList<Event> eventList;
    private String cActivity;
    private String nActivity;
    private String name;
    private String nric;
    private Bitmap photo;
    private int photoid;

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    public String getcActivity() {
        return cActivity;
    }

    public void setcActivity(String cActivity) {
        this.cActivity = cActivity;
    }

    public String getnActivity() {
        return nActivity;
    }

    public void setnActivity(String nActivity) {
        this.nActivity = nActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Schedule(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public Schedule(int photoid, String firstName, String nric, String cActivity, String nActivity) {
        this.photoid = photoid;
        this.name = firstName;
        this.nric = nric;
        this.cActivity = cActivity;
        this.nActivity = nActivity;
    }
}
