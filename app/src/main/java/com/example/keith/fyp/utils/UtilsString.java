package com.example.keith.fyp.utils;

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
}
