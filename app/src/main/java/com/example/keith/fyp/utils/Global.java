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

    // Bundle keys
    public static final String EXTRA_EMPTY_FIELD_ID_LIST = "EXTRA_EMPTY_FIELD_ID_LIST";
    public static final String EXTRA_SELECTED_CATEGORY = "EXTRA_SELECTED_CATEGORY";

    // Event list for broadcast receiver
    public static final String ACTION_NOTIFICATION_UPDATE = "ACTION_NOTIFICATION_UPDATE";
    public static final String ACTION_CREATE_NEW_PATIENT = "ACTION_CREATE_NEW_PATIENT";

    // Create new patient fields IDs (for input checking)
    public static final Integer PHOTO = -1;
    public static final Integer FIRST_NAME_FIELD = 0;
    public static final Integer LAST_NAME_FIELD = 1;
    public static final Integer NRIC_FIELD = 2;
    public static final Integer ADDRESS_FIELD = 3;
    public static final Integer HOME_NUMBER_FIELD = 4;
    public static final Integer PHONE_NUMBER_FIELD = 5;
    public static final Integer GENDER_FIELD = 6;
    public static final Integer DOB_FIELD = 7;
    public static final Integer GUARDIAN_NAME_FIELD = 8;
    public static final Integer GUARDIAN_CONTACT_NUMBER_FIELD = 9;
    public static final Integer GUARDIAN_EMAIL_FIELD = 10;
    public static final Integer HAS_ALLERGY_RADIO_GROUP = 11;
}
