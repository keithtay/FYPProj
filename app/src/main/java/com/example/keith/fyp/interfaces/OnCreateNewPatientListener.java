package com.example.keith.fyp.interfaces;

/**
 * OnCreateNewPatientListener is an interface
 * that provide a listener when {@link com.example.keith.fyp.utils.Global#ACTION_CREATE_NEW_PATIENT} is broadcasted
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public interface OnCreateNewPatientListener {

    /**
     * A method to will be called when {@link com.example.keith.fyp.utils.Global#ACTION_CREATE_NEW_PATIENT} is broadcasted
     */
    void onCreateNewPatient();
}
