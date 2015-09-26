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
import com.example.keith.fyp.models.DefaultSchedule;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.DefaultScheduleListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


public class CareCenterConfigDefaultScheduleFragment extends Fragment {

    private View rootView;

    private ExpandableLayout addDefaultScheduleExpandable;

    private EditText nameEditText;
    private EditText startTimePicker;
    private EditText endTimePicker;
    private EditText everyEditText;
    private Spinner everySpinner;
    private MaterialSpinner startDaySpinner;

    private ArrayList<DefaultSchedule> defaultScheduleList;
    private DefaultScheduleListAdapter defaultScheduleListAdapter;

    private LinearLayout addNewRoutineHeaderContainer;
    private Button cancelNewDefaultScheduleButton;
    private Button addNewDefaultScheduleButton;
    private RecyclerView defaultScheduleRecyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_care_center_config_default_schedule, container, false);

        addDefaultScheduleExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_default_schedule_expandable_layout);

        nameEditText = (EditText) rootView.findViewById(R.id.default_schedule_name_edit_text);
        startTimePicker = (EditText) rootView.findViewById(R.id.default_schedule_start_time_picker);
        endTimePicker = (EditText) rootView.findViewById(R.id.default_schedule_end_time_picker);
        everyEditText = (EditText) rootView.findViewById(R.id.default_schedule_every_edit_text);
        everySpinner = (Spinner) rootView.findViewById(R.id.default_schedule_every_spinner);
        startDaySpinner = (MaterialSpinner) rootView.findViewById(R.id.default_schedule_start_day_spinner);

        UtilsUi.setupEditTextToBeTimePicker(startTimePicker, getString(R.string.select_routine_start_time));
        UtilsUi.setupEditTextToBeTimePicker(endTimePicker, getString(R.string.select_routine_end_time));

        defaultScheduleList = getDefaultScheduleList();
        defaultScheduleListAdapter = new DefaultScheduleListAdapter(getActivity(), this, defaultScheduleList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addNewRoutineHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_default_schedule_header_container);
        addNewRoutineHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addDefaultScheduleExpandable.isOpened()) {
                    resetNewDefaultScheduleFields();
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

        defaultScheduleRecyclerView = (RecyclerView) rootView.findViewById(R.id.default_schedule_recycler_view);
        defaultScheduleRecyclerView.setLayoutManager(layoutManager);
        defaultScheduleRecyclerView.setAdapter(defaultScheduleListAdapter);
        defaultScheduleRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        cancelNewDefaultScheduleButton = (Button) rootView.findViewById(R.id.cancel_add_default_schedule_button);
        cancelNewDefaultScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddDefaultSchedule();
                resetNewDefaultScheduleFields();
            }
        });

        addNewDefaultScheduleButton = (Button) rootView.findViewById(R.id.add_new_default_schedule_button);
        addNewDefaultScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddDefaultSchedule();
            }
        });

        return rootView;
    }

    private void createAndAddDefaultSchedule() {
        // TODO: check for valid entry

        String name = nameEditText.getText().toString();

        String startTimeStr = startTimePicker.getText().toString();
        DateTime startTime = null;
        if(!UtilsString.isEmpty(startTimeStr)) {
            startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
        }

        String endTimeStr = endTimePicker.getText().toString();
        DateTime endTime = null;
        if(!UtilsString.isEmpty(endTimeStr)) {
            endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
        }

        Integer everyNum = null;
        String everyNumStr = everyEditText.getText().toString();
        if (!UtilsString.isEmpty(everyNumStr)) {
            everyNum = Integer.parseInt(everyNumStr);
        }

        String everyLabel = everySpinner.getSelectedItem().toString();
        String startDay = startDaySpinner.getSelectedItem().toString();

        DefaultSchedule newDefaultSchedule = new DefaultSchedule(name, startTime, endTime, everyNum, everyLabel, startDay);
        defaultScheduleList.add(0, newDefaultSchedule);
        defaultScheduleListAdapter.notifyItemInserted(0);

        resetNewDefaultScheduleFields();

        closeExpandableAddDefaultSchedule();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void deleteItem(int selectedItemIdx) {
        defaultScheduleList.remove(selectedItemIdx);
        defaultScheduleListAdapter.notifyItemRemoved(selectedItemIdx);
    }

    private void resetNewDefaultScheduleFields() {
        nameEditText.setText(null);
        startTimePicker.setText(null);
        endTimePicker.setText(null);
        everyEditText.setText(null);
        everySpinner.setSelection(0);
        startDaySpinner.setSelection(0);
    }

    private void closeExpandableAddDefaultSchedule() {
        if (addDefaultScheduleExpandable.isOpened()) {
            addDefaultScheduleExpandable.hide();
        }
    }

    public ArrayList<DefaultSchedule> getDefaultScheduleList() {
        ArrayList<DefaultSchedule> defaultScheduleList = DataHolder.getDefaultScheduleList();

        if(defaultScheduleList == null) {
            defaultScheduleList = new ArrayList<>();

            defaultScheduleList.add(new DefaultSchedule("Lunch",
                    DateTime.now().withHourOfDay(12).withMinuteOfHour(0),
                    DateTime.now().withHourOfDay(13).withMinuteOfHour(0),
                    1,
                    "Day",
                    null));

            defaultScheduleList.add(new DefaultSchedule("Tea Break",
                    DateTime.now().withHourOfDay(16).withMinuteOfHour(0),
                    DateTime.now().withHourOfDay(16).withMinuteOfHour(30),
                    2,
                    "Day",
                    "Monday"));

            defaultScheduleList.add(new DefaultSchedule("Aerobics",
                    DateTime.now().withHourOfDay(9).withMinuteOfHour(0),
                    DateTime.now().withHourOfDay(10).withMinuteOfHour(0),
                    1,
                    "Week",
                    "Wednesday"));
        }

        return defaultScheduleList;
    }
}
