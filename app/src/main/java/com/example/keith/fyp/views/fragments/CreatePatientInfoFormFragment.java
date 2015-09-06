package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keith.fyp.R;

public class CreatePatientInfoFormFragment extends Fragment {
    TextView patientInfoFormTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_patient_info_form, container, false);
        patientInfoFormTextView = (TextView) view.findViewById(R.id.patientInfoFormTextView);

        return view;
    }

    public void changeData(int index) {

        String[] infoContentList = {
                "Personal Information Content",
                "Emergency Contact Content",
                "Alergy Content",
                "Album Content",
                "Social History Content",
                "Prescription Content",
                "Routinity Content",
                "Vital Content"};

        patientInfoFormTextView.setText(infoContentList[index]);
    }
}
