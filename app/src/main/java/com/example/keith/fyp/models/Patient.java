package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import com.example.keith.fyp.utils.UtilsString;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Patient is a model to represent a patient in the care centre
 *
 * @author Sutrisno Suryajaya Dwi Putra
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
    private ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();
    private int AllocatonID;

    public int getAllocatonID() {
        return AllocatonID;
    }

    public void setAllocatonID(int allocatonID) {
        AllocatonID = allocatonID;
    }

    // TODO: remove since it is only for testing (using local stored photo)
    private int photoId;

    /**
     * Default construction
     */
    public Patient() {
    }

    /**
     * Create a new patient with the specified values.
     */
    public Patient(String firstName, String nric, int photoId) {
        this.firstName = firstName;
        this.nric = nric;
        this.photoId = photoId;
    }

    /**
     * Create a new patient with the specified values.
     */
    public Patient(String firstName, String lastName, String nric, Bitmap photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nric = nric;
        this.photo = photo;
    }

    /**
     * @return guardian's full name of the patient
     */
    public String getGuardianFullName() {
        return guardianFullName;
    }

    /**
     * @param guardianFullName guardian full name to set
     */
    public void setGuardianFullName(String guardianFullName) {
        this.guardianFullName = guardianFullName;
    }

    /**
     * @return guardian's contact number of the patient
     */
    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }

    /**
     * @param guardianContactNumber guardian contact name to set
     */
    public void setGuardianContactNumber(String guardianContactNumber) {
        this.guardianContactNumber = guardianContactNumber;
    }

    /**
     * @return guardian's email of the patient
     */
    public String getGuardianEmail() {
        return guardianEmail;
    }

    /**
     * @param guardianEmail guardian email name to set
     */
    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }

    /**
     * @return last name of the patient
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return address of the patient
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return home number of the patient
     */
    public String getHomeNumber() {
        return homeNumber;
    }

    /**
     * @param homeNumber home number to set
     */
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    /**
     * @return phone number of the patient
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return gender of the patient
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * @return photo of the patient
     */
    public Bitmap getPhoto() {
        return photo;
    }

    /**
     * @param photo photo to set
     */
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    /**
     * @return first name of the patient
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return NRIC of the patient
     */
    public String getNric() {
        return nric;
    }

    public int getPhotoId() {
        return photoId;
    }

    /**
     * @param firstName first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param nric nric to set
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    /**
     * @return date of birth of the patient
     */
    public DateTime getDob() {
        return dob;
    }

    /**
     * @param dob date of birth to set
     */
    public void setDob(DateTime dob) {
        this.dob = dob;
    }

    /**
     * @return {@link Allergy} list of the patient
     */
    public ArrayList<Allergy> getAllergyList() {
        return allergyList;
    }

    /**
     * @param allergyList {@link Allergy} list to set
     */
    public void setAllergyList(ArrayList<Allergy> allergyList) {
        this.allergyList = allergyList;
    }

    /**
     * @return {@link Vital} list of the patient
     */
    public ArrayList<Vital> getVitalList() {
        return vitalList;
    }

    /**
     * @param vitalList {@link Vital} list to set
     */
    public void setVitalList(ArrayList<Vital> vitalList) {
        this.vitalList = vitalList;
    }

    /**
     * @return {@link SocialHistory} of the patient
     */
    public SocialHistory getSocialHistory() {
        return socialHistory;
    }

    /**
     * @param socialHistory social history to set
     */
    public void setSocialHistory(SocialHistory socialHistory) {
        this.socialHistory = socialHistory;
    }

    /**
     * @return {@link Prescription} list of the patient
     */
    public ArrayList<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    /**
     * @param prescriptionList {@link Prescription} list to set
     */
    public void setPrescriptionList(ArrayList<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    /**
     * @return {@link Routine} list of the patient
     */
    public ArrayList<Routine> getRoutineList() {
        return routineList;
    }

    /**
     * @param routineList {@link Routine} list to set
     */
    public void setRoutineList(ArrayList<Routine> routineList) {
        this.routineList = routineList;
    }

    /**
     * @return today's {@link Schedule} of the patient
     */
    public Schedule getTodaySchedule() {
        return todaySchedule;
    }

    /**
     * @param todaySchedule today's {@link Schedule} to set
     */
    public void setTodaySchedule(Schedule todaySchedule) {
        this.todaySchedule = todaySchedule;
    }

    /**
     * @return {@link ProblemLog} list of the patient
     */
    public ArrayList<ProblemLog> getProblemLogList() {
        return problemLogList;
    }

    /**
     * @param problemLogList {@link ProblemLog} list to set
     */
    public void setProblemLogList(ArrayList<ProblemLog> problemLogList) {
        this.problemLogList = problemLogList;
    }

    /**
     * @return {@code true} if the patient has allergy otherwise {@code false}
     */
    public Boolean getHasAllergy() {
        return hasAllergy;
    }

    /**
     * @param hasAllergy set whether or not the patient has allergy
     */
    public void setHasAllergy(Boolean hasAllergy) {
        this.hasAllergy = hasAllergy;
    }

    /**
     * @return full name of the patient
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public ArrayList<PhotoAlbum> getPhotoAlbumList() {
        return photoAlbumList;
    }

    public void setPhotoAlbumList(ArrayList<PhotoAlbum> photoAlbumList) {
        this.photoAlbumList = photoAlbumList;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient patient = (Patient) other;

        if (!UtilsString.isEqual(firstName, patient.getFirstName())) {
            return false;
        }

        if (!UtilsString.isEqual(lastName, patient.getLastName())) {
            return false;
        }

        if (!UtilsString.isEqual(nric, patient.getNric())) {
            return false;
        }

        if (!UtilsString.isEqual(address, patient.getAddress())) {
            return false;
        }

        if (!UtilsString.isEqual(homeNumber, patient.getHomeNumber())) {
            return false;
        }

        if (!UtilsString.isEqual(phoneNumber, patient.getPhoneNumber())) {
            return false;
        }

        if (gender != patient.getGender()) {
            return false;
        }

        if (!UtilsString.isEqual(dob, patient.getDob())) {
            return false;
        }

        if (!UtilsString.isEqual(photo, patient.getPhoto())) {
            return false;
        }

        if (!UtilsString.isEqual(guardianFullName, patient.getGuardianFullName())) {
            return false;
        }

        if (!UtilsString.isEqual(guardianContactNumber, patient.getGuardianContactNumber())) {
            return false;
        }

        if (!UtilsString.isEqual(guardianEmail, patient.getGuardianEmail())) {
            return false;
        }

        if (!UtilsString.isEqual(hasAllergy, patient.getHasAllergy())) {
            return false;
        }

        if (allergyList.size() != patient.getAllergyList().size()) {
            return false;
        }

        if (vitalList.size() != patient.getVitalList().size()) {
            return false;
        }

        if (prescriptionList.size() != patient.getPrescriptionList().size()) {
            return false;
        }

        if (routineList.size() != patient.getRoutineList().size()) {
            return false;
        }

        SocialHistory socialHistoryOther = patient.getSocialHistory();

        if (!UtilsString.isEqual(socialHistory.getLiveWith(), socialHistoryOther.getLiveWith())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getDiet(), socialHistoryOther.getDiet())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getReligion(), socialHistoryOther.getReligion())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getIsSexuallyActive(), socialHistoryOther.getIsSexuallyActive())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getIsSecondhandSmoker(), socialHistoryOther.getIsSecondhandSmoker())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getAlcoholUse(), socialHistoryOther.getAlcoholUse())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getCaffeineUse(), socialHistoryOther.getCaffeineUse())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getTobaccoUse(), socialHistoryOther.getTobaccoUse())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getDrugUse(), socialHistoryOther.getDrugUse())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getPet(), socialHistoryOther.getPet())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getOccupation(), socialHistoryOther.getOccupation())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getLike(), socialHistoryOther.getLike())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getDislike(), socialHistoryOther.getDislike())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getHobby(), socialHistoryOther.getHobby())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getHabbit(), socialHistoryOther.getHabbit())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getHolidayExperience(), socialHistoryOther.getHolidayExperience())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getEducation(), socialHistoryOther.getEducation())) {
            return false;
        }

        if (!UtilsString.isEqual(socialHistory.getExercise(), socialHistoryOther.getExercise())) {
            return false;
        }

        return true;
    }
}