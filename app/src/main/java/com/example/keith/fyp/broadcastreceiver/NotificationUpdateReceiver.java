package com.example.keith.fyp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 24/9/2015.
 */
public class NotificationUpdateReceiver extends BroadcastReceiver {

    private OnNotificationUpdateListener notificationUpdateListener = null;

    public NotificationUpdateReceiver(OnNotificationUpdateListener notificationUpdateListener) {
        this.notificationUpdateListener = notificationUpdateListener;
    }

    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (null != notificationUpdateListener) {
            notificationUpdateListener.onNotificationUpdate();
        }
    }
}