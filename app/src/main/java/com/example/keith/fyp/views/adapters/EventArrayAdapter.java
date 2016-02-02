package com.example.keith.fyp.views.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsThread;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.activities.EditScheduleActivity;
import com.example.keith.fyp.views.customviews.TimeRangePicker;
import com.example.keith.fyp.views.fragments.TimeRangePickerFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An {@link BaseAdapter} to display list of event
 */
public class EventArrayAdapter extends BaseAdapter {

    private List<Event> eventList = Collections.emptyList();
    private EditScheduleActivity activity;

    /**
     * Create an event array adapter with the specified values
     *
     * @param activity activity of the UI
     */
    public EventArrayAdapter(EditScheduleActivity activity) {
        this.activity = activity;
    }

    /**
     * Create an event array adapter with the specified values
     *
     * @param activity activity of the UI
     * @param eventList list of event to be displayed
     */
    public EventArrayAdapter(EditScheduleActivity activity, List<Event> eventList) {
        this.activity = activity;
        this.eventList = eventList;
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
        Button gameButton;
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
                                                activity.getString(R.string.dialog_set_time_range),
                                                event.getStartTime(),
                                                event.getEndTime(),
                                                new TimeRangePickerFragment.OnTimeRangeSetListener() {
                                                    @Override
                                                    public void timeRangeSet(DateTime newStartTime, DateTime newEndTime) {
                                                        Event newEvent = null;
                                                        try {
                                                            newEvent = (Event) event.clone();
                                                        } catch (CloneNotSupportedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        newEvent.setStartTime(newStartTime);
                                                        newEvent.setEndTime(newEndTime);

                                                        List<Event> eventListWithoutEditedEvent = new ArrayList<>();
                                                        eventListWithoutEditedEvent.addAll(eventList);
                                                        eventListWithoutEditedEvent.remove(event);

                                                        boolean isOverlap =  isEventOverlapWithEventInList(newEvent, eventListWithoutEditedEvent);
                                                        if(isOverlap) {
                                                            Toast.makeText(activity, R.string.toast_new_event_overlap, Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            event.setStartTime(newStartTime);
                                                            event.setEndTime(newEndTime);
                                                            sortEventList();
                                                            notifyDataSetChanged();
                                                        }
                                                    }
                                                },
                                                fragmentManager
                                        ).show();
                                        
                                        return true;
                                    case R.id.action_item_delete:
                                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                                        String dialogMessage = "Confirm to remove the \"" + event.getTitle() + "\" event at " + event.getStartTime().toString(Global.TIME_FORMAT) + "?";
                                        builder.content(dialogMessage);

                                        builder.positiveText(R.string.button_delete);
                                        builder.negativeText(R.string.button_cancel);

                                        builder.callback(new MaterialDialog.ButtonCallback() {
                                            @Override
                                            public void onPositive(MaterialDialog dialog) {
                                                super.onPositive(dialog);
                                                eventList.remove(position);
                                                activity.updateScheduleListViewHeight();
                                            }
                                        });

                                        builder.show();

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

    /**
     * Method to sort the displayed event list from the latest to oldest
     */
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

    public boolean isEventOverlapWithEventInList(Event event, List<Event> eventList) {
        boolean isOverlap = false;
        for(Event e:eventList) {
            DateTime eStartTime = e.getStartTime();
            DateTime eEndTime = e.getEndTime();

            DateTime newStartTime = event.getStartTime();
            DateTime newEndTime = event.getEndTime();

            if(newStartTime.isAfter(eStartTime) && newStartTime.isBefore(eEndTime)) {
                isOverlap = true;
                break;
            }

            if(newEndTime.isAfter(eStartTime) && newEndTime.isBefore(eEndTime)) {
                isOverlap = true;
                break;
            }

            if(newStartTime.isBefore(eStartTime) && newEndTime.isAfter(eEndTime)) {
                isOverlap = true;
                break;
            }
        }
        return isOverlap;
    }
}
