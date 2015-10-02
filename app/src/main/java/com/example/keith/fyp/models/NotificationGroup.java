package com.example.keith.fyp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Sutrisno on 2/10/2015.
 */
public class NotificationGroup {

    private ArrayList<Notification> unprocessedNotif = new ArrayList<>();
    private ArrayList<Notification> processedNotif = new ArrayList<>();
    private Patient affectedPatient;
    private String summary;
    private int status;

    public static final int STATUS_UNPROCESSED = 0;
    public static final int STATUS_ALL_PROCESSED = 1;

    public NotificationGroup() {

    }

    public NotificationGroup(ArrayList<Notification> unprocessedNotif, ArrayList<Notification> processedNotif, Patient affectedPatient, String summary, int status) {
        this.unprocessedNotif = unprocessedNotif;
        this.processedNotif = processedNotif;
        this.affectedPatient = affectedPatient;
        this.summary = summary;
        this.status = status;
    }

    public ArrayList<Notification> getUnprocessedNotif() {
        return unprocessedNotif;
    }

    public void setUnprocessedNotif(ArrayList<Notification> unprocessedNotif) {
        this.unprocessedNotif = unprocessedNotif;
    }

    public ArrayList<Notification> getProcessedNotif() {
        return processedNotif;
    }

    public void setProcessedNotif(ArrayList<Notification> processedNotif) {
        this.processedNotif = processedNotif;
    }

    public Patient getAffectedPatient() {
        return affectedPatient;
    }

    public void setAffectedPatient(Patient affectedPatient) {
        this.affectedPatient = affectedPatient;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
