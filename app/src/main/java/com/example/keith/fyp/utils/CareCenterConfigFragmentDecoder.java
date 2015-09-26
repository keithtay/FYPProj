package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.views.fragments.CareCenterConfigDefaultScheduleFragment;

/**
 * Created by Sutrisno on 26/9/2015.
 */
public class CareCenterConfigFragmentDecoder {
    public static Fragment getFragment(int index) {

        Fragment fragmentToBeDisplayed = null;

        switch(index) {
            case 0:
                fragmentToBeDisplayed = new CareCenterConfigDefaultScheduleFragment();
                break;
        }

        return fragmentToBeDisplayed;
    }
}
