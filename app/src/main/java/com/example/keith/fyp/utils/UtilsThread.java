package com.example.keith.fyp.utils;

import android.os.Looper;
import android.support.v7.appcompat.BuildConfig;

/**
 * UtilsThread is a singleton class to
 * handle various operations about Android thread
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class UtilsThread {

    /**
     * Method to ensure that current the method is run on the main thread
     */
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}


