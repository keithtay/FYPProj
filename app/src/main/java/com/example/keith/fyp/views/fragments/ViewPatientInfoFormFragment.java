package com.example.keith.fyp.views.fragments;

import android.view.View;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.customviews.CustomField;
import com.example.keith.fyp.views.customviews.DateField;
import com.example.keith.fyp.views.customviews.SpinnerField;
import com.example.keith.fyp.views.customviews.TextField;

import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
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
                case TEXT_FIELD_SOCIAL_HISTORY:
                    TextField textFieldSocHis = (TextField) rootView.findViewById(spec.getViewId());
                    String attributeValueSocHis = (String) PropertyUtils.getProperty(viewedPatient.getSocialHistory(), attributeName);
                    textFieldSocHis.setText(attributeValueSocHis);
                    textFieldSocHis.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            try {
                                PropertyUtils.setProperty(viewedPatient.getSocialHistory(), attributeName, newValue);
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
                case SPINNER_FIELD_RELIGION:
                    final SpinnerField religionSpinnerField = (SpinnerField) rootView.findViewById(spec.getViewId());
                    religionSpinnerField.setText(viewedPatient.getSocialHistory().getReligion());
                    religionSpinnerField.setSpinnerItems(getResources().getStringArray(R.array.option_religion));

                    String[] religionArray = getResources().getStringArray(R.array.option_religion);
                    final ArrayList<String> religionArrayList = new ArrayList<String>(Arrays.asList(religionArray));

                    religionSpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
                        @Override
                        public void onSpinnerFieldItemSelected(int index) {
                            religionSpinnerField.changeDisplayedText(religionArrayList.get(index));
                        }
                    });

                    religionSpinnerField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            viewedPatient.getSocialHistory().setReligion(newValue);
                        }
                    });
                    break;
                case SPINNER_FIELD_YES_NO_SOCIAL_HISTORY:
                    final SpinnerField yesNoSpinnerField = (SpinnerField) rootView.findViewById(spec.getViewId());
                    Boolean bool = (Boolean) PropertyUtils.getProperty(viewedPatient.getSocialHistory(), spec.getAttributeName());

                    String[] yesNoArray = getResources().getStringArray(R.array.option_yes_no);
                    final ArrayList<String> yesNoArrayList = new ArrayList<String>(Arrays.asList(yesNoArray));

                    if(bool != null) {
                        if(bool) {
                            yesNoSpinnerField.setText(yesNoArray[0]);
                        } else {
                            yesNoSpinnerField.setText(yesNoArray[1]);
                        }
                    }

                    yesNoSpinnerField.setSpinnerItems(getResources().getStringArray(R.array.option_yes_no));

                    yesNoSpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
                        @Override
                        public void onSpinnerFieldItemSelected(int index) {
                            yesNoSpinnerField.changeDisplayedText(yesNoArrayList.get(index));
                        }
                    });

                    yesNoSpinnerField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            int index = yesNoArrayList.indexOf(newValue);
                            boolean value = index == 0 ? true : false;
                            try {
                                PropertyUtils.setProperty(viewedPatient.getSocialHistory(), spec.getAttributeName(), value);
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
                case SPINNER_FIELD_FREQ_SOCIAL_HISTORY:
                    final SpinnerField freqSpinnerField = (SpinnerField) rootView.findViewById(spec.getViewId());

                    String freqValue = (String) PropertyUtils.getProperty(viewedPatient.getSocialHistory(), spec.getAttributeName());
                    freqSpinnerField.setText(freqValue);
                    freqSpinnerField.setSpinnerItems(getResources().getStringArray(R.array.option_frequency));

                    String[] freqArray = getResources().getStringArray(R.array.option_frequency);
                    final ArrayList<String> freqArrayList = new ArrayList<String>(Arrays.asList(freqArray));

                    freqSpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
                        @Override
                        public void onSpinnerFieldItemSelected(int index) {
                            freqSpinnerField.changeDisplayedText(freqArrayList.get(index));
                        }
                    });

                    freqSpinnerField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
                        @Override
                        public void onCustomFieldSave(String newValue) {
                            try {
                                PropertyUtils.setProperty(viewedPatient.getSocialHistory(), spec.getAttributeName(), newValue);
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
