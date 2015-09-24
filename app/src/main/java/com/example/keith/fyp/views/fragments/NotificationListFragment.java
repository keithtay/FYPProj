package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.interfaces.NotificationCommunicator;
import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;
import com.quentindommerc.superlistview.SuperListview;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class NotificationListFragment extends Fragment implements AdapterView.OnItemClickListener, OnNotificationUpdateListener {
    private View rootView;
    private SuperListview notificationListView;
    private NotificationListAdapter notificationListAdapter;

    private ArrayList<Notification> notificationList;
    private NotificationCommunicator communicator;
    private NotificationUpdateReceiver notificationUpdateReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        notificationListView = (SuperListview) rootView.findViewById(R.id.notification_recycler_view);

        notificationList = getNotificationList();

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

    public ArrayList<Notification> getNotificationList() {
        ArrayList<Notification> notificationList = new ArrayList<>();

        Bitmap avatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01);
        Bitmap avatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03);
        Bitmap avatar4 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_04);
        Bitmap avatar5 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_05);

        DateTime date1 = DateTime.now();
        DateTime date2 = DateTime.now().minusDays(2).minusHours(2);
        DateTime date3 = DateTime.now().minusDays(5).plusHours(1);
        DateTime date4 = DateTime.now().minusDays(4).minusHours(2);
        DateTime date5 = DateTime.now().minusDays(1).plusHours(1);

        String name1 = "John Doe";
        String name2 = "Jane Doe";
        String name3 = "Tracy Lee";
        String name4 = "Apple Tan";
        String name5 = "Bethany Mandler";

        String summary1 = "Updated information of patient Jane Doe";
        String summary2 = "Create new patient Alice Lee";
        String summary3 = "Log new problem for patient Bob Tan";
        String summary4 = "Recommended the game category memory to patient Eileen Ng";
        String summary5 = "Updated the milk allergy of patient Hilda";

        Notification notification1 = new Notification(date1, name1, avatar1, summary1, Notification.STATUS_NONE, Notification.TYPE_UPDATE_INFO_FIELD);
        Notification notification2 = new Notification(date2, name2, avatar2, summary2, Notification.STATUS_NONE, Notification.TYPE_NEW_PATIENT);
        Notification notification3 = new Notification(date3, name3, avatar3, summary3, Notification.STATUS_NONE, Notification.TYPE_NEW_LOG);
        Notification notification4 = new Notification(date4, name4, avatar4, summary4, Notification.STATUS_NONE, Notification.TYPE_GAME_RECOMMENDATION);
        Notification notification5 = new Notification(date5, name5, avatar5, summary5, Notification.STATUS_NONE, Notification.TYPE_UPDATE_INFO_OBJECT);

        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);
        notificationList.add(notification4);
        notificationList.add(notification5);

        Collections.sort(notificationList, new Comparator<Notification>() {
            @Override
            public int compare(Notification lhs, Notification rhs) {
                DateTime lhsDate = lhs.getCreationDate();
                DateTime rhsDate = rhs.getCreationDate();

                if (lhsDate.isBefore(rhsDate)) {
                    return 1;
                } else if (lhsDate.isAfter(rhsDate)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return notificationList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicator.respond(notificationList.get(position));
    }

    public void setCommunicator(NotificationCommunicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void onNotificationUpdate() {
        notificationListAdapter.notifyDataSetChanged();
    }
}
