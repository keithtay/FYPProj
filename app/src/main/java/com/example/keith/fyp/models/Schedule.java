package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ProblemLog is a model to represent a series of {@link Event} that will done by the {@link Patient}.
 *
 * @author Sutrisno Suryajaya Dwi Putra
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

    /**
     * {@link Comparator} to compare two NRIC to determine their ordering with
     * respect to each other. This comparator can be used to sort using {@link java.util.Collections#sort(List, Comparator)}
     */
    public static Comparator<Schedule> COMPARE_BY_ID = new Comparator<Schedule>(){
        @Override
        public int compare(Schedule lhs, Schedule rhs) {
            return lhs.getNric().compareTo(rhs.getNric());
        }
    };

    /**
     * {@link Comparator} to compare two name to determine their ordering with
     * respect to each other. This comparator can be used to sort using {@link java.util.Collections#sort(List, Comparator)}
     */
    public static Comparator<Schedule> COMPARE_BY_NAME = new Comparator<Schedule>(){
        @Override
        public int compare(Schedule lhs, Schedule rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    };

    /**
     * {@link Comparator} to compare two {@link Schedule} to determine their ordering with
     * respect to each other. This comparator can be used to sort using {@link java.util.Collections#sort(List, Comparator)}
     */
    public static Comparator<Schedule> COMPARE_BY_SCHEDULE = new Comparator<Schedule>(){
        @Override
        public int compare(Schedule lhs, Schedule rhs) {
            return lhs.getnActivityTime().compareTo(rhs.getnActivityTime());
        }
    };

    /**
     * Default constructor
     */
    public Schedule() {}

    /**
     * Create a new schedule with the specified values
     */
    public Schedule(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Create a new schedule with the specified values
     */
    public Schedule(Bitmap photo, String firstName, String nric, String cActivity, String nActivityTime, String nActivity) {
        this.photo = photo;
        this.name = firstName;
        this.nric = nric;
        this.cActivity = cActivity;
        this.nActivityTime = nActivityTime;
        this.nActivity = nActivity;
    }

    /**
     * @return time of next activity of the schedule
     */
    public String getnActivityTime() {
        return nActivityTime;
    }

    /**
     * @param nActivityTime time of next activity of the schedule
     */
    public void setnActivityTime(String nActivityTime) {
        this.nActivityTime = nActivityTime;
    }

    /**
     * @return photo ID of the schedule's owner
     */
    public int getPhotoid() {
        return photoid;
    }

    /**
     * @param photoid photo ID of the schedule's owner
     */
    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    /**
     * @return time of current activity of the schedule
     */
    public String getcActivity() {
        return cActivity;
    }

    /**
     * @param cActivity time of current activity of the schedule
     */
    public void setcActivity(String cActivity) {
        this.cActivity = cActivity;
    }

    /**
     * @return name of next activity of the schedule
     */
    public String getnActivity() {
        return nActivity;
    }

    /**
     * @param nActivity name of next activity of the schedule
     */
    public void setnActivity(String nActivity) {
        this.nActivity = nActivity;
    }

    /**
     * @return name of the schedule
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of the schedule
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return NRIC of the schedule's owner
     */
    public String getNric() {
        return nric;
    }

    /**
     * @param nric NRIC of the schedule's owner
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * @return photo of the schedule's owner
     */
    public Bitmap getPhoto() {
        return photo;
    }

    /**
     * @param photo photo of the schedule's owner
     */
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    /**
     * @return list of {@link Event}
     */
    public ArrayList<Event> getEventList() {
        return eventList;
    }

    /**
     * @param eventList list of {@link Event} to set
     */
    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }
}
