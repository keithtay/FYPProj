package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.EditScheduleActivity;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.ScheduleActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class ViewScheduleActivity extends ScheduleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        init();

        eventListContainer = (LinearLayout) findViewById(R.id.event_list_container);

        displaySchedule();
    }

    public void openEditScheduleActivity(View view)
    {
        Intent intent = new Intent(ViewScheduleActivity.this, EditScheduleActivity.class);
        startActivity(intent);
    }

    private void displaySchedule() {
        boolean isCurrentTimeMarkHasBeenDisplayed = false;

        // Adding event views to the layout
        int lastIndex = eventList.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            Event event = eventList.get(i);

            String eventTitle = event.getTitle();
            String eventDescription = event.getDescription();
            DateTime startTime = event.getStartTime();
            DateTime endTime = event.getEndTime();
            String startTimeStr = startTime.toString(Global.TIME_FORMAT);
            String durationStr = UtilsUi.convertDurationToString(event.getDuration());

            if (!isCurrentTimeMarkHasBeenDisplayed && currentTime.isBefore(startTime)) {
                View currentTimeMarker = getLayoutInflater().inflate(R.layout.current_time_marker, eventListContainer, false);
                if (i == 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, spacingBetweenEventView);
                    currentTimeMarker.setLayoutParams(lp);
                }
                eventListContainer.addView(currentTimeMarker);
                isCurrentTimeMarkHasBeenDisplayed = true;
            }

            int layoutId;
            int marginLeft = (int) getResources().getDimension(R.dimen.event_margin_left);

            boolean isEventCurrentlyOccurring = (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) || currentTime.isEqual(startTime);
            if (!isCurrentTimeMarkHasBeenDisplayed && isEventCurrentlyOccurring) {
                layoutId = R.layout.event_layout_current;
                isCurrentTimeMarkHasBeenDisplayed = true;
                marginLeft = 0;
            } else {
                layoutId = R.layout.event_layout;
            }

            View eventView = createEventViewWithLayout(layoutId, eventTitle, eventDescription, startTimeStr, durationStr, i, lastIndex, marginLeft);
            eventListContainer.addView(eventView);
        }

        if (!isCurrentTimeMarkHasBeenDisplayed) {
            View currentTimeMarker = getLayoutInflater().inflate(R.layout.current_time_marker, eventListContainer, false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, spacingBetweenEventView, 0, 0);
            currentTimeMarker.setLayoutParams(lp);
            eventListContainer.addView(currentTimeMarker);
        }
    }

    private View createEventViewWithLayout(int layoutId, String eventTitle, String eventDescription, String startTimeStr, String durationStr, int currentIndex, int lastIndex, int marginLeft) {
        View eventView = getLayoutInflater().inflate(layoutId, eventListContainer, false);

        TextView titleTextView = (TextView) eventView.findViewById(R.id.event_title_text_view);
        TextView descriptionTextView = (TextView) eventView.findViewById(R.id.event_description_text_view);
        TextView startTimeTextView = (TextView) eventView.findViewById(R.id.event_start_time_text_view);
        TextView durationTextView = (TextView) eventView.findViewById(R.id.event_duration_text_view);

        titleTextView.setText(eventTitle);
        descriptionTextView.setText(eventDescription);
        startTimeTextView.setText(startTimeStr);
        durationTextView.setText(durationStr);

        if (currentIndex > 0 && currentIndex < lastIndex) {
            LinearLayout.LayoutParams defaultEventLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            defaultEventLayoutParams.setMargins(marginLeft, spacingBetweenEventView, 0, spacingBetweenEventView);
            eventView.setLayoutParams(defaultEventLayoutParams);
        } else if (currentIndex == 0) {
            LinearLayout.LayoutParams firstEventLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            firstEventLayoutParams.setMargins(marginLeft, 0, 0, spacingBetweenEventView);
            eventView.setLayoutParams(firstEventLayoutParams);
        } else if (currentIndex == lastIndex) {
            LinearLayout.LayoutParams lastEventLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lastEventLayoutParams.setMargins(marginLeft, spacingBetweenEventView, 0, 0);
            eventView.setLayoutParams(lastEventLayoutParams);
        }

        return eventView;
    }
}


