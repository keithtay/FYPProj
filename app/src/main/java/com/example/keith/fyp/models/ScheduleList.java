package com.example.keith.fyp.models;

/**
 * Created by Keith on 21/12/2015.
 */
public class ScheduleList {
    private String eventName;
    private String eventDesc;

    public ScheduleList(String eventName, String eventDesc, boolean selected) {
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

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
