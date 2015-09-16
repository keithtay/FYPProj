package com.example.keith.fyp.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 16/9/2015.
 */
public class ScheduleActivity extends AppCompatActivity {

    protected ArrayList<Event> eventList;
    protected LinearLayout eventListContainer;

    protected int spacingBetweenEventView;
    protected DateTime currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventList = getPatientEventList();

        spacingBetweenEventView = (int) getResources().getDimension(R.dimen.paper_card_padding) / 2;

        // Initialize current time (for display purpose) TODO: change with actual current time
        currentTime = Global.TIME_FORMAT.parseDateTime("13:30");
    }

    protected void setScheduleDate() {
        TextView todayDate = (TextView) findViewById(R.id.today_date);
        DateTime currentDate = DateTime.now();
        todayDate.setText(currentDate.toString(Global.DATE_FORMAT));
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

    protected void init() {
        setScheduleDate();
    }
}
