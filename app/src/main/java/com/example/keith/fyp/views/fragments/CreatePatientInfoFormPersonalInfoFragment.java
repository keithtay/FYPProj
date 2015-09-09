package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class CreatePatientInfoFormPersonalInfoFragment extends CreatePatientInfoFormFragment implements Serializable {
    LinearLayout rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_personal_info, container, false);

        patientFormSpecs.add(new PatientFormSpec(R.id.name_text_view, "name", TEXT_VIEW));
        patientFormSpecs.add(new PatientFormSpec(R.id.nric_text_view, "nric", TEXT_VIEW));

        try {
            prepareForm(rootView, patientFormSpecs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
