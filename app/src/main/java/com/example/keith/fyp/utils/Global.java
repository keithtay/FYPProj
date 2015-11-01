package com.example.keith.fyp.utils;

import android.os.Environment;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Global is a singleton class that
 * hold constant attributes that being used across different
 * part of the application
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class Global {
    /**
     * String date format of NRIC
     */
    public static final String NRIC_DATE_FORMAT_STR =  "dd-MM-yyyy";
    /**
     * String date format
     */
    public static final String DATE_FORMAT_STR =  "d MMM yyyy";
    /**
     * String time format
     */
    public static final String TIME_FORMAT_STR =  "HH:mm";
    /**
     * String date and time format
     */
    public static final String DATE_TIME_FORMAT_STR = DATE_FORMAT_STR + " " + TIME_FORMAT_STR;
    /**
     * Formatter to print date
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern(DATE_FORMAT_STR);
    /**
     * Formatter to print date for NRIC
     */
    public static final DateTimeFormatter NRIC_DATE_FORMAT = DateTimeFormat.forPattern(NRIC_DATE_FORMAT_STR);
    /**
     * Formatter to print time
     */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern(TIME_FORMAT_STR);
    /**
     * Formatter to print date and time
     */
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern(DATE_TIME_FORMAT_STR);





    /**
     * Data path to retrieve the photo captured for OCR
     */
    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/PearPCC/";
    /**
     * Language used in OCR feature
     */
    public static final String LANG = "eng";






    /**
     * Bundle key for empty field ID list
     */
    public static final String EXTRA_EMPTY_FIELD_ID_LIST = "EXTRA_EMPTY_FIELD_ID_LIST";
    /**
     * Bundle key for selected category
     */
    public static final String EXTRA_SELECTED_CATEGORY = "EXTRA_SELECTED_CATEGORY";
    /**
     * Bundle key for recognized text
     */
    public static final String EXTRA_RECOGNIZED_TEXT = "EXTRA_RECOGNIZED_TEXT";
    /**
     * Bundle key to notify from notification detail activity
     */
    public static final String EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY = "EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY";
    /**
     * Bundle key for selected navigation ID
     */
    public static final String EXTRA_SELECTED_NAVIGATION_ID = "EXTRA_SELECTED_NAVIGATION_ID";
    /**
     * Bundle key for selected patient draft ID
     */
    public static final String EXTRA_SELECTED_PATIENT_DRAFT_ID = "EXTRA_SELECTED_PATIENT_DRAFT_ID";
    /**
     * Bundle key for last displayed fragment ID
     */
    public static final String STATE_LAST_DISPLAYED_FRAGMENT_ID = "LAST_DISPLAYED_FRAGMENT_ID";
    /**
     * Bundle key for selected patient draft ID
     */
    public static final String STATE_SELECTED_PATIENT_DRAFT_ID = "STATE_SELECTED_PATIENT_DRAFT_ID";
    /**
     * Bundle key for selected patient NRIC
     */
    public static final String STATE_SELECTED_PATIENT_NRIC = "STATE_SELECTED_PATIENT_NRIC";






    /**
     * Event identifier for {@link com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver}
     */
    public static final String ACTION_NOTIFICATION_UPDATE = "ACTION_NOTIFICATION_UPDATE";
    /**
     * Event identifier for {@link com.example.keith.fyp.broadcastreceiver.NotificationGroupUpdateReceiver}
     */
    public static final String ACTION_NOTIFICATION_GROUP_UPDATE = "ACTION_NOTIFICATION_GROUP_UPDATE";
    /**
     * Event identifier for {@link com.example.keith.fyp.broadcastreceiver.CreateNewPatientReceiver}
     */
    public static final String ACTION_CREATE_NEW_PATIENT = "ACTION_CREATE_NEW_PATIENT";






    /**
     * Shared preference key to store patient draft
     */
    public static final String SP_CREATE_PATIENT_DRAFT = "SP_CREATE_PATIENT_DRAFT";






    /**
     * Photo field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer PHOTO = -1;
    /**
     * First name field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer FIRST_NAME_FIELD = 0;
    /**
     * Last name field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer LAST_NAME_FIELD = 1;
    /**
     * NRIC field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer NRIC_FIELD = 2;
    /**
     * Address field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer ADDRESS_FIELD = 3;
    /**
     * Home number field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer HOME_NUMBER_FIELD = 4;
    /**
     * Photo number field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer PHONE_NUMBER_FIELD = 5;
    /**
     * Gender field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer GENDER_FIELD = 6;
    /**
     * Date of birth field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer DOB_FIELD = 7;
    /**
     * Guardian name field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer GUARDIAN_NAME_FIELD = 8;
    /**
     * Guardian contact number field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer GUARDIAN_CONTACT_NUMBER_FIELD = 9;
    /**
     * Guardian email field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer GUARDIAN_EMAIL_FIELD = 10;
    /**
     * Has allergy field identifier for {@link CreatedPatientEmptyFieldChecker}
     */
    public static final Integer HAS_ALLERGY_RADIO_GROUP = 11;





    /**
     * Patient list navigation identifier for the navigation bar
     */
    public static final int NAVIGATION_PATIENT_LIST_ID = 1;
    /**
     * Notification navigation identifier for the navigation bar
     */
    public static final int NAVIGATION_NOTIFICATION_ID = 2;
    /**
     * Care centre configuration navigation identifier for the navigation bar
     */
    public static final int NAVIGATION_CARE_CENTER_CONFIG_ID = 4;
    public static final int NAVIGATION_LOGOUT = 5;
}
