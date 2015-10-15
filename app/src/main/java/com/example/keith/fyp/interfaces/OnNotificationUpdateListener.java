package com.example.keith.fyp.interfaces;

/**
 * OnNotificationUpdateListener is an interface
 * that provide a listener when {@link com.example.keith.fyp.utils.Global#ACTION_NOTIFICATION_UPDATE} is broadcasted
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public interface OnNotificationUpdateListener {

    /**
     * A method to will be called when {@link com.example.keith.fyp.utils.Global#ACTION_NOTIFICATION_UPDATE} is broadcasted
     */
    void onNotificationUpdate();
}
