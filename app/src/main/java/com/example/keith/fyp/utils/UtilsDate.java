package com.example.keith.fyp.utils;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 17/9/2015.
 */
public class UtilsDate {
    public static DateTime setCurrentDateToTime(DateTime time) {
        DateTime now = DateTime.now();
        now = now.withHourOfDay(time.getHourOfDay());
        now = now.withMinuteOfHour(time.getMinuteOfHour());
        return now;
    }

    public static DateTime setCurrentDateToTime(String timeStr) {
        DateTime dateTime = DateTime.now();
        DateTime time = Global.TIME_FORMAT.parseDateTime(timeStr);
        dateTime = dateTime.withHourOfDay(time.getHourOfDay());
        dateTime = dateTime.withMinuteOfHour(time.getMinuteOfHour());
        return dateTime;
    }
}
