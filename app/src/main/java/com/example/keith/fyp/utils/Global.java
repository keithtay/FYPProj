package com.example.keith.fyp.utils;

import com.example.keith.fyp.views.adapters.NotificationListAdapter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class Global {
    public static final String DATE_FORMAT_STR =  "d MMM yyyy";
    public static final String TIME_FORMAT_STR =  "HH:mm";
    public static final String DATE_TIME_FORMAT_STR = DATE_FORMAT_STR + " " + TIME_FORMAT_STR;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern(DATE_FORMAT_STR);
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern(TIME_FORMAT_STR);
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern(DATE_TIME_FORMAT_STR);

    public static final String ACTION_NOTIFICATION_UPDATE = "ACTION_NOTIFICATION_UPDATE";
}
