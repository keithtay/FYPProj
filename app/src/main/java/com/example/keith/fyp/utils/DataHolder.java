package com.example.keith.fyp.utils;

import com.example.keith.fyp.models.DefaultSchedule;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.models.Patient;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class DataHolder {
    // Patient currently being created
    private static Patient createdPatient;

    // Patient currently being viewed
    private static Patient viewedPatient;

    private static ArrayList<NotificationGroup> notificationGroupList = new ArrayList<>();
    private static ArrayList<DefaultSchedule> defaultScheduleList = new ArrayList<>();

    public static Patient getCreatedPatient() {
        if(createdPatient == null) {
            createdPatient = new Patient();
        }
        return createdPatient;
    }

    public static void resetCreatedPatient() {
        createdPatient = new Patient();
    }

    public static Patient getViewedPatient() {
        if(viewedPatient == null) {
            viewedPatient = new Patient();
        }
        return viewedPatient;
    }

    public static void resetViewedPatient() {
        viewedPatient = new Patient();
    }

    public static ArrayList<NotificationGroup> getNotificationGroupList() {
        return notificationGroupList;
    }

    public static void setNotificationGroupList(ArrayList<NotificationGroup> notificationGroupList) {
        DataHolder.notificationGroupList = notificationGroupList;
    }

    public static ArrayList<DefaultSchedule> getDefaultScheduleList() {
        return defaultScheduleList;
    }

    public static void setDefaultScheduleList(ArrayList<DefaultSchedule> defaultScheduleList) {
        DataHolder.defaultScheduleList = defaultScheduleList;
    }
}
