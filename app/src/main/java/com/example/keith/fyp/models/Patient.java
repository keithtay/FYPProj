package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 3/9/2015.
 */
public class Patient {
    private String firstName;
    private String lastName;
    private String nric;
    private String address;
    private String homeNumber;
    private String phoneNumber;
    private char gender;
    private DateTime dob;
    private Bitmap photo;
    private String guardianFullName;
    private String guardianContactNumber;
    private String guardianEmail;

    public String getGuardianFullName() {
        return guardianFullName;
    }

    public void setGuardianFullName(String guardianFullName) {
        this.guardianFullName = guardianFullName;
    }

    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }

    public void setGuardianContactNumber(String guardianContactNumber) {
        this.guardianContactNumber = guardianContactNumber;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }

    // TODO: remove since it is only for testing (using local stored photo)
    private int photoId;

    public Patient() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Patient(String firstName, String nric, int photoId) {
        this.firstName = firstName;
        this.nric = nric;
        this.photoId = photoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNric() {
        return nric;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public DateTime getDob() {
        return dob;
    }

    public void setDob(DateTime dob) {
        this.dob = dob;
    }
}
