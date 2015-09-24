package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class Routine implements ObjectToAttributeValueTransformer {
    private String name;
    private String notes;
    private DateTime startDate;
    private DateTime endDate;
    private DateTime startTime;
    private DateTime endTime;
    private Integer everyNumber;
    private String everyLabel;

    public Routine(String name, String notes, DateTime startDate, DateTime endDate, DateTime startTime, DateTime endTime, Integer everyNumber, String everyLabel) {
        this.name = name;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.everyNumber = everyNumber;
        this.everyLabel = everyLabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
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

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();

        list.add(new AttributeValuePair("Name", getName()));
        list.add(new AttributeValuePair("Notes", getNotes()));

        String startDateStr = startDate.toString(Global.DATE_FORMAT);
        String endDateStr = endDate.toString(Global.DATE_FORMAT);
        String startTimeStr = startTime.toString(Global.TIME_FORMAT);
        String endTimeStr = endTime.toString(Global.TIME_FORMAT);
        String everyStr = Integer.toString(everyNumber) + " " + everyLabel;

        list.add(new AttributeValuePair("Date range", startDateStr + " to " + endDateStr));
        list.add(new AttributeValuePair("Time", startTimeStr + " to " + endTimeStr));
        list.add(new AttributeValuePair("Every", everyStr));

        return list;
    }
}
