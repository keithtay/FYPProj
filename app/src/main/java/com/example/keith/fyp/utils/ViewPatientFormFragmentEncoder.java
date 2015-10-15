package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.views.fragments.CreatePatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPersonalInfoFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPrescriptionFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormRoutineFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormSocialHistoryFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormVitalFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormPersonalInfoFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormPrescriptionFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormProblemLogFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormRoutineFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormSocialHistoryFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormVitalFragment;

/**
 * ViewPatientFormFragmentEncoder is a singleton class that
 * encode an integer ID into a {@link ViewPatientInfoFormFragment}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientFormFragmentEncoder {

    /**
     * Method to get {@link ViewPatientInfoFormFragment} based on the specified integer ID
     *
     * @param index integer ID of the {@link ViewPatientInfoFormFragment} identifier
     * @return encoded {@link ViewPatientInfoFormFragment}
     */
    public static Fragment getFragment(int index) {

        Fragment fragmentToBeDisplayed = null;

        switch(index) {
            case 0:
                fragmentToBeDisplayed = new ViewPatientInfoFormPersonalInfoFragment();
                break;
            case 1:
                fragmentToBeDisplayed = new ViewPatientInfoFormAllergyFragment();
                break;
            case 2:
                fragmentToBeDisplayed = new ViewPatientInfoFormVitalFragment();
                break;
            case 3:
                fragmentToBeDisplayed = new ViewPatientInfoFormSocialHistoryFragment();
                break;
            case 4:
                fragmentToBeDisplayed = new ViewPatientInfoFormPrescriptionFragment();
                break;
            case 5:
                fragmentToBeDisplayed = new ViewPatientInfoFormRoutineFragment();
                break;
            case 6:
                fragmentToBeDisplayed = new ViewPatientInfoFormProblemLogFragment();
                break;
        }

        return fragmentToBeDisplayed;
    }
}
