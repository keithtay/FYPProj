package com.example.keith.fyp.views.fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationGroupUpdateReceiver;
import com.example.keith.fyp.interfaces.OnNotificationGroupUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.adapters.NotificationDetailListAdapter;

import java.util.ArrayList;

/**
 * Fragment to display notification detail
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class NotificationDetailFragment extends Fragment implements OnNotificationGroupUpdateListener {

    private View rootView;
    private ListView notificationDetailListView;

    private ArrayList<Notification> displayedNotificationDetailList;

    private NotificationDetailListAdapter listAdapter;

    private NotificationGroupUpdateReceiver notificationGroupUpdateReceiver;

    private int selectedIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_detail, container, false);

        displayedNotificationDetailList = new ArrayList<>();
        notificationDetailListView = (ListView) rootView.findViewById(R.id.notification_detail_list_view);
        listAdapter = new NotificationDetailListAdapter(getActivity(), displayedNotificationDetailList);
        notificationDetailListView.setAdapter(listAdapter);

        notificationGroupUpdateReceiver = new NotificationGroupUpdateReceiver(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != notificationGroupUpdateReceiver) {
            getActivity().registerReceiver(notificationGroupUpdateReceiver, new IntentFilter(Global.ACTION_NOTIFICATION_GROUP_UPDATE));
        }
    }

    /**
     * Render the notification detail
     *
     * @param position position of the selected notification
     */
    public void renderDetail(int position) {
        selectedIndex = position;

        displayedNotificationDetailList.clear();

        NotificationGroup notificationGroup = DataHolder.getNotificationGroupList().get(position);

        ArrayList<Notification> unprocessedNotifList = (ArrayList<Notification>) notificationGroup.getUnprocessedNotif().clone();
        ArrayList<Notification> processedNotifList = (ArrayList<Notification>) notificationGroup.getProcessedNotif().clone();
        displayedNotificationDetailList.addAll(unprocessedNotifList);
        displayedNotificationDetailList.addAll(processedNotifList);

        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNotificationGroupUpdate() {
        renderDetail(selectedIndex);
    }
}
