package com.example.keith.fyp;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.ScheduleActivity;
import com.example.keith.fyp.views.adapters.EventArrayAdapter;

public class EditScheduleActivity extends ScheduleActivity {

    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        init();

        // Hide the more patient info button
        View viewMoreButton = findViewById(R.id.view_more_button);
        ((ViewGroup) viewMoreButton.getParent()).removeView(viewMoreButton);

        eventListView = (ListView) findViewById(R.id.event_list_list_view);
        EventArrayAdapter customAdapter = new EventArrayAdapter(this, R.layout.event_layout_editable, eventList);
        eventListView .setAdapter(customAdapter);

        updateScheduleListViewHeight();
    }

    public void updateScheduleListViewHeight() {
        UtilsUi.setListViewHeightBasedOnChildren(eventListView);
    }
}
