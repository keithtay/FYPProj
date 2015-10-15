package com.example.keith.fyp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.keith.fyp.interfaces.OnCreateNewPatientListener;

/**
 * CreateNewPatientReceiver is a <a href="http://developer.android.com/reference/android/content/BroadcastReceiver.html"></a>BroadcastReceiver</a>
 * that act as a callback class when a new {@link com.example.keith.fyp.models.Patient} is created.
 * It is triggered when {@link com.example.keith.fyp.utils.Global#ACTION_CREATE_NEW_PATIENT} event is broadcasted.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class CreateNewPatientReceiver extends BroadcastReceiver {

    private OnCreateNewPatientListener createNewPatientListener;

    /**
     * Constructor
     *
     * @param createNewPatientListener       listener that will be called when {@link com.example.keith.fyp.utils.Global#ACTION_CREATE_NEW_PATIENT} event is broadcasted.
     */
    public CreateNewPatientReceiver(OnCreateNewPatientListener createNewPatientListener) {
        this.createNewPatientListener = createNewPatientListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != createNewPatientListener) {
            createNewPatientListener.onCreateNewPatient();
        }
    }
}
