package com.example.keith.fyp.views.fragments;

import android.content.Context;
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
import com.example.keith.fyp.interfaces.NotificationCommunicator;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.renderers.ActionRenderer;
import com.example.keith.fyp.renderers.BackgroundRenderer;
import com.example.keith.fyp.renderers.ContentRenderer;
import com.example.keith.fyp.renderers.HeaderRenderer;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;
import com.quentindommerc.superlistview.SuperListview;

import org.joda.time.DateTime;

import java.util.ArrayList;


public class NotificationListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;
    private SuperListview notificationListView;
    private NotificationListAdapter notificationListAdapter;

    private  ArrayList<Notification> notificationList;
    private NotificationCommunicator communicator;

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

        DateTime date1 = DateTime.now();
        DateTime date2 = DateTime.now().minusDays(2).minusHours(2);
        DateTime date3 = DateTime.now().minusDays(5).plusHours(1);

        String name1 = "John Doe";
        String name2 = "Jane Doe";
        String name3 = "Jonathan Lee";

        String summary1 = "Updated information of patient Jane Doe";
        String summary2 = "Create new patient Alice Lee";
        String summary3 = "Log new problem for patient Bob Tan";

        LayoutInflater inflater = getActivity().getLayoutInflater();

        BackgroundRenderer backgroundRenderer = new BackgroundRenderer(inflater);

        HeaderRenderer headerRenderer1 = new HeaderRenderer(inflater, avatar1, name1, date1, summary1);
        HeaderRenderer headerRenderer2 = new HeaderRenderer(inflater, avatar2, name2, date2, summary2);
        HeaderRenderer headerRenderer3 = new HeaderRenderer(inflater, avatar3, name3, date3, summary3);

        ContentRenderer contentRenderer = null;
        ActionRenderer actionRenderer = null;

        NotificationRenderer renderer1 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer1, contentRenderer, actionRenderer);
        NotificationRenderer renderer2 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer2, contentRenderer, actionRenderer);
        NotificationRenderer renderer3 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer3, contentRenderer, actionRenderer);

        Notification notification1 = new Notification(date1, name1, avatar1, summary1, "Read", renderer1);
        Notification notification2 = new Notification(date2, name2, avatar2, summary2, "Read", renderer2);
        Notification notification3 = new Notification(date3, name3, avatar3, summary3, "Read", renderer3);

        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);

        return notificationList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicator.respond(notificationList.get(position));
    }

    public void setCommunicator(NotificationCommunicator communicator) {
        this.communicator = communicator;
    }
}
