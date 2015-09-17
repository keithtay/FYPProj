package com.example.keith.fyp.models;

import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class Event implements Cloneable {
    private String title;
    private String description;
    private DateTime startTime;
    private DateTime endTime;
    private Duration duration;

    public Event(String title, String description, DateTime startTime, DateTime endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = new Duration(startTime, endTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
        recalculateDuration();
    }

    public Duration getDuration() {
        return duration;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
        recalculateDuration();
    }

    private void recalculateDuration() {
        this.duration = new Duration(this.startTime, this.endTime);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
