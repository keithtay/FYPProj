package com.example.keith.fyp.utils;

import org.joda.time.DateTime;

/**
 * UtilsDate is a singleton class to
 * handle various operations to {@link DateTime}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class UtilsDate {

    /**
     * Method to get the current date with the passed time
     *
     * @param timeStr time to be included in current date
     * @return current date with the passed time
     */
    public static DateTime setCurrentDateToTime(String timeStr) {
        DateTime dateTime = DateTime.now();
        DateTime time = Global.TIME_FORMAT.parseDateTime(timeStr);
        dateTime = dateTime.withHourOfDay(time.getHourOfDay());
        dateTime = dateTime.withMinuteOfHour(time.getMinuteOfHour());
        return dateTime;
    }
}
