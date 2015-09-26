package com.example.keith.fyp.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsDate;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.customviews.TimeRangePicker;
import com.example.keith.fyp.views.adapters.EventArrayAdapter;
import com.example.keith.fyp.views.fragments.TimeRangePickerFragment;

import org.joda.time.DateTime;

public class EditScheduleActivity extends ScheduleActivity implements View.OnClickListener {

    private ListView eventListView;
    private EventArrayAdapter eventListAdapter;
    private Button addEventButton;
    private EditText eventTitleEditText;
    private EditText eventDescriptionEditText;
    private EditText startTimePicker;
    private EditText endTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        init();

        // Hide the container of schedule  action buttons
        View actionContainer = findViewById(R.id.schedule_action_container);
        ((ViewGroup) actionContainer.getParent()).removeView(actionContainer);

        eventListView = (ListView) findViewById(R.id.event_list_list_view);
        eventListAdapter = new EventArrayAdapter(this, eventList);
        eventListView.setAdapter(eventListAdapter);

        updateScheduleListViewHeight();

        addEventButton = (Button) findViewById(R.id.add_event_button);
        addEventButton.setOnClickListener(this);
    }

    public void updateScheduleListViewHeight() {
        UtilsUi.setListViewHeightBasedOnChildren(eventListView);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.dialog_title_add_event);

        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_event, null);

        eventTitleEditText = (EditText) rootView.findViewById(R.id.event_title_edit_text);
        eventDescriptionEditText = (EditText) rootView.findViewById(R.id.event_description_edit_text);
        startTimePicker = (EditText) rootView.findViewById(R.id.event_start_time_picker);
        endTimePicker = (EditText) rootView.findViewById(R.id.event_end_time_picker);

        String dialogTitle = getString(R.string.dialog_set_time_range);
        setupEditTextToBeTimeRangePicker(startTimePicker, dialogTitle);
        setupEditTextToBeTimeRangePicker(endTimePicker, dialogTitle);

        builder.setView(rootView);

        builder.setPositiveButton(R.string.dialog_button_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: check for field values

                String title = eventTitleEditText.getText().toString();
                String description = eventDescriptionEditText.getText().toString();

                String startTimeStr = startTimePicker.getText().toString();
                DateTime startTime = UtilsDate.setCurrentDateToTime(startTimeStr);
                String endTimeStr = endTimePicker.getText().toString();
                DateTime endTime = UtilsDate.setCurrentDateToTime(endTimeStr);

                Event newEvent = new Event(title, description, startTime, endTime);

                if(eventListAdapter.isEventOverlapWithEventInList(newEvent, eventList)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_new_event_overlap, Toast.LENGTH_SHORT).show();
                } else {
                    eventList.add(newEvent);
                    eventListAdapter.sortEventList();
                    eventListAdapter.notifyDataSetChanged();
                    updateScheduleListViewHeight();
                }
            }
        });
        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void setupEditTextToBeTimeRangePicker(final EditText editText, final String title) {
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTime startTime;
                DateTime endTime;

                String prevStartTimeStr = startTimePicker.getText().toString();
                String prevEndTimeStr = endTimePicker.getText().toString();

                if(UtilsString.isEmpty(prevStartTimeStr) && UtilsString.isEmpty(prevEndTimeStr)) {
                    startTime = DateTime.now();
                    endTime = startTime.withHourOfDay(startTime.plusHours(1).getHourOfDay());
                } else {
                    startTime = UtilsDate.setCurrentDateToTime(prevStartTimeStr);
                    endTime = UtilsDate.setCurrentDateToTime(prevEndTimeStr);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();

                TimeRangePicker.make(
                        title,
                        startTime,
                        endTime,
                        new TimeRangePickerFragment.OnTimeRangeSetListener() {
                            @Override
                            public void timeRangeSet(DateTime newStartTime, DateTime newEndTime) {
                                String startTimeStr = newStartTime.toString(Global.TIME_FORMAT);
                                startTimePicker.setText(startTimeStr);

                                String endTimeStr = newEndTime.toString(Global.TIME_FORMAT);
                                endTimePicker.setText(endTimeStr);
                            }
                        },
                        fragmentManager
                ).show();
            }
        });
    }
}
