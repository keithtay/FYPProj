package com.example.keith.fyp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.keith.fyp.interfaces.OnNotificationGroupUpdateListener;

/**
 * Created by Sutrisno on 2/10/2015.
 */
public class NotificationGroupUpdateReceiver extends BroadcastReceiver {

    private OnNotificationGroupUpdateListener notificationGroupUpdateListener = null;

    public NotificationGroupUpdateReceiver(OnNotificationGroupUpdateListener notificationGroupUpdateListener) {
        this.notificationGroupUpdateListener = notificationGroupUpdateListener;
    }

    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (null != notificationGroupUpdateListener) {
            notificationGroupUpdateListener.onNotificationGroupUpdate();
        }
    }
}
