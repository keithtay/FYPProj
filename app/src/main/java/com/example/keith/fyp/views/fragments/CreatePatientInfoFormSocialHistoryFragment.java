package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;

import java.lang.reflect.InvocationTargetException;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class CreatePatientInfoFormSocialHistoryFragment extends CreatePatientInfoFormFragment {

    private LinearLayout rootView;

    private MaterialSpinner religionSpinner;
    private MaterialSpinner sexuallyActiveSpinner;
    private MaterialSpinner secondhandSomkerSpinner;
    private MaterialSpinner alcoholUseSpinner;
    private MaterialSpinner caffeineUseSpinner;
    private MaterialSpinner tobaccoUseSpinner;
    private MaterialSpinner drugUseSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_social_history, container, false);

        religionSpinner = (MaterialSpinner) rootView.findViewById(R.id.religion_spinner);
        ArrayAdapter<CharSequence> religionAdapter = ArrayAdapter.createFromResource(activity,
                R.array.option_religion, android.R.layout.simple_spinner_item);
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religionSpinner.setAdapter(religionAdapter);



        ArrayAdapter<CharSequence> yesNoAdapter = ArrayAdapter.createFromResource(activity,
                R.array.option_yes_no, android.R.layout.simple_spinner_item);
        yesNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexuallyActiveSpinner = (MaterialSpinner) rootView.findViewById(R.id.sexually_active_spinner);
        sexuallyActiveSpinner.setAdapter(yesNoAdapter);

        secondhandSomkerSpinner = (MaterialSpinner) rootView.findViewById(R.id.secondhand_smoker_spinner);
        secondhandSomkerSpinner.setAdapter(yesNoAdapter);



        ArrayAdapter<CharSequence> frequencyAdapter = ArrayAdapter.createFromResource(activity,
                R.array.option_frequency, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        alcoholUseSpinner = (MaterialSpinner) rootView.findViewById(R.id.alcohol_use_spinner);
        alcoholUseSpinner.setAdapter(frequencyAdapter);

        caffeineUseSpinner = (MaterialSpinner) rootView.findViewById(R.id.caffeine_use_spinner);
        caffeineUseSpinner.setAdapter(frequencyAdapter);

        tobaccoUseSpinner = (MaterialSpinner) rootView.findViewById(R.id.tobacco_use_spinner);
        tobaccoUseSpinner.setAdapter(frequencyAdapter);

        drugUseSpinner = (MaterialSpinner) rootView.findViewById(R.id.drug_use_spinner);
        drugUseSpinner.setAdapter(frequencyAdapter);

        return rootView;
    }
}