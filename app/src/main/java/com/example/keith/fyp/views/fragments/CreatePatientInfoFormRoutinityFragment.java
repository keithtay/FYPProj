package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.models.Routinity;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.adapters.RoutinityListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class CreatePatientInfoFormRoutinityFragment extends CreatePatientInfoFormFragment {
    private LinearLayout rootView;

    private ExpandableLayout addRoutinityExpandable;

    private EditText nameEditText;
    private EditText notesEditText;
    private EditText startDatePicker;
    private EditText endDatePicker;
    private EditText startTimePicker;
    private EditText endTimePicker;
    private EditText everyEditText;
    private Spinner everySpinner;

    private ArrayList<Routinity> routinityList;
    private RoutinityListAdapter routinityListAdapter;

    private LinearLayout addNewRoutinityHeaderContainer;
    private Button cancelNewRoutinityButton;
    private Button addNewRoutinityButton;
    private RecyclerView routinityRecyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_routinity, container, false);

        addRoutinityExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_routinity_expandable_layout);

        nameEditText = (EditText) rootView.findViewById(R.id.routinity_name_edit_text);
        notesEditText = (EditText) rootView.findViewById(R.id.routinity_notes_edit_text);
        startDatePicker = (EditText) rootView.findViewById(R.id.routinity_start_date_picker);
        endDatePicker = (EditText) rootView.findViewById(R.id.routinity_end_date_picker);
        startTimePicker = (EditText) rootView.findViewById(R.id.routinity_start_time_picker);
        endTimePicker = (EditText) rootView.findViewById(R.id.routinity_end_time_picker);
        everyEditText = (EditText) rootView.findViewById(R.id.routinity_every_edit_text);
        everySpinner = (Spinner) rootView.findViewById(R.id.routinity_every_spinner);

        setupEditTextToBeDatePicker(startDatePicker);
        setupEditTextToBeDatePicker(endDatePicker);
        setupEditTextToBeTimePicker(startTimePicker);
        setupEditTextToBeTimePicker(endTimePicker);

        routinityList = DataHolder.getCreatedPatient().getRoutinityList();
        routinityListAdapter = new RoutinityListAdapter(getActivity(), this, routinityList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addNewRoutinityHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_routinity_header_container);
        addNewRoutinityHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addRoutinityExpandable.isOpened()) {
                    resetNewRoutinityFields();
                }
                return false;
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_every_label, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        everySpinner.setAdapter(adapter);

        routinityRecyclerView = (RecyclerView) rootView.findViewById(R.id.routinity_recycler_view);
        routinityRecyclerView.setLayoutManager(layoutManager);
        routinityRecyclerView.setAdapter(routinityListAdapter);
        routinityRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        cancelNewRoutinityButton = (Button) rootView.findViewById(R.id.cancel_add_routinity_button);
        cancelNewRoutinityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddRoutinity();
                resetNewRoutinityFields();
            }
        });

        addNewRoutinityButton = (Button) rootView.findViewById(R.id.add_new_routinity_button);
        addNewRoutinityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddRoutinity();
            }
        });

        return rootView;
    }

    private void createAndAddRoutinity() {
        // TODO: check for valid entry

        String name = nameEditText.getText().toString();
        String notes = notesEditText.getText().toString();

        String startDateStr = startDatePicker.getText().toString();
        DateTime startDate = null;
        if(startDateStr != null && !startDateStr.isEmpty()) {
            startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
        }

        String endDateStr = endDatePicker.getText().toString();
        DateTime endDate = null;
        if(endDateStr != null && !endDateStr.isEmpty()) {
            endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
        }

        String startTimeStr = startTimePicker.getText().toString();
        DateTime startTime = null;
        if(startTimeStr != null && !startTimeStr.isEmpty()) {
            startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
        }

        String endTimeStr = endTimePicker.getText().toString();
        DateTime endTime = null;
        if(endTimeStr != null && !endTimeStr.isEmpty()) {
            endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
        }

        Integer everyNum = null;
        String everyNumStr = everyEditText.getText().toString();
        if (everyNumStr != null && !everyNumStr.isEmpty()) {
            everyNum = Integer.parseInt(everyNumStr);
        }

        String everyLabel = everySpinner.getSelectedItem().toString();

        Routinity newRoutinity = new Routinity(name, notes, startDate, endDate, startTime, endTime, everyNum, everyLabel);
        routinityList.add(0, newRoutinity);
        routinityListAdapter.notifyItemInserted(0);

        resetNewRoutinityFields();

        closeExpandableAddRoutinity();
        hideKeyboard();
    }

    public void deleteItem(int selectedItemIdx) {
        routinityList.remove(selectedItemIdx);
        routinityListAdapter.notifyItemRemoved(selectedItemIdx);
    }

    private void resetNewRoutinityFields() {
        nameEditText.setText(null);
        notesEditText.setText(null);
        startDatePicker.setText(null);
        endDatePicker.setText(null);
        startTimePicker.setText(null);
        endTimePicker.setText(null);
        everyEditText.setText(null);
        everySpinner.setSelection(0);
    }

    private void closeExpandableAddRoutinity() {
        if (addRoutinityExpandable.isOpened()) {
            addRoutinityExpandable.hide();
        }
    }
}