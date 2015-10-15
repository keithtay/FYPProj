package com.example.keith.fyp.comparators;

import com.example.keith.fyp.models.Notification;

import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.List;

/**
 * NotificationComparator is a {@link Comparator}
 * to compare two {@link Notification} to determine their ordering with
 * respect to each other. This comparator can be used to sort using {@link java.util.Collections#sort(List, Comparator)}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class NotificationComparator implements Comparator<Notification> {

    /**
     * Compares the two specified {@link Notification} to determine their relative ordering. The ordering
     * implied by the return value of this method.
     *
     * @param lhs
     *            an {@link Notification}.
     * @param rhs
     *            a second {@link Notification} to compare with {@code lhs}.
     * @return an integer < 0 if {@code lhs} is created before {@code rhs}, 0 if they are
     *         created at the same time, and > 0 if {@code lhs} is created after {@code rhs}.
     */
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
