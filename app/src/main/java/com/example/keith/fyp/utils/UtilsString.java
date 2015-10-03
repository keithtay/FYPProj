package com.example.keith.fyp.utils;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 17/9/2015.
 */
public class UtilsString {
    public static boolean isEmpty(String str) {
        boolean isEmpty = true;

        if(str != null && !str.isEmpty()) {
            isEmpty = false;
        }

        return isEmpty;
    }

    public static boolean isEqual(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

    public static boolean isEqual(Boolean bool1, Boolean bool2) {
        return (bool1 == null ? bool2 == null : bool1.equals(bool2));
    }

    public static boolean isEqual(DateTime dateTime1, DateTime dateTime2) {
        return (dateTime1 == null ? dateTime2 == null : dateTime1.equals(dateTime2));
    }

    public static boolean isEqual(Bitmap bitmap1, Bitmap bitmap2) {
        return (bitmap1 == null ? bitmap2 == null : bitmap1.equals(bitmap2));
    }
}