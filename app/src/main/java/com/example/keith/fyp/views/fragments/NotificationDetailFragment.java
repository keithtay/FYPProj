package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.NotificationToRendererConverter;
import com.example.keith.fyp.views.adapters.NotificationDetailListAdapter;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;

import java.util.ArrayList;

public class NotificationDetailFragment extends Fragment {

    private View rootView;
    private ListView notificationDetailListView;

    private ArrayList<Notification> displayedNotificationDetailList;

    private NotificationDetailListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_detail, container, false);

        displayedNotificationDetailList = new ArrayList<>();
        notificationDetailListView = (ListView) rootView.findViewById(R.id.notification_detail_list_view);
        listAdapter = new NotificationDetailListAdapter(getActivity(), displayedNotificationDetailList);
        notificationDetailListView.setAdapter(listAdapter);

        return rootView;
    }

    public void renderDetail(int position) {
        displayedNotificationDetailList.clear();

        NotificationGroup notificationGroup = DataHolder.getNotificationGroupList().get(position);

        ArrayList<Notification> unprocessedNotifList = (ArrayList<Notification>) notificationGroup.getUnprocessedNotif().clone();
        ArrayList<Notification> processedNotifList = (ArrayList<Notification>) notificationGroup.getProcessedNotif().clone();
        displayedNotificationDetailList.addAll(unprocessedNotifList);
        displayedNotificationDetailList.addAll(processedNotifList);

        listAdapter.notifyDataSetChanged();
    }
}
