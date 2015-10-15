package com.example.keith.fyp.comparators;

import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.ProblemLog;

import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.List;

/**
 * ProblemLogComparator is a {@link Comparator}
 * to compare two {@link ProblemLog} to determine their ordering with
 * respect to each other. This comparator can be used to sort using {@link java.util.Collections#sort(List, Comparator)}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ProblemLogComparator implements Comparator<ProblemLog> {

    /**
     * Compares the two specified {@link ProblemLog} to determine their relative ordering. The ordering
     * implied by the return value of this method.
     *
     * @param lhs
     *            an {@link ProblemLog}.
     * @param rhs
     *            a second {@link ProblemLog} to compare with {@code lhs}.
     * @return an integer < 0 if {@code lhs} is created before {@code rhs}, 0 if they are
     *         created at the same time, and > 0 if {@code lhs} is created after {@code rhs}.
     */
    @Override
    public int compare(ProblemLog lhs, ProblemLog rhs) {
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
