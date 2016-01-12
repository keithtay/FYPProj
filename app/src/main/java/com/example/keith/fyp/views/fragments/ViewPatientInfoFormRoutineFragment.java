package com.example.keith.fyp.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.RoutineListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Fragment to display the patient's routine information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientInfoFormRoutineFragment extends ViewPatientInfoFormFragment implements PatientInfoFormListFragment {
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

        routineList = viewedPatient.getRoutineList();
        routineListAdapter = new RoutineListAdapter(getActivity(), this, routineList, viewedPatient);
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
            SharedPreferences pref;
            pref = getActivity().getSharedPreferences("Login", 0);
            final int UserID = Integer.parseInt(pref.getString("userid", ""));
            final int UserTypeID = Integer.parseInt(pref.getString("userTypeId",""));
            String info = name + ";" + notes + ";" + startDate.toString() + ";" +
                    endDate.toString() + ";" + startTime.toString()
                    +";"+ endTime.toString() + ";" + String.valueOf(everyNum) + ";" + everyLabel;
            dbfile db = new dbfile();

            int x = db.getPatientId(viewedPatient.getNric());

            db.insertPatientSpec(info, x, 5, UserTypeID,UserID);
            if(UserTypeID ==3) {
                Routine newRoutine = new Routine(name, notes, startDate, endDate, startTime, endTime, everyNum, everyLabel);
                routineList.add(0, newRoutine);
                routineListAdapter.notifyItemInserted(0);
            }else{
                Toast.makeText(getActivity(), "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
            }
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
        String oldValue = routineList.get(selectedItemIdx).getName() + ";" + routineList.get(selectedItemIdx).getNotes() +";" +
                routineList.get(selectedItemIdx).getStartDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toString() + ";" + routineList.get(selectedItemIdx).getEndDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toString()
                + ";" + routineList.get(selectedItemIdx).getStartTime().withDayOfYear(1).withMonthOfYear(1).withYear(1970).withMillisOfSecond(0).toString() + ";" + routineList.get(selectedItemIdx).getEndTime().withYear(1970).withMonthOfYear(1).withMillisOfSecond(0).withDayOfYear(1).toString()
                + ";" + String.valueOf(routineList.get(selectedItemIdx).getEveryNumber()) + ";" + routineList.get(selectedItemIdx).getEveryLabel();
        dbfile db = new dbfile();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        int x = db.getPatientId(selectedPatientNric);
        int getRowID = db.getAllergyRowId(oldValue, x);
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        db.deletePatientSpec(oldValue, x, 5, getRowID, UserTypeID, UserID);
        if(UserTypeID == 3) {
            routineList.remove(selectedItemIdx);
            routineListAdapter.notifyItemRemoved(selectedItemIdx);
            Toast.makeText(getActivity(), "Successfully Changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
        }
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
