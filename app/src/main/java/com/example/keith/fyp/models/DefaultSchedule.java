package com.example.keith.fyp.models;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 26/9/2015.
 */
public class DefaultSchedule {
    private String name;
    private DateTime startTime;
    private DateTime endTime;
    private Integer everyNumber;
    private String everyLabel;
    private String startDay;

    public DefaultSchedule(String name, DateTime startTime, DateTime endTime, Integer everyNumber, String everyLabel, String startDay) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.everyNumber = everyNumber;
        this.everyLabel = everyLabel;
        this.startDay = startDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getEveryNumber() {
        return everyNumber;
    }

    public void setEveryNumber(Integer everyNumber) {
        this.everyNumber = everyNumber;
    }

    public String getEveryLabel() {
        return everyLabel;
    }

    public void setEveryLabel(String everyLabel) {
        this.everyLabel = everyLabel;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
}
