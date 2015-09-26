package com.example.keith.fyp.interfaces;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 26/9/2015.
 */
public interface CreatePatientCommunicator {
    void respond(int index);
    void respond(int index, ArrayList<Integer> emptyFieldIdList);
}
