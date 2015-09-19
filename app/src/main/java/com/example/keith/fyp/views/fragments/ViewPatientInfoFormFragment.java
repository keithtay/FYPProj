package com.example.keith.fyp.views.fragments;

import android.view.View;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.CustomField;
import com.example.keith.fyp.views.DateField;
import com.example.keith.fyp.views.SpinnerField;
import com.example.keith.fyp.views.TextField;

import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import javadz.beanutils.PropertyUtils;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class ViewPatientInfoFormFragment extends PatientInfoFormFragment {
    protected Patient viewedPatient;
    protected ArrayList<PatientFormSpec> patientFormSpecs;

    public void init() {
        super.init();
        viewedPatient = DataHolder.getViewedPatient();
        patientFormSpecs = new ArrayList<>();
    }

    protected void prepareForm(View rootView, ArrayList<PatientFormSpec> patientFormSpecs) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for(final PatientFormSpec spec:patientFormSpecs) {
            final String attributeName = spec.getAttributeName();

            switch (spec.getViewClassName()) {
                case TEXT_FIELD:
                    TextField textField = (TextField) rootView.findViewById(spec.getViewId());
                    String attributeValue = (String) PropertyUtils.getProperty(viewedPatient, attributeName);
                    textField.setText(attributeValue);
                    textField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            try {
                                PropertyUtils.setProperty(viewedPatient, attributeName, newValue);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case SPINNER_FIELD_GENDER:
                    final SpinnerField genderSpinnerField = (SpinnerField) rootView.findViewById(spec.getViewId());

                    char genderShort = viewedPatient.getGender();
                    String[] genderShortArray = getResources().getStringArray(R.array.option_gender_short);
                    final ArrayList<String> genderShortArrayList = new ArrayList<String>(Arrays.asList(genderShortArray));
                    int index = genderShortArrayList.indexOf(String.valueOf(genderShort));

                    String[] genderArray = getResources().getStringArray(R.array.option_gender);
                    final ArrayList<String> genderArrayList = new ArrayList<String>(Arrays.asList(genderArray));
                    String genderStr = genderArrayList.get(index);
                    genderSpinnerField.setText(genderStr);
                    genderSpinnerField.setSpinnerItems(getResources().getStringArray(R.array.option_gender));

                    genderSpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
                        @Override
                        public void onSpinnerFieldItemSelected(int index) {
                            genderSpinnerField.changeDisplayedText(genderArrayList.get(index));
                        }
                    });

                    genderSpinnerField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            int index = genderArrayList.indexOf(newValue);
                            viewedPatient.setGender(genderShortArrayList.get(index).charAt(0));
                        }
                    });
                    break;
                case DATE_FIELD:
                    DateField dateField = (DateField) rootView.findViewById(spec.getViewId());
                    DateTime dateTime = (DateTime) PropertyUtils.getProperty(viewedPatient, attributeName);
                    dateField.setText(dateTime.toString(Global.DATE_FORMAT));
                    dateField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            DateTime newDateTime = Global.DATE_FORMAT.parseDateTime(newValue);
                            try {
                                PropertyUtils.setProperty(viewedPatient, attributeName, newDateTime);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
            }
        }
    }
}
