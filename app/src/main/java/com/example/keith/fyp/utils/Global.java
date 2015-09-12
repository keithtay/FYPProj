package com.example.keith.fyp.utils;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class Global {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("d MMM yyyy");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm");
}
