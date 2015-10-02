package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.comparators.NotificationComparator;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;
import com.quentindommerc.superlistview.SuperListview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NotificationListFragment extends Fragment implements AdapterView.OnItemClickListener, OnNotificationUpdateListener {
    private View rootView;
    private SuperListview notificationListView;
    private NotificationListAdapter notificationListAdapter;

    private ArrayList<NotificationGroup> notificationGroupList;
    private Communicator communicator;
    private NotificationUpdateReceiver notificationUpdateReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        notificationListView = (SuperListview) rootView.findViewById(R.id.notification_recycler_view);

        notificationGroupList = DataHolder.getNotificationGroupList();

        notificationListAdapter = new NotificationListAdapter(getActivity(), notificationGroupList);
        notificationListView.setAdapter(notificationListAdapter);
        notificationListView.setOnItemClickListener(this);

        notificationListView.getList().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        notificationUpdateReceiver = new NotificationUpdateReceiver(this);

        // Select the first item of the listView (only for landscape mode)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewTreeObserver observer = notificationListView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    notificationListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (notificationListAdapter.getCount() > 0) {
                        notificationListView.getList().performItemClick(
                                notificationListView.getAdapter().getView(0, null, null),
                                0,
                                notificationListView.getAdapter().getItemId(0));
                        notificationListView.getList().setItemChecked(0, true);
                    }
                }
            });
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != notificationUpdateReceiver) {
            getActivity().registerReceiver(notificationUpdateReceiver, new IntentFilter(Global.ACTION_NOTIFICATION_UPDATE));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(communicator != null) {
            communicator.respond(position);
        }
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void onNotificationUpdate() {

        for(NotificationGroup notificationGroup : notificationGroupList) {
            ArrayList<Notification> toBeMoved = new ArrayList<>();

            for(Notification notification:notificationGroup.getUnprocessedNotif()) {
                if(notification.getStatus() != Notification.STATUS_NONE) {
                    toBeMoved.add(notification);
                }
            }

            if(toBeMoved.size() > 0) {
                ArrayList<Notification> unprocessedNotifList = notificationGroup.getUnprocessedNotif();
                ArrayList<Notification> processedNotifList = notificationGroup.getProcessedNotif();
                unprocessedNotifList.removeAll(toBeMoved);
                processedNotifList.addAll(toBeMoved);

                // Sort the notifications by date
                NotificationComparator comparator = new NotificationComparator();
                Collections.sort(notificationGroup.getProcessedNotif(), comparator);
                Collections.sort(notificationGroup.getUnprocessedNotif(), comparator);

                // Recalculate the summary + status
                UtilsUi.setNotificationGroupSummary(getActivity(), notificationGroup);
                UtilsUi.setNotificationGroupStatus(getActivity(), notificationGroup);
            }
        }

        Intent intent = new Intent(Global.ACTION_NOTIFICATION_GROUP_UPDATE);
        getActivity().sendBroadcast(intent);

        notificationListAdapter.notifyDataSetChanged();
    }
}
