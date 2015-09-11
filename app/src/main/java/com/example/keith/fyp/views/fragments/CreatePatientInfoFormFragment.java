package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import javadz.beanutils.PropertyUtils;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class CreatePatientInfoFormFragment extends Fragment {
    protected final String TEXT_VIEW = "TextView";
    protected final String SPINNER_GENDER = "SpinnerGender";
    protected final String DATE_PICKER = "DatePicker";

    protected Patient createdPatient;
    protected ArrayList<PatientFormSpec> patientFormSpecs;

    protected final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("d MMM yyyy");

    protected Activity activity;
    protected InputMethodManager inputManager;

    public void init() {
        createdPatient = DataHolder.getCreatedPatient();
        patientFormSpecs = new ArrayList<>();
        activity = getActivity();
        inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    // Fill the form with the previously inserted value and add listener on change value
    protected void prepareForm(View rootView, ArrayList<PatientFormSpec> patientFormSpecs) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (final PatientFormSpec spec : patientFormSpecs) {
            View view = rootView.findViewById(spec.getViewId());
            switch (spec.getViewClassName()) {
                case TEXT_VIEW:
                    TextView textView = (TextView) view;
                    textView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                PropertyUtils.setProperty(createdPatient, spec.getAttributeName(), s.toString());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    String attributeValue = (String) PropertyUtils.getProperty(createdPatient, spec.getAttributeName());
                    if (attributeValue != null && !attributeValue.isEmpty()) {
                        textView.setText(attributeValue);
                    }
                    break;
                case SPINNER_GENDER:
                    final MaterialSpinner spinner = (MaterialSpinner) view;
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                String selectedGender = spinner.getSelectedItem().toString();
                                char toStoreValue = ' ';
                                if (selectedGender.equals("Male")) {
                                    toStoreValue = 'M';
                                } else if(selectedGender.equals("Female")) {
                                    toStoreValue = 'F';
                                }
                                PropertyUtils.setProperty(createdPatient, spec.getAttributeName(), toStoreValue);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    char patientGender = (char) PropertyUtils.getProperty(createdPatient, spec.getAttributeName());
                    if (Character.isLetter(patientGender)) {
                        if (patientGender == 'M') {
                            spinner.setSelection(1);
                        } else {
                            spinner.setSelection(2);
                        }
                    }
                    break;
                case DATE_PICKER:
                    final TextView datePicker = (TextView) view;
                    datePicker.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                DateTime date = dateFormat.parseDateTime(s.toString());
                                PropertyUtils.setProperty(createdPatient, spec.getAttributeName(), date);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    DateTime dateValue = (DateTime) PropertyUtils.getProperty(createdPatient, spec.getAttributeName());
                    if (dateValue != null) {
                        datePicker.setText(dateValue.toString(dateFormat));
                    }
                    break;

            }
        }
    }

    protected void hideKeyboard() {
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
