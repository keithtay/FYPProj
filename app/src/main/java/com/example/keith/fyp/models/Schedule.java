package com.example.keith.fyp.models;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class Schedule {
    private ArrayList<Event> eventList;

    public Schedule(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }
}
