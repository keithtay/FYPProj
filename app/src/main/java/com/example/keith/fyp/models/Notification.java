package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 20/9/2015.
 */
public class Notification {

    public static final int TYPE_GAME_RECOMMENDATION = 0;
    public static final int TYPE_NEW_LOG = 1;
    public static final int TYPE_NEW_PATIENT = 2;
    public static final int TYPE_UPDATE_INFO_FIELD = 3;
    public static final int TYPE_UPDATE_INFO_OBJECT = 4;

    public static final int STATUS_NONE = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_REJECTED = 2;

    private DateTime creationDate;
    private String senderName;
    private Bitmap senderPhoto;
    private String summary;
    private int status;
    private int type;

    public Notification(DateTime creationDate, String senderName, Bitmap senderPhoto, String summary, int status, int type) {
        this.creationDate = creationDate;
        this.senderName = senderName;
        this.senderPhoto = senderPhoto;
        this.summary = summary;
        this.status = status;
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Bitmap getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(Bitmap senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
