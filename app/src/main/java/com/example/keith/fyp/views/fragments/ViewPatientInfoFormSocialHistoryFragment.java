package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;

import java.lang.reflect.InvocationTargetException;

/**
 * Fragment to display the patient's social history information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientInfoFormSocialHistoryFragment extends ViewPatientInfoFormFragment  {

    private LinearLayout rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_info_form_social_history, container, false);

        // Patient attribute name (second attribute) should be the same with the one in Patient class
        patientFormSpecs.add(new PatientFormSpec(R.id.live_with_text_field, "liveWith", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.diet_text_field, "diet", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.religion_spinner_field, "religion", SPINNER_FIELD_RELIGION));
        patientFormSpecs.add(new PatientFormSpec(R.id.sexually_active_spinner_field, "isSexuallyActive", SPINNER_FIELD_YES_NO_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.secondhand_smoker_spinner_field, "isSecondhandSmoker", SPINNER_FIELD_YES_NO_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.alcohol_use_spinner_field, "alcoholUse", SPINNER_FIELD_FREQ_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.caffeine_use_spinner_field, "caffeineUse", SPINNER_FIELD_FREQ_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.tobacco_use_spinner_field, "tobaccoUse", SPINNER_FIELD_FREQ_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.drug_use_spinner_field, "drugUse", SPINNER_FIELD_FREQ_SOCIAL_HISTORY));

        patientFormSpecs.add(new PatientFormSpec(R.id.pet_text_field, "pet", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.occupation_text_field, "occupation", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.like_text_field, "like", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.dislike_text_field, "dislike", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.hobby_text_field, "hobby", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.habbit_text_field, "habbit", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.holiday_experience_text_field, "holidayExperience", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.education_text_field, "education", TEXT_FIELD_SOCIAL_HISTORY));
        patientFormSpecs.add(new PatientFormSpec(R.id.exercise_text_field, "exercise", TEXT_FIELD_SOCIAL_HISTORY));

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
