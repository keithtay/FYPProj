package com.example.keith.fyp.comparators;

import com.example.keith.fyp.models.Notification;

import org.joda.time.DateTime;

import java.util.Comparator;

/**
 * Created by Sutrisno on 2/10/2015.
 */
public class NotificationComparator implements Comparator<Notification> {
    @Override
    public int compare(Notification lhs, Notification rhs) {
        DateTime lhsDate = lhs.getCreationDate();
        DateTime rhsDate = rhs.getCreationDate();

        if (lhsDate.isBefore(rhsDate)) {
            return 1;
        } else if (lhsDate.isAfter(rhsDate)) {
            return -1;
        } else {
            return 0;
        }
    }
}
