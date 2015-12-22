package com.example.keith.fyp.models;

/**
 * Created by Keith on 21/12/2015.
 */
public class ScheduleList {
    private String eventName;
    private String eventDesc;

    public void ScheduleList(String eventName, String eventDesc){
        this.eventName = eventName;
        this.eventDesc = eventDesc;
    }
    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
