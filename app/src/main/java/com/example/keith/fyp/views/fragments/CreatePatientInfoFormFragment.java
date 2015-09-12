package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
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

import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;

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
                                if(s.toString() != null && !s.toString().isEmpty()) {
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

            }
        }
    }

    protected void hideKeyboard() {
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void setupEditTextToBeDatePicker(final EditText editText) {
        editText.setFocusable(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear;
                int mMonth;
                int mDay;

                String prevSelectedDateStr = editText.getText().toString();

                if (prevSelectedDateStr == null || prevSelectedDateStr.isEmpty()) {
                    Calendar mcurrentDate = Calendar.getInstance();
                    mYear = mcurrentDate.get(Calendar.YEAR);
                    mMonth = mcurrentDate.get(Calendar.MONTH);
                    mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                } else {
                    DateTime date = Global.DATE_FORMAT.parseDateTime(prevSelectedDateStr);
                    mYear = date.getYear();
                    mMonth = date.getMonthOfYear();
                    mDay = date.getDayOfMonth();
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        DateTime date = DateTime.now();

                        date = date.withDayOfMonth(selectedDay);
                        date = date.withMonthOfYear(selectedMonth);
                        date = date.withYear(selectedYear);

                        String selectedDateStr = date.toString(Global.DATE_FORMAT);
                        editText.setText(selectedDateStr);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.setTitle(activity.getString(R.string.select_date_of_birth));
                datePickerDialog.show();
            }
        });
    }

    public void setupEditTextToBeTimePicker(final EditText editText) {
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHour;
                int mMinutes;

                String prevSelectedTimeStr = editText.getText().toString();

                if (prevSelectedTimeStr == null || prevSelectedTimeStr.isEmpty()) {
                    Calendar mCurrentTime = Calendar.getInstance();
                    mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                    mMinutes = mCurrentTime.get(Calendar.MINUTE);
                } else {
                    DateTime time = Global.TIME_FORMAT.parseDateTime(prevSelectedTimeStr);
                    mHour = time.getHourOfDay();
                    mMinutes = time.getMinuteOfHour();
                }

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DateTime time = DateTime.now();

                        time = time.withHourOfDay(hourOfDay);
                        time = time.withMinuteOfHour(minute);

                        String selectedTimeStr = time.toString(Global.TIME_FORMAT);
                        editText.setText(selectedTimeStr);
                    }
                }, mHour, mMinutes, true);

                timePickerDialog.setTitle(activity.getString(R.string.select_time_add_new_vital));
                timePickerDialog.show();
            }
        });
    }
}
