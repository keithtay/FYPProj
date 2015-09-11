package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.views.fragments.CreatePatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormVitalFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPersonalInfoFragment;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class CreatePatientFormFragmentDecoder {
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
                fragmentToBeDisplayed = new CreatePatientInfoFormVitalFragment();
                break;
            case 4:
                fragmentToBeDisplayed = new CreatePatientInfoFormPersonalInfoFragment();
                break;
            case 5:
                fragmentToBeDisplayed = new CreatePatientInfoFormVitalFragment();
                break;
        }

        return fragmentToBeDisplayed;
    }
}
