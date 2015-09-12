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


        // Fill the form with the previously added content
        try {
            // Patient attribute name (second attribute) should be the same with the one in Patient class
            patientFormSpecs.add(new PatientFormSpec(R.id.live_with_edit_text, "liveWith", TEXT_VIEW_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.diet_edit_text, "diet", TEXT_VIEW_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.religion_spinner, "religion", SPINNER_RELIGION));
            patientFormSpecs.add(new PatientFormSpec(R.id.sexually_active_spinner, "isSexuallyActive", SPINNER_YES_NO_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.secondhand_smoker_spinner, "isSecondhandSmoker", SPINNER_YES_NO_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.alcohol_use_spinner, "alcoholUse", SPINNER_FREQ_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.caffeine_use_spinner, "caffeineUse", SPINNER_FREQ_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.tobacco_use_spinner, "tobaccoUse", SPINNER_FREQ_SOCIAL_HISTORY));
            patientFormSpecs.add(new PatientFormSpec(R.id.drug_use_spinner, "drugUse", SPINNER_FREQ_SOCIAL_HISTORY));

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