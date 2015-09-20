package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 20/9/2015.
 */
public class Notification {
    private DateTime creationDate;
    private String senderName;
    private Bitmap senderPhoto;
    private String summary;
    private String status;

    public Notification(DateTime creationDate, String senderName, Bitmap senderPhoto, String summary, String status) {
        this.creationDate = creationDate;
        this.senderName = senderName;
        this.senderPhoto = senderPhoto;
        this.summary = summary;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}
