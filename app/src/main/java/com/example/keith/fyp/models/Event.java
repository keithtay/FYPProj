package com.example.keith.fyp.models;

import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Event is a model to represent an activities need to be done by the {@link Patient}.
 * Event is a part of {@link Schedule}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class Event implements Cloneable {
    private String title;
    private String description;
    private DateTime startTime;
    private DateTime endTime;
    private Duration duration;

    /**
     * Create a new event with the specified {@code title}, {@code description}, {@code startTime} and {@code endTime} values
     */
    public Event(String title, String description, DateTime startTime, DateTime endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = new Duration(startTime, endTime);
    }

    /**
     * @return title of event
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description of event
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return end time of event
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime end time to set
     */
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
        recalculateDuration();
    }

    /**
     * @return duration of event (different between {@code startTime} and {@code endTime})
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * @return start time of event
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime start time to set
     */
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
