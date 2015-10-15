package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormRoutineFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPrescriptionFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormSocialHistoryFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormVitalFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPersonalInfoFragment;

/**
 * CreatePatientFormFragmentEncoder is a singleton class that
 * encode an integer ID into a {@link CreatePatientInfoFormFragment}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class CreatePatientFormFragmentEncoder {

    /**
     * Method to get {@link CreatePatientInfoFormFragment} based on the specified integer ID
     *
     * @param index integer ID of the {@link CreatePatientInfoFormFragment} identifier
     * @return encoded {@link CreatePatientInfoFormFragment}
     */
    public static Fragment getFragment(int index) {

        Fragment fragmentToBeDisplayed = null;

        switch(index) {
            case 0:
                fragmentToBeDisplayed = new CreatePatientInfoFormPersonalInfoFragment();
                break;
            case 1:
                fragmentToBeDisplayed = new CreatePatientInfoFormAllergyFragment();
                break;
            case 2:
                fragmentToBeDisplayed = new CreatePatientInfoFormVitalFragment();
                break;
            case 3:
                fragmentToBeDisplayed = new CreatePatientInfoFormSocialHistoryFragment();
                break;
            case 4:
                fragmentToBeDisplayed = new CreatePatientInfoFormPrescriptionFragment();
                break;
            case 5:
                fragmentToBeDisplayed = new CreatePatientInfoFormRoutineFragment();
                break;
        }

        return fragmentToBeDisplayed;
    }
}
