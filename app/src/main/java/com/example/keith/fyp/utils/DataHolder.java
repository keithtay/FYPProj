package com.example.keith.fyp.utils;

import com.example.keith.fyp.models.Patient;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class DataHolder {
    // Patient currently being created
    private static Patient createdPatient;

    // Patient currently being viewed
    private static Patient viewedPatient;

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
}
