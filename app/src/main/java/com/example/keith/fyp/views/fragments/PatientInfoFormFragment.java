package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.example.keith.fyp.utils.DataHolder;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class PatientInfoFormFragment extends Fragment {
    protected final String TEXT_VIEW = "TextView";
    protected final String TEXT_VIEW_SOCIAL_HISTORY = "TextViewSocialHistory";
    protected final String SPINNER_GENDER = "SpinnerGender";
    protected final String SPINNER_RELIGION = "SpinnerReligion";
    protected final String SPINNER_YES_NO_SOCIAL_HISTORY = "SpinnerYesNoSocialHistory";
    protected final String SPINNER_FREQ_SOCIAL_HISTORY = "SpinnerFrequencySocialHistory";
    protected final String DATE_PICKER = "DatePicker";

    protected final String TEXT_FIELD = "TextField";
    protected final String TEXT_FIELD_SOCIAL_HISTORY = "TextFieldSocialHistory";
    protected final String SPINNER_FIELD_GENDER = "SpinnerFieldGender";
    protected final String DATE_FIELD = "DateField";
    protected final String SPINNER_FIELD_RELIGION = "SpinnerFieldReligion";
    protected final String SPINNER_FIELD_YES_NO_SOCIAL_HISTORY = "SpinnerFieldYesNoSocialHistory";
    protected final String SPINNER_FIELD_FREQ_SOCIAL_HISTORY = "SpinnerFieldFrequencySocialHistory";

    protected Activity activity;
    protected InputMethodManager inputManager;

    public void init() {
        activity = getActivity();
        inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    protected void hideKeyboard() {
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
