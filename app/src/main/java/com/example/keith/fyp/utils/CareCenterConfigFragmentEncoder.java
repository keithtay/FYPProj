package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.renderers.Renderer;
import com.example.keith.fyp.views.fragments.CareCenterConfigDefaultEventFragment;
import com.example.keith.fyp.views.fragments.NotificationSettingsFragment;

/**
 * CareCenterConfigFragmentEncoder is a singleton class that
 * encode an integer ID into a care centre config {@link Fragment}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class CareCenterConfigFragmentEncoder {

    /**
     * Method to get {@link Fragment} based on the specified integer ID
     *
     * @param index integer ID of the {@link Fragment} identifier
     * @return encoded {@link Fragment}
     */
    public static Fragment getFragment(int index) {

        Fragment fragmentToBeDisplayed = null;

        switch(index) {
            case 0:
                fragmentToBeDisplayed = new CareCenterConfigDefaultEventFragment();
                break;
            case 1:
                fragmentToBeDisplayed = new NotificationSettingsFragment();
                break;
        }

        return fragmentToBeDisplayed;
    }
}
