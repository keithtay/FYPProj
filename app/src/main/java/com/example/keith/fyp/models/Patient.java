package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import com.example.keith.fyp.utils.UtilsString;

import org.joda.time.DateTime;

import java.util.ArrayList;

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
    private Boolean hasAllergy;
    private ArrayList<Allergy> allergyList = new ArrayList<>();
    private ArrayList<Vital> vitalList = new ArrayList<>();
    private ArrayList<Prescription> prescriptionList = new ArrayList<>();
    private ArrayList<Routine> routineList = new ArrayList<>();
    private ArrayList<ProblemLog> problemLogList = new ArrayList<>();
    private SocialHistory socialHistory = new SocialHistory();
    private Schedule todaySchedule;

    // TODO: remove since it is only for testing (using local stored photo)
    private int photoId;

    public Patient() {
    }

    public Patient(String firstName, String nric, int photoId) {
        this.firstName = firstName;
        this.nric = nric;
        this.photoId = photoId;
    }

    public Patient(String firstName, String lastName, String nric, Bitmap photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nric = nric;
        this.photo = photo;
    }

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

    public ArrayList<Allergy> getAllergyList() {
        return allergyList;
    }

    public void setAllergyList(ArrayList<Allergy> allergyList) {
        this.allergyList = allergyList;
    }

    public ArrayList<Vital> getVitalList() {
        return vitalList;
    }

    public void setVitalList(ArrayList<Vital> vitalList) {
        this.vitalList = vitalList;
    }

    public SocialHistory getSocialHistory() {
        return socialHistory;
    }

    public void setSocialHistory(SocialHistory socialHistory) {
        this.socialHistory = socialHistory;
    }

    public ArrayList<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(ArrayList<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public ArrayList<Routine> getRoutineList() {
        return routineList;
    }

    public void setRoutineList(ArrayList<Routine> routineList) {
        this.routineList = routineList;
    }

    public Schedule getTodaySchedule() {
        return todaySchedule;
    }

    public void setTodaySchedule(Schedule todaySchedule) {
        this.todaySchedule = todaySchedule;
    }

    public ArrayList<ProblemLog> getProblemLogList() {
        return problemLogList;
    }

    public void setProblemLogList(ArrayList<ProblemLog> problemLogList) {
        this.problemLogList = problemLogList;
    }

    public Boolean getHasAllergy() {
        return hasAllergy;
    }

    public void setHasAllergy(Boolean hasAllergy) {
        this.hasAllergy = hasAllergy;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient patient = (Patient) other;

        if(!UtilsString.isEqual(firstName, patient.getFirstName())) {
            return false;
        }

        if(!UtilsString.isEqual(lastName, patient.getLastName())) {
            return false;
        }

        if(!UtilsString.isEqual(nric, patient.getNric())) {
            return false;
        }

        if(!UtilsString.isEqual(address, patient.getAddress())) {
            return false;
        }

        if(!UtilsString.isEqual(homeNumber, patient.getHomeNumber())) {
            return false;
        }

        if(!UtilsString.isEqual(phoneNumber, patient.getPhoneNumber())) {
            return false;
        }

        if(gender != patient.getGender()) {
            return false;
        }

        if(!UtilsString.isEqual(dob, patient.getDob())) {
            return false;
        }

        if(!UtilsString.isEqual(photo, patient.getPhoto())) {
            return false;
        }

        if(!UtilsString.isEqual(guardianFullName, patient.getGuardianFullName())) {
            return false;
        }

        if(!UtilsString.isEqual(guardianContactNumber, patient.getGuardianContactNumber())) {
            return false;
        }

        if(!UtilsString.isEqual(guardianEmail, patient.getGuardianEmail())) {
            return false;
        }

        if(!UtilsString.isEqual(hasAllergy, patient.getHasAllergy())) {
            return false;
        }

        if(allergyList.size() != patient.getAllergyList().size()) {
            return false;
        }

        if(vitalList.size() != patient.getVitalList().size()) {
            return false;
        }

        if(prescriptionList.size() != patient.getPrescriptionList().size()) {
            return false;
        }

        if(routineList.size() != patient.getRoutineList().size()) {
            return false;
        }

        SocialHistory socialHistoryOther = patient.getSocialHistory();

        if(!UtilsString.isEqual(socialHistory.getLiveWith(), socialHistoryOther.getLiveWith())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getDiet(), socialHistoryOther.getDiet())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getReligion(), socialHistoryOther.getReligion())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getIsSexuallyActive(), socialHistoryOther.getIsSexuallyActive())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getIsSecondhandSmoker(), socialHistoryOther.getIsSecondhandSmoker())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getAlcoholUse(), socialHistoryOther.getAlcoholUse())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getCaffeineUse(), socialHistoryOther.getCaffeineUse())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getTobaccoUse(), socialHistoryOther.getTobaccoUse())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getDrugUse(), socialHistoryOther.getDrugUse())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getPet(), socialHistoryOther.getPet())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getOccupation(), socialHistoryOther.getOccupation())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getLike(), socialHistoryOther.getLike())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getDislike(), socialHistoryOther.getDislike())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getHobby(), socialHistoryOther.getHobby())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getHabbit(), socialHistoryOther.getHabbit())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getHolidayExperience(), socialHistoryOther.getHolidayExperience())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getEducation(), socialHistoryOther.getEducation())) {
            return false;
        }

        if(!UtilsString.isEqual(socialHistory.getExercise(), socialHistoryOther.getExercise())) {
            return false;
        }

        return true;
    }
}