package com.example.keith.fyp.utils;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * UtilsString is a singleton class to
 * handle various operations to {@link String}
 *
 * @author Sutrisno Suryajaya Dwi Putra
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

        if (str != null && !str.isEmpty()) {
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

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     *
     * @param s1 first string
     * @param s2 second string
     * @return similarity between 0 and 1 (1 is exact same)
     */
    public static double similarity(String s1, String s2, boolean isCaseSensitive) {
        String longer = s1, shorter = s2;
        if(!isCaseSensitive) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();
        }
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0; /* both strings are zero length */
        }

        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    /**
     * Implementation of the Levenshtein Edit Distance
     *
     * @param s1 first string
     * @param s2 second string
     * @return Levenshtein edit distance between {@code s1} and {@code s2}
     */
    private static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    public static String arrayToString(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (String n : arr) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(n);
        }
        return sb.toString();
    }

    public static String capsFirstLetter(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();

            for(int i = 0; i < words.length; i++) {
                if(words[i].length() == 0) {
                    continue;
                }

                if(words[i].length() == 1) {
                    ret.append(Character.toUpperCase(words[i].charAt(0)));
                    ret.append(' ');
                } else {
                    ret.append(Character.toUpperCase(words[i].charAt(0)));
                    ret.append(words[i].substring(1).toLowerCase());
                    if(i < words.length - 1) {
                        ret.append(' ');
                    }
                }
            }

        return ret.toString();
    }
}