package com.example.keith.fyp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * NotificationGroup is a model to represent a group of notification that affect the same patient
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class NotificationGroup {

    private ArrayList<Notification> unprocessedNotif = new ArrayList<>();
    private ArrayList<Notification> processedNotif = new ArrayList<>();
    private Patient affectedPatient;
    private String summary;
    private int status;

    /**
     * The identifier for notification group status when any of the notification in the group have the status {@link Notification#STATUS_NONE}
     */
    public static final int STATUS_UNPROCESSED = 0;

    /**
     * The identifier for notification group status when all the notifications in the group have the status {@link Notification#STATUS_ACCEPTED} or {@link Notification#STATUS_REJECTED}
     */
    public static final int STATUS_ALL_PROCESSED = 1;

    /**
     * Default construction
     */
    public NotificationGroup() {}

    /**
     * Create a new group of notification with the specified {@code unprocessedNotif}, {@code processedNotif}, {@code affectedPatient}, {@code summary} and {@code status} values.
     * {@code unprocessedNotif} is list of notifications that have status {@link Notification#STATUS_NONE}.
     * {@code processedNotif} is list of notifications that have status {@link Notification#STATUS_ACCEPTED} or {@link Notification#STATUS_REJECTED}.
     * Available {@code status} are {@link NotificationGroup#STATUS_UNPROCESSED} or {@link NotificationGroup#STATUS_ALL_PROCESSED}
     */
    public NotificationGroup(ArrayList<Notification> unprocessedNotif, ArrayList<Notification> processedNotif, Patient affectedPatient, String summary, int status) {
        this.unprocessedNotif = unprocessedNotif;
        this.processedNotif = processedNotif;
        this.affectedPatient = affectedPatient;
        this.summary = summary;
        this.status = status;
    }

    /**
     * {@code unprocessedNotif} is list of notifications that have status {@link Notification#STATUS_NONE}.
     * @return list of unprocessed notifications
     */
    public ArrayList<Notification> getUnprocessedNotif() {
        return unprocessedNotif;
    }

    /**
     * {@code unprocessedNotif} is list of notifications that have status {@link Notification#STATUS_NONE}.
     * @param unprocessedNotif list of unprocessed notifications to set
     */
    public void setUnprocessedNotif(ArrayList<Notification> unprocessedNotif) {
        this.unprocessedNotif = unprocessedNotif;
    }

    /**
     * {@code processedNotif} is list of notifications that have status {@link Notification#STATUS_ACCEPTED} or {@link Notification#STATUS_REJECTED}.
     * @return list of processed notifications
     */
    public ArrayList<Notification> getProcessedNotif() {
        return processedNotif;
    }

    /**
     * {@code processedNotif} is list of notifications that have status {@link Notification#STATUS_ACCEPTED} or {@link Notification#STATUS_REJECTED}.
     * @param processedNotif list of processed notifications to set
     */
    public void setProcessedNotif(ArrayList<Notification> processedNotif) {
        this.processedNotif = processedNotif;
    }

    /**
     * @return patient affected by the notification group
     */
    public Patient getAffectedPatient() {
        return affectedPatient;
    }

    /**
     * @param affectedPatient affected patient by the notification group to set
     */
    public void setAffectedPatient(Patient affectedPatient) {
        this.affectedPatient = affectedPatient;
    }

    /**
     * Available {@code status} are {@link NotificationGroup#STATUS_UNPROCESSED} or {@link NotificationGroup#STATUS_ALL_PROCESSED}
     * @return status of notification group
     */
    public int getStatus() {
        return status;
    }

    /**
     * Available {@code status} are {@link NotificationGroup#STATUS_UNPROCESSED} or {@link NotificationGroup#STATUS_ALL_PROCESSED}
     * @param status status of notification group
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return summary of notification group
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary summary of notification group
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
