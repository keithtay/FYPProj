package com.example.keith.fyp.utils;

import android.os.Environment;

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

    // OCR related
    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/PearPCC/";
    public static final String LANG = "eng";

    // Bundle keys / Intent extras
    public static final String EXTRA_EMPTY_FIELD_ID_LIST = "EXTRA_EMPTY_FIELD_ID_LIST";
    public static final String EXTRA_SELECTED_CATEGORY = "EXTRA_SELECTED_CATEGORY";
    public static final String EXTRA_OPEN_PROBLEM_LOG_TAB = "EXTRA_OPEN_PROBLEM_LOG_TAB";
    public static final String EXTRA_RECOGNIZED_TEXT = "EXTRA_RECOGNIZED_TEXT";
    public static final String EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY = "EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY";
    public static final String EXTRA_SELECTED_NAVIGATION_ID = "EXTRA_SELECTED_NAVIGATION_ID";
    public static final String EXTRA_SELECTED_PATIENT_DRAFT_ID = "EXTRA_SELECTED_PATIENT_DRAFT_ID";

    public static final String STATE_LAST_DISPLAYED_FRAGMENT_ID = "LAST_DISPLAYED_FRAGMENT_ID";
    public static final String STATE_SELECTED_PATIENT_DRAFT_ID = "STATE_SELECTED_PATIENT_DRAFT_ID";
    public static final String STATE_SELECTED_PATIENT_NRIC = "STATE_SELECTED_PATIENT_NRIC";
    public static final String STATE_SELECTED_PROBLEM_LOG_CATEGORY_FILTER = "STATE_SELECTED_PROBLEM_LOG_CATEGORY_FILTER";

    // Event list for broadcast receiver
    public static final String ACTION_NOTIFICATION_UPDATE = "ACTION_NOTIFICATION_UPDATE";
    public static final String ACTION_NOTIFICATION_GROUP_UPDATE = "ACTION_NOTIFICATION_GROUP_UPDATE";
    public static final String ACTION_CREATE_NEW_PATIENT = "ACTION_CREATE_NEW_PATIENT";

    // Keys in Shared Preferences
    public static final String SP_CREATE_PATIENT_DRAFT = "SP_CREATE_PATIENT_DRAFT";

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

    // Navigation bar item IDs
    public static final int NAVIGATION_PATIENT_LIST_ID = 1;
    public static final int NAVIGATION_NOTIFICATION_ID = 2;
    public static final int NAVIGATION_OLD_PATIENT_LIST_ID = 3;
    public static final int NAVIGATION_CARE_CENTER_CONFIG_ID = 4;

}
