package com.example.keith.fyp.utils;

import android.provider.ContactsContract;

import com.example.keith.fyp.models.Patient;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 26/9/2015.
 */
public class CreatedPatientEmptyFieldChecker {
    public static ArrayList<Integer> checkPersonalInfo() {
        Patient createdPatient = DataHolder.getCreatedPatient();

        ArrayList<Integer> emptyFieldIdList = new ArrayList<>();

        if (UtilsString.isEmpty(createdPatient.getFirstName())) {
            emptyFieldIdList.add(Global.FIRST_NAME_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getLastName())) {
            emptyFieldIdList.add(Global.LAST_NAME_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getNric())) {
            emptyFieldIdList.add(Global.NRIC_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getAddress())) {
            emptyFieldIdList.add(Global.ADDRESS_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getHomeNumber())) {
            emptyFieldIdList.add(Global.HOME_NUMBER_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getPhoneNumber())) {
            emptyFieldIdList.add(Global.PHONE_NUMBER_FIELD);
        }

        if (createdPatient.getGender() == 0) {
            emptyFieldIdList.add(Global.GENDER_FIELD);
        }

        if (createdPatient.getDob() == null) {
            emptyFieldIdList.add(Global.DOB_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getGuardianFullName())) {
            emptyFieldIdList.add(Global.GUARDIAN_NAME_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getGuardianContactNumber())) {
            emptyFieldIdList.add(Global.GUARDIAN_CONTACT_NUMBER_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getGuardianEmail())) {
            emptyFieldIdList.add(Global.GUARDIAN_EMAIL_FIELD);
        }

        if (createdPatient.getPhoto() == null) {
            emptyFieldIdList.add(Global.PHOTO);
        }

        return emptyFieldIdList;
    }

    public static ArrayList<Integer> checkAllergy() {
        Patient createdPatient = DataHolder.getCreatedPatient();

        ArrayList<Integer> emptyFieldIdList = new ArrayList<>();

        if(createdPatient.getHasAllergy() == null) {
            emptyFieldIdList.add(Global.HAS_ALLERGY_RADIO_GROUP);
        }

        return emptyFieldIdList;
    }
}
