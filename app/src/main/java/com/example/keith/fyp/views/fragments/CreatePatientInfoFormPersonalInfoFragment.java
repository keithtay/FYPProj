package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CreatePatientInfoFormPersonalInfoFragment extends CreatePatientInfoFormFragment implements Serializable {

    private InputMethodManager inputManager;

    private Activity activity;

    private LinearLayout rootView;
    private MaterialSpinner spinner;
    private EditText phoneNumberTextView;
    private EditText dobTextView;

    private boolean initialDisplay = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        activity = getActivity();
        inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_personal_info, container, false);

        patientFormSpecs.add(new PatientFormSpec(R.id.first_name_text_view, "name", TEXT_VIEW));
        patientFormSpecs.add(new PatientFormSpec(R.id.nric_text_view, "nric", TEXT_VIEW));

        dobTextView = (EditText) rootView.findViewById(R.id.dob_text_view);
        dobTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {

                    int mYear = 0;
                    int mMonth = 0;
                    int mDay = 0;

                    final DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("d MMM yyyy");
                    String prevSelectedDateStr = dobTextView.getText().toString();

                    if(prevSelectedDateStr == null || prevSelectedDateStr.isEmpty()) {
                        Calendar mcurrentDate = Calendar.getInstance();
                        mYear = mcurrentDate.get(Calendar.YEAR);
                        mMonth = mcurrentDate.get(Calendar.MONTH);
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                    } else {
                        DateTime date = dateTimeFormat.parseDateTime(prevSelectedDateStr);
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

                            String selectedDateStr = date.toString(dateTimeFormat);
                            dobTextView.setText(selectedDateStr);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                return false;
            }
        });

        spinner = (MaterialSpinner) rootView.findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (initialDisplay) {
                    initialDisplay = false;
                    return;
                }
                dobTextView.requestFocus();
                dobTextView.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        phoneNumberTextView = (EditText) rootView.findViewById(R.id.phone_number_text_view);
        phoneNumberTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    hideKeyboard();
                    textView.clearFocus();
                    spinner.requestFocus();
                    spinner.performClick();
                }
                return true;
            }
        });

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

    private void hideKeyboard() {
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
