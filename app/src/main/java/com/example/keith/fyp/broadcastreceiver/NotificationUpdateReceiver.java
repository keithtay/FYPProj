package com.example.keith.fyp.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;

import java.util.ArrayList;

/**
 * NotificationUpdateReceiver is a <a href="http://developer.android.com/reference/android/content/BroadcastReceiver.html"></a>BroadcastReceiver</a>
 * that act as a callback class when a new {@link com.example.keith.fyp.models.Notification} is created.
 * It is triggered when {@link com.example.keith.fyp.utils.Global#ACTION_NOTIFICATION_UPDATE} event is broadcasted.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class NotificationUpdateReceiver extends BroadcastReceiver {

    private OnNotificationUpdateListener notificationUpdateListener = null;

    /**
     * Constructor
     *
     * @param notificationUpdateListener       listener that will be called when {@link com.example.keith.fyp.utils.Global#ACTION_NOTIFICATION_UPDATE} event is broadcasted.
     */
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