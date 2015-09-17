package com.example.keith.fyp.views.adapters;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.utils.UtilsThread;
import com.example.keith.fyp.views.TimeRangePicker;
import com.example.keith.fyp.views.activities.EditScheduleActivity;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.TimeRangePickerFragment;

import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Sutrisno on 16/9/2015.
 */
public class EventArrayAdapter extends BaseAdapter {

    private List<Event> eventList = Collections.emptyList();
    private EditScheduleActivity activity;

    public EventArrayAdapter(EditScheduleActivity activity) {
        this.activity = activity;
    }

    public EventArrayAdapter(EditScheduleActivity activity, List<Event> eventList) {
        this.activity = activity;
        this.eventList = eventList;
    }

    public void updateEventList(List<Event> eventList) {
        UtilsThread.checkOnMainThread();
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Event getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View eventView = convertView;

        if (eventView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(activity);
            eventView = vi.inflate(R.layout.event_layout_editable, null);
        }

        final Event event = getItem(position);

        if (event != null) {
            TextView startTimeTextView = (TextView) eventView.findViewById(R.id.event_start_time_text_view);
            TextView durationTextView = (TextView) eventView.findViewById(R.id.event_duration_text_view);
            TextView titleTextView = (TextView) eventView.findViewById(R.id.event_title_text_view);
            TextView descriptionTextView = (TextView) eventView.findViewById(R.id.event_description_text_view);
            ImageView menuImageView = (ImageView) eventView.findViewById(R.id.menu_button);

            if (startTimeTextView != null) {
                startTimeTextView.setText(event.getStartTime().toString(Global.TIME_FORMAT));
            }

            if (durationTextView != null) {
                durationTextView.setText(UtilsUi.convertDurationToString(event.getDuration()));
            }

            if (titleTextView != null) {
                titleTextView.setText(event.getTitle());
            }

            if (descriptionTextView != null) {
                descriptionTextView.setText(event.getDescription());
            }

            if (menuImageView != null) {
                menuImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(), v);
                        popup.inflate(R.menu.menu_edit_item);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_item_edit:
                                        FragmentManager fragmentManager = activity.getSupportFragmentManager();

                                        TimeRangePicker.make(
                                                "Set Date & Time Title",
                                                event.getStartTime(),
                                                event.getEndTime(),
                                                new TimeRangePickerFragment.OnTimeRangeSetListener() {
                                                    @Override
                                                    public void timeRangeSet(DateTime startTime, DateTime endTime) {
                                                        event.setStartTime(startTime);
                                                        event.setEndTime(endTime);
                                                        sortEventList();
                                                        notifyDataSetChanged();
                                                    }
                                                },
                                                fragmentManager
                                        ).show();

                                        // TODO check new time is not overlapped with existing event

                                        return true;
                                    case R.id.action_item_delete:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                        String dialogMessage = "Confirm to remove the \"" + event.getTitle() + "\" event at " + event.getStartTime().toString(Global.TIME_FORMAT) + "?";
                                        builder.setMessage(dialogMessage);

                                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                eventList.remove(position);
                                                activity.updateScheduleListViewHeight();
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popup.show();
                    }
                });
            }
        }

        return eventView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void sortEventList() {
        Collections.sort(eventList, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2) {
                DateTime startTime1 = event1.getStartTime();
                DateTime startTime2 = event2.getStartTime();

                if (startTime1.isAfter(startTime2)) {
                    return 1;
                } else if (startTime1.isBefore(startTime2)) {
                    return -1;
                }

                return 0;
            }
        });
    }
}
