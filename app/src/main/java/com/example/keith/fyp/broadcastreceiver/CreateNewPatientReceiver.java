package com.example.keith.fyp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.keith.fyp.interfaces.OnCreateNewPatientListener;

/**
 * Created by Sutrisno on 26/9/2015.
 */
public class CreateNewPatientReceiver extends BroadcastReceiver {

    private OnCreateNewPatientListener createNewPatientListener;

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
