package com.example.keith.fyp.utils;

import android.os.Looper;
import android.support.v7.appcompat.BuildConfig;

/**
 * Created by Sutrisno on 17/9/2015.
 */
public class UtilsThread {
    public static void checkOnMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}


