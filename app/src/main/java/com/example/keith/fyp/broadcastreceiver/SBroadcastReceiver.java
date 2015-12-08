package com.example.keith.fyp.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.os.Bundle;
import android.util.Log;

//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.nearby.messages.internal.MessageType;

/**
 * Created by Keith on 30/9/2015.
 */
public class SBroadcastReceiver extends WakefulBroadcastReceiver {

    public void onReceive(Context context, Intent intent){

        // Attach component of GCMIntentService that will handle the intent in background thread
        ComponentName comp = new ComponentName(context.getPackageName(),
                GCMIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);}}


