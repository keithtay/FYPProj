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
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.RoutineListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Fragment to display the routine form in creating patient
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class CreatePatientInfoFormRoutineFragment extends CreatePatientInfoFormFragment implements PatientInfoFormListFragment {
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

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_patient_info_form_routine, container, false);

        addRoutineExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_routine_expandable_layout);

        nameEditText = (EditText) rootView.findViewById(R.id.routine_name_edit_text);
        notesEditText = (EditText) rootView.findViewById(R.id.routine_notes_edit_text);
        startDatePicker = (EditText) rootView.findViewById(R.id.routine_start_date_picker);
        endDatePicker = (EditText) rootView.findViewById(R.id.routine_end_date_picker);
        startTimePicker = (EditText) rootView.findViewById(R.id.routine_start_time_picker);
        endTimePicker = (EditText) rootView.findViewById(R.id.routine_end_time_picker);
        everyEditText = (EditText) rootView.findViewById(R.id.routine_every_edit_text);
        everySpinner = (Spinner) rootView.findViewById(R.id.routine_every_spinner);

        UtilsUi.setupEditTextToBeDatePicker(startDatePicker, getString(R.string.select_routine_start_date));
        UtilsUi.setupEditTextToBeDatePicker(endDatePicker, getString(R.string.select_routine_end_date));
        UtilsUi.setupEditTextToBeTimePicker(startTimePicker, getString(R.string.select_routine_start_time));
        UtilsUi.setupEditTextToBeTimePicker(endTimePicker, getString(R.string.select_routine_end_time));

        routineList = createdPatient.getRoutineList();
        routineListAdapter = new RoutineListAdapter(getActivity(), this, routineList, createdPatient);
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

                nameEditText.setError(null);
                startDatePicker.setError(null);
                endDatePicker.setError(null);
                startTimePicker.setError(null);
                endTimePicker.setError(null);
                everyEditText.setError(null);
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
        String name = nameEditText.getText().toString();
        String notes = notesEditText.getText().toString();
        String startDateStr = startDatePicker.getText().toString();
        String endDateStr = endDatePicker.getText().toString();
        String startTimeStr = startTimePicker.getText().toString();
        String endTimeStr = endTimePicker.getText().toString();
        String everyNumStr = everyEditText.getText().toString();
        String everyLabel = everySpinner.getSelectedItem().toString();

        // Input checking
        boolean isValidForm = true;
        String errorMessage = getResources().getString(R.string.error_msg_field_required);

        if(UtilsString.isEmpty(name)) {
            nameEditText.setError(errorMessage);
            isValidForm = false;
        }
        if(UtilsString.isEmpty(notes)) {
            notesEditText.setError(errorMessage);
            isValidForm = false;
        }
        DateTime startDate = null;
        if(UtilsString.isEmpty(startDateStr)) {
            startDatePicker.setError(errorMessage);
            isValidForm = false;
        } else {
            startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
        }

        DateTime endDate = null;
        if(UtilsString.isEmpty(endDateStr)) {
            endDatePicker.setError(errorMessage);
            isValidForm = false;
        } else {
            endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
        }

        DateTime startTime = null;
        if(UtilsString.isEmpty(startTimeStr)) {
            startTimePicker.setError(errorMessage);
            isValidForm = false;
        } else {
            startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
        }

        DateTime endTime = null;
        if(UtilsString.isEmpty(endTimeStr)) {
            endTimePicker.setError(errorMessage);
            isValidForm = false;
        } else {
            endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
        }

        Integer everyNum = null;
        if(UtilsString.isEmpty(everyNumStr)) {
            everyEditText.setError(errorMessage);
            isValidForm = false;
        } else {
            everyNum = Integer.parseInt(everyNumStr);
        }

        if(startDate != null && endDate != null) {
            if(startDate.isAfter(endDate)) {
                endDatePicker.setError(activity.getString(R.string.error_msg_end_date_must_be_after_start_date));
                isValidForm = false;
            }
        }

        if(startTime != null && endTime != null) {
            if(startTime.isAfter(endTime)) {
                endTimePicker.setError(activity.getString(R.string.error_msg_end_time_must_be_after_start_time));
                isValidForm = false;
            }
        }

        if(isValidForm) {
            Routine newRoutine = new Routine(name, notes, startDate, endDate, startTime, endTime, everyNum, everyLabel);
            routineList.add(0, newRoutine);
            routineListAdapter.notifyItemInserted(0);

            resetNewRoutineFields();

            closeExpandableAddRoutine();
            hideKeyboard();
        }
    }


    /**
     * Delete an routine from the list
     *
     * @param selectedItemIdx index of routine to be deleted
     */
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