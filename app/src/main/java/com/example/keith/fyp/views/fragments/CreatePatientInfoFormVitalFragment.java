package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;

import org.joda.time.DateTime;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class CreatePatientInfoFormVitalFragment extends CreatePatientInfoFormFragment {
    private LinearLayout rootView;
    private LinearLayout addNewVitalHeaderContainer;
    private ExpandableLayout addVitalExpandable;
    private EditText vitalDateTakenTextView;
    private EditText vitalTimeTakenTextView;
    private MaterialSpinner vitalLabelSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_vital, container, false);

        vitalDateTakenTextView = (EditText) rootView.findViewById(R.id.vital_date_picker);
        setupEditTextToBeDatePicker(vitalDateTakenTextView);

        vitalTimeTakenTextView = (EditText) rootView.findViewById(R.id.vital_time_picker);
        setupEditTextToBeTimePicker(vitalTimeTakenTextView);

        addVitalExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_vital_expandable_layout);

        addNewVitalHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_vital_header_container);
        addNewVitalHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!addVitalExpandable.isOpened()) {
                    // TODO: fill in current date and time
                    DateTime currentDateTime = DateTime.now();
                    String currentDateStr = currentDateTime.toString(dateFormat);
                    String currentTimeStr = currentDateTime.toString(timeFormat);
                    vitalDateTakenTextView.setText(currentDateStr);
                    vitalTimeTakenTextView.setText(currentTimeStr);
                } else {
                    // TODO: reset all field
                    vitalDateTakenTextView.setText(null);
                    vitalTimeTakenTextView.setText(null);
                }
                return false;
            }
        });

        vitalLabelSpinner = (MaterialSpinner) rootView.findViewById(R.id.vital_label_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_vital_label, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vitalLabelSpinner.setAdapter(adapter);

        return rootView;
    }
}