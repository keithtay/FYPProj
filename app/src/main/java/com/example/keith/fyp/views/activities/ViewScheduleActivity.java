package com.example.keith.fyp.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class ViewScheduleActivity extends AppCompatActivity {

    private ArrayList<Event> eventList;

    private LinearLayout eventListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        eventList = getPatientEventList();
        eventListContainer = (LinearLayout) findViewById(R.id.event_list_container);

        for(Event event : eventList) {
            String eventTitle = event.getTitle();
            String eventDescription = event.getDescription();
            DateTime startTime = event.getStartTime();
            String startTimeStr = startTime.toString(Global.TIME_FORMAT);
            String durationStr = Long.toString(event.getDuration().getStandardMinutes()) + " min";

            View eventView = getLayoutInflater().inflate(R.layout.event_layout, eventListContainer, false);

            TextView titleTextView = (TextView) eventView.findViewById(R.id.event_title_text_view);
            TextView descriptionTextView = (TextView) eventView.findViewById(R.id.event_description_text_view);
            TextView startTimeTextView = (TextView) eventView.findViewById(R.id.event_start_time_text_view);
            TextView durationTextView = (TextView) eventView.findViewById(R.id.event_duration_text_view);

            titleTextView.setText(eventTitle);
            descriptionTextView.setText(eventDescription);
            startTimeTextView.setText(startTimeStr);
            durationTextView.setText(durationStr);

            eventListContainer.addView(eventView);
        }
    }

    private ArrayList<Event> getPatientEventList() {
        DateTimeFormatter formatter = Global.TIME_FORMAT;

        DateTime startTime1 = formatter.parseDateTime("12:00");
        DateTime endTime1 = formatter.parseDateTime("12:30");
        Event event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

        DateTime startTime2 = formatter.parseDateTime("12:30");
        DateTime endTime2 = formatter.parseDateTime("13:30");
        Event event2 = new Event("Games", "Play memory games", startTime2, endTime2);

        DateTime startTime3 = formatter.parseDateTime("14:30");
        DateTime endTime3 = formatter.parseDateTime("15:30");
        Event event3 = new Event("Games", "Play cognitive games", startTime3, endTime3);

        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);

        return eventList;
    }
}


