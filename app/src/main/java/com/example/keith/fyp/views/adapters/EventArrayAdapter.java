package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.activities.ViewScheduleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sutrisno on 16/9/2015.
 */
public class EventArrayAdapter extends ArrayAdapter<Event> {

    public EventArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public EventArrayAdapter(Context context, int resource, List<Event> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View eventView = convertView;

        if (eventView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            eventView = vi.inflate(R.layout.event_layout_editable, null);
        }

        Event event = getItem(position);

        if (event != null) {
            TextView startTimeTextView = (TextView) eventView.findViewById(R.id.event_start_time_text_view);
            TextView durationTextView = (TextView) eventView.findViewById(R.id.event_duration_text_view);
            TextView titleTextView = (TextView) eventView.findViewById(R.id.event_title_text_view);
            TextView descriptionTextView = (TextView) eventView.findViewById(R.id.event_description_text_view);

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
        }

        return eventView;
    }
}
