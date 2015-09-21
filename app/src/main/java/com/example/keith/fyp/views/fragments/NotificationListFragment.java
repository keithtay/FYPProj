package com.example.keith.fyp.views.fragments;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;
import com.quentindommerc.superlistview.SuperListview;

import org.joda.time.DateTime;

import java.util.ArrayList;


public class NotificationListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;
    private SuperListview notificationListView;
    private NotificationListAdapter notificationListAdapter;

    private Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        notificationListView = (SuperListview) rootView.findViewById(R.id.notification_recycler_view);

        final ArrayList<Notification> notificationList = getNotificationList();

        notificationListAdapter = new NotificationListAdapter(getActivity(), notificationList);
        notificationListView.setAdapter(notificationListAdapter);
        notificationListView.setOnItemClickListener(this);

        notificationListView.getList().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        // Select the first item of the listView (only for landscape mode)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewTreeObserver observer = notificationListView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    notificationListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if(notificationListAdapter.getCount() > 0) {
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

    public ArrayList<Notification> getNotificationList() {
        ArrayList<Notification> notificationList = new ArrayList<>();

        Bitmap avatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01);
        Bitmap avatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03);

        Notification notification1 = new Notification(DateTime.now(), "John Doe", avatar1, "Updated information of patient Jane Doe", "Read");
        Notification notification2 = new Notification(DateTime.now(), "Jane Doe", avatar2, "Create new patient Alice Lee", "Read");
        Notification notification3 = new Notification(DateTime.now(), "Jonathan Lee", avatar3, "Log new problem for patient Bob Tan", "Read");

        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);

        return notificationList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicator.respond(position);
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }
}
