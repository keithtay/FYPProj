package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.keith.fyp.R;

import java.io.Serializable;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class CreatePatientInfoFormEmergencyContactFragment extends CreatePatientInfoFormFragment implements Serializable {
    LinearLayout rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_emergency_contact, container, false);

        return rootView;
    }

}