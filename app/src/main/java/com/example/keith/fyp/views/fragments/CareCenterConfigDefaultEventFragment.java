package com.example.keith.fyp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.DefaultEventListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the care centre default event configuration
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class CareCenterConfigDefaultEventFragment extends Fragment {

    private View rootView;

    private ExpandableLayout addDefaultEventExpandable;

    private EditText nameEditText;
    private EditText startTimePicker;
    private EditText endTimePicker;
    private EditText everyEditText;
    private Spinner everySpinner;
    private MaterialSpinner startDaySpinner;

    private ArrayList<DefaultEvent> defaultEventList;
    private DefaultEventListAdapter defaultEventListAdapter;

    private LinearLayout addNewRoutineHeaderContainer;
    private Button cancelNewDefaultEventButton;
    private Button addNewDefaultEventButton;
    private RecyclerView defaultEventRecyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_care_center_config_default_event, container, false);

        addDefaultEventExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_default_event_expandable_layout);

        nameEditText = (EditText) rootView.findViewById(R.id.default_event_name_edit_text);
        startTimePicker = (EditText) rootView.findViewById(R.id.default_event_start_time_picker);
        endTimePicker = (EditText) rootView.findViewById(R.id.default_event_end_time_picker);
        everyEditText = (EditText) rootView.findViewById(R.id.default_event_every_edit_text);
        everySpinner = (Spinner) rootView.findViewById(R.id.default_event_every_spinner);
        startDaySpinner = (MaterialSpinner) rootView.findViewById(R.id.default_event_start_day_spinner);

        UtilsUi.setupEditTextToBeTimePicker(startTimePicker, getString(R.string.select_routine_start_time));
        UtilsUi.setupEditTextToBeTimePicker(endTimePicker, getString(R.string.select_routine_end_time));

        defaultEventList = getDefaultEventList();
        defaultEventListAdapter = new DefaultEventListAdapter(getActivity(), this, defaultEventList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addNewRoutineHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_default_event_header_container);
        addNewRoutineHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addDefaultEventExpandable.isOpened()) {
                    resetNewDefaultEventFields();
                }
                return false;
            }
        });

        ArrayAdapter<CharSequence> everyAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.option_every_label, android.R.layout.simple_spinner_item);
        everyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        everySpinner.setAdapter(everyAdapter);

        ArrayAdapter<CharSequence> startDayAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.option_day, android.R.layout.simple_spinner_item);
        startDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startDaySpinner.setAdapter(startDayAdapter);

        defaultEventRecyclerView = (RecyclerView) rootView.findViewById(R.id.default_event_recycler_view);
        defaultEventRecyclerView.setLayoutManager(layoutManager);
        defaultEventRecyclerView.setAdapter(defaultEventListAdapter);
        defaultEventRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        cancelNewDefaultEventButton = (Button) rootView.findViewById(R.id.cancel_add_default_event_button);
        cancelNewDefaultEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddDefaultEvent();
                resetNewDefaultEventFields();

                nameEditText.setError(null);
                startDaySpinner.setError(null);
                startTimePicker.setError(null);
                endTimePicker.setError(null);
                everyEditText.setError(null);
            }
        });

        addNewDefaultEventButton = (Button) rootView.findViewById(R.id.add_new_default_event_button);
        addNewDefaultEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddDefaultEvent();
            }
        });

        return rootView;
    }

    private void createAndAddDefaultEvent() {
        String name = nameEditText.getText().toString();
        String startTimeStr = startTimePicker.getText().toString();
        String endTimeStr = endTimePicker.getText().toString();
        String everyNumStr = everyEditText.getText().toString();
        String everyLabel = everySpinner.getSelectedItem().toString();
        int startDayPosition = startDaySpinner.getSelectedItemPosition();
        String startDay = startDaySpinner.getSelectedItem().toString();

        // Input checking
        boolean isValidForm = true;
        String errorMsg = getResources().getString(R.string.error_msg_field_required);

        if (UtilsString.isEmpty(name)) {
            nameEditText.setError(errorMsg);
            isValidForm = false;
        }

        if (startDayPosition == 0) {
            startDaySpinner.setError(errorMsg);
            isValidForm = false;
        }

        DateTime startTime = null;
        if (UtilsString.isEmpty(startTimeStr)) {
            startTimePicker.setError(errorMsg);
            isValidForm = false;
        } else {
            startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
        }

        DateTime endTime = null;
        if (UtilsString.isEmpty(endTimeStr)) {
            endTimePicker.setError(errorMsg);
            isValidForm = false;
        } else {
            endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
        }

        if (UtilsString.isEmpty(everyNumStr)) {
            everyEditText.setError(errorMsg);
            isValidForm = false;
        }

        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                endTimePicker.setError(getResources().getString(R.string.error_msg_end_time_must_be_after_start_time));
            }
        }

        if (isValidForm) {
            Integer everyNum = Integer.parseInt(everyNumStr);

            DefaultEvent newDefaultEvent = new DefaultEvent(name, startTime, endTime, everyNum, everyLabel, startDay);
            defaultEventList.add(0, newDefaultEvent);
            defaultEventListAdapter.notifyItemInserted(0);

            resetNewDefaultEventFields();

            closeExpandableAddDefaultEvent();
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Delete a default event from the list
     *
     * @param selectedItemIdx index of event to be deleted
     */
    public void deleteItem(int selectedItemIdx) {
        defaultEventList.remove(selectedItemIdx);
        defaultEventListAdapter.notifyItemRemoved(selectedItemIdx);
    }

    private void resetNewDefaultEventFields() {
        nameEditText.setText(null);
        startTimePicker.setText(null);
        endTimePicker.setText(null);
        everyEditText.setText(null);
        everySpinner.setSelection(0);
        startDaySpinner.setSelection(0);
    }

    private void closeExpandableAddDefaultEvent() {
        if (addDefaultEventExpandable.isOpened()) {
            addDefaultEventExpandable.hide();
        }
    }

    private ArrayList<DefaultEvent> getDefaultEventList() {
        ArrayList<DefaultEvent> defaultEventList = DataHolder.getDefaultEventList();

        if (defaultEventList == null) {
            defaultEventList = new ArrayList<>();

            defaultEventList.add(new DefaultEvent("Lunch",
                    DateTime.now().withHourOfDay(12).withMinuteOfHour(0),
                    DateTime.now().withHourOfDay(13).withMinuteOfHour(0),
                    1,
                    "Day",
                    null));

            defaultEventList.add(new DefaultEvent("Tea Break",
                    DateTime.now().withHourOfDay(16).withMinuteOfHour(0),
                    DateTime.now().withHourOfDay(16).withMinuteOfHour(30),
                    2,
                    "Day",
                    "Monday"));

            defaultEventList.add(new DefaultEvent("Aerobics",
                    DateTime.now().withHourOfDay(9).withMinuteOfHour(0),
                    DateTime.now().withHourOfDay(10).withMinuteOfHour(0),
                    1,
                    "Week",
                    "Wednesday"));
        }

        return defaultEventList;
    }
}
