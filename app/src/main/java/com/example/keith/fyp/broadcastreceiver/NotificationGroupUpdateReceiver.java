package com.example.keith.fyp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.keith.fyp.interfaces.OnNotificationGroupUpdateListener;

/**
 * NotificationGroupUpdateReceiver is a <a href="http://developer.android.com/reference/android/content/BroadcastReceiver.html"></a>BroadcastReceiver</a>
 * that act as a callback class when a new {@link com.example.keith.fyp.models.NotificationGroup} is created.
 * It is triggered when {@link com.example.keith.fyp.utils.Global#ACTION_NOTIFICATION_GROUP_UPDATE} event is broadcasted.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class NotificationGroupUpdateReceiver extends BroadcastReceiver {

    private OnNotificationGroupUpdateListener notificationGroupUpdateListener = null;

    /**
     * Constructor
     *
     * @param notificationGroupUpdateListener       listener that will be called when {@link com.example.keith.fyp.utils.Global#ACTION_NOTIFICATION_GROUP_UPDATE} event is broadcasted.
     */
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
