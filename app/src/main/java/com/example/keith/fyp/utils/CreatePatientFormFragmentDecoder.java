package com.example.keith.fyp.utils;

import android.app.Fragment;

import com.example.keith.fyp.views.fragments.CreatePatientInfoFormEmergencyContactFragment;
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
                fragmentToBeDisplayed = new CreatePatientInfoFormEmergencyContactFragment();
                break;
            case 2:
                fragmentToBeDisplayed = new CreatePatientInfoFormPersonalInfoFragment();
                break;
            case 3:
                fragmentToBeDisplayed = new CreatePatientInfoFormEmergencyContactFragment();
                break;
            case 4:
                fragmentToBeDisplayed = new CreatePatientInfoFormPersonalInfoFragment();
                break;
            case 5:
                fragmentToBeDisplayed = new CreatePatientInfoFormEmergencyContactFragment();
                break;
            case 6:
                fragmentToBeDisplayed = new CreatePatientInfoFormPersonalInfoFragment();
                break;
            case 7:
                fragmentToBeDisplayed = new CreatePatientInfoFormEmergencyContactFragment();
                break;
        }

        return fragmentToBeDisplayed;
    }
}
