package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.views.fragments.CreatePatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPersonalInfoFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPrescriptionFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormRoutineFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormSocialHistoryFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormVitalFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormPersonalInfoFragment;
import com.example.keith.fyp.views.fragments.ViewPatientInfoFormVitalFragment;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class ViewPatientFormFragmentDecoder {
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
