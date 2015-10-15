package com.example.keith.fyp.interfaces;

import java.util.ArrayList;

/**
 * CreatePatientCommunicator is an interface
 * that bridge the communication between two objects such
 * that the objects should not be aware of each other.
 * It is specifically used to assist the communication
 * between {@link com.example.keith.fyp.views.fragments.PatientInfoCategListFragment} and {@link com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public interface CreatePatientCommunicator {

    /**
     * A method that will be called when the first object
     * want to send an integer data to the second object
     *
     * @param index
     *            an integer data that is going to be send to the second object.
     */
    void respond(int index);

    /**
     * A method that will be called when the first object
     * want to send an integer data and array list of integer to the second object
     *
     * @param index
     *            an integer data that is going to be send to the second object.
     * @param emptyFieldIdList
     *            an list of integer data that is going to be send to the second object.
     */
    void respond(int index, ArrayList<Integer> emptyFieldIdList);
}
