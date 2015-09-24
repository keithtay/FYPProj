package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
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
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;
import com.quentindommerc.superlistview.SuperListview;

import java.util.ArrayList;


public class NotificationListFragment extends Fragment implements AdapterView.OnItemClickListener, OnNotificationUpdateListener {
    private View rootView;
    private SuperListview notificationListView;
    private NotificationListAdapter notificationListAdapter;

    private ArrayList<Notification> notificationList;
    private Communicator communicator;
    private NotificationUpdateReceiver notificationUpdateReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        notificationListView = (SuperListview) rootView.findViewById(R.id.notification_recycler_view);

        notificationList = DataHolder.getNotificationList();

        notificationListAdapter = new NotificationListAdapter(getActivity(), notificationList);
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
        communicator.respond(position);
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void onNotificationUpdate() {
        notificationListAdapter.notifyDataSetChanged();
    }
}
