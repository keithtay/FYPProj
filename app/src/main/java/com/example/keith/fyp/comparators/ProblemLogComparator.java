package com.example.keith.fyp.comparators;

import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.ProblemLog;

import org.joda.time.DateTime;

import java.util.Comparator;

/**
 * Created by Sutrisno on 8/10/2015.
 */
public class ProblemLogComparator implements Comparator<ProblemLog> {
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
