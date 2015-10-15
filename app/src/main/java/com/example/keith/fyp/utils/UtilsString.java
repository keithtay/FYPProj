package com.example.keith.fyp.utils;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * UtilsString is a singleton class to
 * handle various operations to {@link String}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class UtilsString {
    /**
     * Method to check if the passed {@code str} is empty
     *
     * @param str string to be checked
     * @return {@code true} if {@code str} is empty, otherwise {@code false}
     */
    public static boolean isEmpty(String str) {
        boolean isEmpty = true;

        if(str != null && !str.isEmpty()) {
            isEmpty = false;
        }

        return isEmpty;
    }

    /**
     * Method to check if the passed {@code str1} and {@code str2} has the same value
     *
     * @param str1 first string to be checked
     * @param str2 second string to be checked
     * @return {@code true} if {@code str1} and {@code str2} has the same value, otherwise {@code false}
     */
    public static boolean isEqual(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

    /**
     * Method to check if the passed {@code bool1} and {@code bool2} has the same value
     *
     * @param bool1 first boolean to be checked
     * @param bool2 second boolean to be checked
     * @return {@code true} if {@code bool1} and {@code bool2} has the same value, otherwise {@code false}
     */
    public static boolean isEqual(Boolean bool1, Boolean bool2) {
        return (bool1 == null ? bool2 == null : bool1.equals(bool2));
    }

    /**
     * Method to check if the passed {@code dateTime1} and {@code dateTime2} has the same value
     *
     * @param dateTime1 first date and time to be checked
     * @param dateTime2 second date and time to be checked
     * @return {@code true} if {@code dateTime1} and {@code dateTime2} has the same value, otherwise {@code false}
     */
    public static boolean isEqual(DateTime dateTime1, DateTime dateTime2) {
        return (dateTime1 == null ? dateTime2 == null : dateTime1.equals(dateTime2));
    }

    /**
     * Method to check if the passed {@code bitmap1} and {@code bitmap2} has the same value
     *
     * @param bitmap1 first bitmap to be checked
     * @param bitmap2 second bitmap to be checked
     * @return {@code true} if {@code bitmap1} and {@code bitmap2} has the same value, otherwise {@code false}
     */
    public static boolean isEqual(Bitmap bitmap1, Bitmap bitmap2) {
        return (bitmap1 == null ? bitmap2 == null : bitmap1.equals(bitmap2));
    }
}