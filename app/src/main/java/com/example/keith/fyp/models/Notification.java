package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * Notification is a model to represent an in-app notification a user have.
 * Each notification is affecting a {@link Patient} in the care centre
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class Notification {

    /**
     * The identifier for notification with type game recommendation
     */
    public static final int TYPE_GAME_RECOMMENDATION = 0;

    /**
     * The identifier for notification with type new info object type
     * Info object is a group of patient information that are related to each other (e.g. patient's allergy which consist of name, reaction and notes)
     */
    public static final int TYPE_NEW_INFO_OBJECT = 1;

    /**
     * The identifier for notification with type new patient type
     */
    public static final int TYPE_NEW_PATIENT = 2;

    /**
     * The identifier for notification with type update info field type
     */
    public static final int TYPE_UPDATE_INFO_FIELD = 3;

    /**
     * The identifier for notification with type update info object type.
     * Info object is a group of patient information that are related to each other (e.g. patient's allergy which consist of name, reaction and notes)
     */
    public static final int TYPE_UPDATE_INFO_OBJECT = 4;

    public static final int TYPE_REJECTION_INFO_OBJECT = 5;

    public static final int TYPE_DELETE_INFO_OBJECT = 12;
    /**
     * The identifier for a notification that have not been responded by the user
     */
    public static final int STATUS_NONE = 0;

    /**
     * The identifier for a notification that have been accepted by the user
     */
    public static final int STATUS_ACCEPTED = 1;

    /**
     * The identifier for a notification that have been rejected by the user
     */
    public static final int STATUS_REJECTED = 2;

    private DateTime creationDate;
    private String senderName;
    private Bitmap senderPhoto;
    private String summary;
    private Patient affectedPatient;
    private String rejectionReason;
    private int status;
    private int type;

    private int logid;

    public int getLogid() {
        return logid;
    }

    public void setLogid(int logid) {
        this.logid = logid;
    }

    public String getLogData() {
        return logData;
    }

    public void setLogData(String logData) {
        this.logData = logData;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    private String logData;
    private String additionalInfo;

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    private String ta;
    private int ra;
    private int patientID;

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    /**
     * Create a new notification with the specified {@code creationDate}, {@code senderName}, {@code senderPhoto}, {@code summary}, {@code affectedPatient}, {@code status} and {@code type} values
     */
    public Notification(DateTime creationDate, String senderName, Bitmap senderPhoto, String summary, Patient affectedPatient, int status, int type, int logid, String logData, String additionalInfo, String ta, int ra, int patientID, String rejectionReason) {
        this.creationDate = creationDate;
        this.senderName = senderName;
        this.senderPhoto = senderPhoto;
        this.summary = summary;
        this.affectedPatient = affectedPatient;
        this.status = status;
        this.type = type;
        this.logid = logid;
        this.logData=logData;
        this.additionalInfo = additionalInfo;
        this.ta = ta;
        this.ra = ra;
        this.patientID = patientID;
        this.rejectionReason = rejectionReason;
    }

    /**
     * @return status of notification. Available status are {@link Notification#STATUS_NONE}, {@link Notification#STATUS_ACCEPTED} and {@link Notification#STATUS_REJECTED}
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the date when the notification is created
     */
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate creation date to set
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return name of the sender who send the notification
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName sender's name to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return photo of the sender who send the notification
     */
    public Bitmap getSenderPhoto() {
        return senderPhoto;
    }

    /**
     * @param senderPhoto sender's photo to set
     */
    public void setSenderPhoto(Bitmap senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    /**
     * @return summary of notification
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return type of notification. Available types are {@link Notification#TYPE_GAME_RECOMMENDATION}, {@link Notification#TYPE_NEW_INFO_OBJECT}, {@link Notification#TYPE_NEW_PATIENT}, {@link Notification#TYPE_UPDATE_INFO_FIELD} and {@link Notification#TYPE_UPDATE_INFO_OBJECT}
     */
    public int getType() {
        return type;
    }

    /**
     * @param type type of notification to set. Available types are {@link Notification#TYPE_GAME_RECOMMENDATION}, {@link Notification#TYPE_NEW_INFO_OBJECT}, {@link Notification#TYPE_NEW_PATIENT}, {@link Notification#TYPE_UPDATE_INFO_FIELD} and {@link Notification#TYPE_UPDATE_INFO_OBJECT}
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return patient that is affected by the notification.
     */
    public Patient getAffectedPatient() {
        return affectedPatient;
    }

    /**
     * @param affectedPatient the patient affected to set.
     */
    public void setAffectedPatient(Patient affectedPatient) {
        this.affectedPatient = affectedPatient;
    }

    /**
     * @return the reason why a notification is rejected
     */
    public String getRejectionReason() {
        return rejectionReason;
    }

    /**
     * @param rejectionReason the reason of rejectionto set.
     */
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
