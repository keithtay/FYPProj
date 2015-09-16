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
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.adapters.RoutineListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class CreatePatientInfoFormRoutineFragment extends CreatePatientInfoFormFragment {
    private LinearLayout rootView;

    private ExpandableLayout addRoutineExpandable;

    private EditText nameEditText;
    private EditText notesEditText;
    private EditText startDatePicker;
    private EditText endDatePicker;
    private EditText startTimePicker;
    private EditText endTimePicker;
    private EditText everyEditText;
    private Spinner everySpinner;

    private ArrayList<Routine> routineList;
    private RoutineListAdapter routineListAdapter;

    private LinearLayout addNewRoutineHeaderContainer;
    private Button cancelNewRoutineButton;
    private Button addNewRoutineButton;
    private RecyclerView routineRecyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_routine, container, false);

        addRoutineExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_routine_expandable_layout);

        nameEditText = (EditText) rootView.findViewById(R.id.routine_name_edit_text);
        notesEditText = (EditText) rootView.findViewById(R.id.routine_notes_edit_text);
        startDatePicker = (EditText) rootView.findViewById(R.id.routine_start_date_picker);
        endDatePicker = (EditText) rootView.findViewById(R.id.routine_end_date_picker);
        startTimePicker = (EditText) rootView.findViewById(R.id.routine_start_time_picker);
        endTimePicker = (EditText) rootView.findViewById(R.id.routine_end_time_picker);
        everyEditText = (EditText) rootView.findViewById(R.id.routine_every_edit_text);
        everySpinner = (Spinner) rootView.findViewById(R.id.routine_every_spinner);

        setupEditTextToBeDatePicker(startDatePicker, getString(R.string.select_routine_start_date));
        setupEditTextToBeDatePicker(endDatePicker, getString(R.string.select_routine_end_date));
        setupEditTextToBeTimePicker(startTimePicker, getString(R.string.select_routine_start_time));
        setupEditTextToBeTimePicker(endTimePicker, getString(R.string.select_routine_end_time));

        routineList = DataHolder.getCreatedPatient().getRoutineList();
        routineListAdapter = new RoutineListAdapter(getActivity(), this, routineList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addNewRoutineHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_routine_header_container);
        addNewRoutineHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addRoutineExpandable.isOpened()) {
                    resetNewRoutineFields();
                }
                return false;
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_every_label, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        everySpinner.setAdapter(adapter);

        routineRecyclerView = (RecyclerView) rootView.findViewById(R.id.routine_recycler_view);
        routineRecyclerView.setLayoutManager(layoutManager);
        routineRecyclerView.setAdapter(routineListAdapter);
        routineRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        cancelNewRoutineButton = (Button) rootView.findViewById(R.id.cancel_add_routine_button);
        cancelNewRoutineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddRoutine();
                resetNewRoutineFields();
            }
        });

        addNewRoutineButton = (Button) rootView.findViewById(R.id.add_new_routine_button);
        addNewRoutineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddRoutine();
            }
        });

        return rootView;
    }

    private void createAndAddRoutine() {
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

        Routine newRoutine = new Routine(name, notes, startDate, endDate, startTime, endTime, everyNum, everyLabel);
        routineList.add(0, newRoutine);
        routineListAdapter.notifyItemInserted(0);

        resetNewRoutineFields();

        closeExpandableAddRoutine();
        hideKeyboard();
    }

    public void deleteItem(int selectedItemIdx) {
        routineList.remove(selectedItemIdx);
        routineListAdapter.notifyItemRemoved(selectedItemIdx);
    }

    private void resetNewRoutineFields() {
        nameEditText.setText(null);
        notesEditText.setText(null);
        startDatePicker.setText(null);
        endDatePicker.setText(null);
        startTimePicker.setText(null);
        endTimePicker.setText(null);
        everyEditText.setText(null);
        everySpinner.setSelection(0);
    }

    private void closeExpandableAddRoutine() {
        if (addRoutineExpandable.isOpened()) {
            addRoutineExpandable.hide();
        }
    }
}