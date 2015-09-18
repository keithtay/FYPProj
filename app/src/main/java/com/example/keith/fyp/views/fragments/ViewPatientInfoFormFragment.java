package com.example.keith.fyp.views.fragments;

import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class ViewPatientInfoFormFragment extends PatientInfoFormFragment {
    protected Patient viewedPatient;

    public void init() {
        super.init();
        viewedPatient = DataHolder.getViewedPatient();
    }
}
