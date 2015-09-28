package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class Schedule {
    private ArrayList<Event> eventList;
    private String cActivity;
    private String nActivity;
    private String name;
    private String nric;
    private String nActivityTime;
    private Bitmap photo;
    private int photoid;

    public String getnActivityTime() {
        return nActivityTime;
    }

    public void setnActivityTime(String nActivityTime) {
        this.nActivityTime = nActivityTime;
    }

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

    public Schedule(int photoid, String firstName, String nric, String cActivity, String nActivityTime, String nActivity) {
        this.photoid = photoid;
        this.name = firstName;
        this.nric = nric;
        this.cActivity = cActivity;
        this.nActivityTime = nActivityTime;
        this.nActivity = nActivity;
    }

    public static Comparator<Schedule> COMPARE_BY_ID = new Comparator<Schedule>(){
        @Override
        public int compare(Schedule lhs, Schedule rhs) {
            return lhs.getNric().compareTo(rhs.getNric());
        }
    };

    public static Comparator<Schedule> COMPARE_BY_NAME = new Comparator<Schedule>(){
        @Override
        public int compare(Schedule lhs, Schedule rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    };

    public static Comparator<Schedule> COMPARE_BY_SCHEDULE = new Comparator<Schedule>(){
        @Override
        public int compare(Schedule lhs, Schedule rhs) {
            return lhs.getnActivityTime().compareTo(rhs.getnActivityTime());
        }
    };
}
