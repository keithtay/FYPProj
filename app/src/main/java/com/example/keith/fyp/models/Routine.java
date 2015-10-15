package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Routine is a model to represent an activity that regularly done by a {@link Patient}'s
 *
 * @author Sutrisno Suryajaya Dwi Putra
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

    /**
     * Create a new routine with the specified values.
     * {@code everyLabel} is the metrics of {@code everyNumber} (e.g. a routine that happened every two days will have {@code everyNumber} equals to 2 and {@code everyLabel} equals to "day").
     */
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

    /**
     * @return name of the routine
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return notes of the routine
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return start date of the routine
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate start date to set
     */
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end date of the routine
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate end date to set
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return start time of the routine
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime start time to set
     */
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end time of the routine
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime end time to set
     */
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the number of time the routine happened every the specified {@code everyLabel}
     */
    public Integer getEveryNumber() {
        return everyNumber;
    }

    /**
     * Every number is the number of time the routine happened every the specified {@code everyLabel}.
     * @param everyNumber every number to set.
     */
    public void setEveryNumber(Integer everyNumber) {
        this.everyNumber = everyNumber;
    }

    /**
     * {@code everyLabel} is the metrics of {@code everyNumber} (e.g. a routine that happened every two days will have {@code everyNumber} equals to 2 and {@code everyLabel} equals to "day")
     * @return the everyLabel of the routine
     */
    public String getEveryLabel() {
        return everyLabel;
    }

    /**
     * Every label is the metrics of {@code everyNumber} (e.g. a routine that happened every two days will have {@code everyNumber} equals to 2 and {@code everyLabel} equals to "day")
     * @param everyLabel every label to set.
     */
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
