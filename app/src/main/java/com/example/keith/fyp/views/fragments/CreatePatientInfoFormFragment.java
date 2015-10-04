package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;
import javadz.beanutils.PropertyUtils;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class CreatePatientInfoFormFragment extends PatientInfoFormFragment {

    protected Patient createdPatient;
    protected ArrayList<PatientFormSpec> patientFormSpecs;

    public void init() {
        super.init();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedPatientDraftId = mPrefs.getString(Global.STATE_SELECTED_PATIENT_DRAFT_ID, null);
        if (UtilsString.isEmpty(selectedPatientDraftId)) {
            selectedPatientDraftId = getActivity().getIntent().getStringExtra(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID);
        }
        if(!UtilsString.isEmpty(selectedPatientDraftId)) {
            String json = mPrefs.getString(Global.SP_CREATE_PATIENT_DRAFT, "");
            if(!UtilsString.isEmpty(json)) {
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, Patient>>(){}.getType();
                HashMap<String, Patient> draftMap = gson.fromJson(json, type);
                Patient patient = draftMap.get(selectedPatientDraftId);
                DataHolder.setCreatedPatient(patient);
            }
        }

        createdPatient = DataHolder.getCreatedPatient();
        patientFormSpecs = new ArrayList<>();
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
                                char toStoreValue = 0;
                                if (selectedGender.equals("Male")) {
                                    toStoreValue = 'M';
                                } else if (selectedGender.equals("Female")) {
                                    toStoreValue = 'F';
                                }
                                if(toStoreValue != 0) {
                                    PropertyUtils.setProperty(createdPatient, spec.getAttributeName(), toStoreValue);
                                }
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
                        } else if (patientGender == 'F') {
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
                                if (s.toString() != null && !s.toString().isEmpty()) {
                                    DateTime date = Global.DATE_FORMAT.parseDateTime(s.toString());
                                    PropertyUtils.setProperty(createdPatient, spec.getAttributeName(), date);
                                }
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
                        datePicker.setText(dateValue.toString(Global.DATE_FORMAT));
                    }
                    break;
                case TEXT_VIEW_SOCIAL_HISTORY:
                    TextView textViewSocialHistory = (TextView) view;
                    textViewSocialHistory.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                PropertyUtils.setProperty(createdPatient.getSocialHistory(), spec.getAttributeName(), s.toString());
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

                    String attributeValueSocialHistoryStr = (String) PropertyUtils.getProperty(createdPatient.getSocialHistory(), spec.getAttributeName());
                    if (attributeValueSocialHistoryStr != null && !attributeValueSocialHistoryStr.isEmpty()) {
                        textViewSocialHistory.setText(attributeValueSocialHistoryStr);
                    }
                    break;
                case SPINNER_RELIGION:
                    final MaterialSpinner religionSpinner = (MaterialSpinner) view;
                    religionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                String selectedReligion = null;
                                if(religionSpinner.getSelectedItemPosition() != 0) {
                                    selectedReligion = religionSpinner.getSelectedItem().toString();
                                }

                                PropertyUtils.setProperty(createdPatient.getSocialHistory(), spec.getAttributeName(), selectedReligion);
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

                    String patientReligion = (String) PropertyUtils.getProperty(createdPatient.getSocialHistory(), spec.getAttributeName());
                    if (patientReligion != null && !patientReligion.isEmpty()) {
                        String[] religionStrArray = getResources().getStringArray(R.array.option_religion);
                        int idx = Arrays.asList(religionStrArray).indexOf(patientReligion);
                        religionSpinner.setSelection(idx + 1);
                    }
                    break;
                case SPINNER_YES_NO_SOCIAL_HISTORY:
                    final MaterialSpinner yesNoSpinner = (MaterialSpinner) view;
                    yesNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                String yesOrNo = yesNoSpinner.getSelectedItem().toString();
                                Boolean isYes;

                                switch (yesOrNo) {
                                    case "Yes":
                                        isYes = true;
                                        break;
                                    case "No":
                                        isYes = false;
                                        break;
                                    default:
                                        isYes = null;
                                        break;
                                }
                                PropertyUtils.setProperty(createdPatient.getSocialHistory(), spec.getAttributeName(), isYes);

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

                    Boolean isYes = (Boolean) PropertyUtils.getProperty(createdPatient.getSocialHistory(), spec.getAttributeName());
                    if(isYes != null) {
                        if (isYes) {
                            yesNoSpinner.setSelection(1);
                        } else {
                            yesNoSpinner.setSelection(2);
                        }
                    }
                    break;
                case SPINNER_FREQ_SOCIAL_HISTORY:
                    final MaterialSpinner freqSpinner = (MaterialSpinner) view;
                    freqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                String freqStr = null;
                                if(freqSpinner.getSelectedItemPosition() != 0) {
                                    freqStr = freqSpinner.getSelectedItem().toString();
                                }
                                PropertyUtils.setProperty(createdPatient.getSocialHistory(), spec.getAttributeName(), freqStr);
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

                    String patientFreq = (String) PropertyUtils.getProperty(createdPatient.getSocialHistory(), spec.getAttributeName());
                    if (patientFreq != null && !patientFreq.isEmpty()) {
                        String[] freqStrArray = getResources().getStringArray(R.array.option_frequency);
                        int idx = Arrays.asList(freqStrArray).indexOf(patientFreq);
                        freqSpinner.setSelection(idx+1);
                    }
                    break;
            }
        }
    }
}
