package com.example.keith.fyp.models;

import org.joda.time.DateTime;

import java.util.Comparator;

/**
 * DefaultEvent is a model to represent the care centre default events.
 * Default event is an activity that will be done by all the patients in the care centre at the same time (e.g. lunch time)
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class DefaultEvent {
    private String name;
    private DateTime startTime;
    private DateTime endTime;
    private Integer everyNumber;
    private String everyLabel;
    private String startDay;


    /**
     * Create a new default event with the specified {@code name}, {@code startTime}, {@code endTime}, {@code everyNumber}, {@code everyLabel} and {@code startDay} values.
     * {@code everyLabel} is the metrics of {@code everyNumber} (e.g. an event that happened every two days will have {@code everyNumber} equals to 2 and {@code everyLabel} equals to "day")
     */
    public DefaultEvent(String name, DateTime startTime, DateTime endTime, Integer everyNumber, String everyLabel, String startDay) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.everyNumber = everyNumber;
        this.everyLabel = everyLabel;
        this.startDay = startDay;
    }

    /**
     * @return name of default event
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
     * @return start time of default event
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
     * @return end time of default event
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
     * @return the number of time the default event happened every the specified {@code everyLabel}
     */
    public Integer getEveryNumber() {
        return everyNumber;
    }

    /**
     * Every number is the number of time the default event happened every the specified {@code everyLabel}.
     * @param everyNumber every number to set.
     */
    public void setEveryNumber(Integer everyNumber) {
        this.everyNumber = everyNumber;
    }

    /**
     * {@code everyLabel} is the metrics of {@code everyNumber} (e.g. an event that happened every two days will have {@code everyNumber} equals to 2 and {@code everyLabel} equals to "day")
     * @return the everyLabel of the default event
     */
    public String getEveryLabel() {
        return everyLabel;
    }

    /**
     * Every label is the metrics of {@code everyNumber} (e.g. an event that happened every two days will have {@code everyNumber} equals to 2 and {@code everyLabel} equals to "day")
     * @param everyLabel every label to set.
     */
    public void setEveryLabel(String everyLabel) {
        this.everyLabel = everyLabel;
    }

    /**
     * @return starting day of default event
     */
    public String getStartDay() {
        return startDay;
    }

    /**
     * @param startDay starting day to set
     */
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public static Comparator<DefaultEvent> COMPARE_BY_TIME = new Comparator<DefaultEvent>(){
        @Override
        public int compare(DefaultEvent lhs, DefaultEvent rhs) {
            return lhs.getStartTime().compareTo(rhs.getStartTime());
        }
    };
}
