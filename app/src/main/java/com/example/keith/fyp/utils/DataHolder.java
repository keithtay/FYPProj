package com.example.keith.fyp.utils;

import com.example.keith.fyp.models.Patient;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class DataHolder {
    // Patient currently being created
    private static Patient createdPatient;

    public static Patient getCreatedPatient() {
        if(createdPatient == null) {
            createdPatient = new Patient();
        }
        return createdPatient;
    }

    public static void resetCreatedPatient() {
        createdPatient = new Patient();
    }
}
