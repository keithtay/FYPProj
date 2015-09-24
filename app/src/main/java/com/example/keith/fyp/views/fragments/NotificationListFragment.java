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
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.renderers.ActionRenderer;
import com.example.keith.fyp.renderers.BackgroundRenderer;
import com.example.keith.fyp.renderers.ContentGameRecommendationRenderer;
import com.example.keith.fyp.renderers.ContentNewLogRenderer;
import com.example.keith.fyp.renderers.ContentRenderer;
import com.example.keith.fyp.renderers.ContentUpdateInfoFieldRenderer;
import com.example.keith.fyp.renderers.ContentUpdateInfoObjectRenderer;
import com.example.keith.fyp.renderers.HeaderRenderer;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.views.adapters.NotificationListAdapter;
import com.quentindommerc.superlistview.SuperListview;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.keith.fyp.models.Notification.*;


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

        LayoutInflater inflater = getActivity().getLayoutInflater();

        BackgroundRenderer backgroundRenderer = new BackgroundRenderer(inflater);

        HeaderRenderer headerRenderer1 = new HeaderRenderer(inflater, avatar1, name1, date1, summary1);
        HeaderRenderer headerRenderer2 = new HeaderRenderer(inflater, avatar2, name2, date2, summary2);
        HeaderRenderer headerRenderer3 = new HeaderRenderer(inflater, avatar3, name3, date3, summary3);
        HeaderRenderer headerRenderer4 = new HeaderRenderer(inflater, avatar4, name4, date4, summary4);
        HeaderRenderer headerRenderer5 = new HeaderRenderer(inflater, avatar5, name5, date5, summary5);

        ContentRenderer contentRenderer = null;
        ActionRenderer actionRenderer = null;

        ProblemLog problemLog = new ProblemLog(DateTime.now(), "Emotion", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu odio diam. Morbi sit amet erat at libero ullamcorper mollis et at turpis. Nam consequat ex sem, non ultricies risus molestie vel. Aenean placerat bibendum ipsum, eu volutpat sem pharetra sit amet. Proin metus nisi, lacinia id pharetra a, sollicitudin sit amet sapien. Vestibulum vehicula magna sit amet justo convallis tempor");
        Allergy oldAllergy = new Allergy("Milk", "Itchy skin", "");
        Allergy newAllergy = new Allergy("Milk", "Itchy skin", "Wash the skin with cold cloth");

        ContentUpdateInfoFieldRenderer contentRenderer1 = new ContentUpdateInfoFieldRenderer(inflater, "Personal Information", "Address", "32 Nanyang Avenue #12-7-23", "32 Nanyang Avenue #12-7-24");
        ContentNewLogRenderer contentRenderer3 = new ContentNewLogRenderer(inflater, problemLog);
        ContentGameRecommendationRenderer contentRenderer4 = new ContentGameRecommendationRenderer(inflater);
        ContentUpdateInfoObjectRenderer contentRenderer5 = new ContentUpdateInfoObjectRenderer(inflater, oldAllergy, newAllergy);

        NotificationRenderer renderer1 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer1, contentRenderer1, actionRenderer);
        NotificationRenderer renderer2 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer2, contentRenderer, actionRenderer);
        NotificationRenderer renderer3 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer3, contentRenderer3, actionRenderer);
        NotificationRenderer renderer4 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer4, contentRenderer4, actionRenderer);
        NotificationRenderer renderer5 = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer5, contentRenderer5, actionRenderer);

        Notification notification1 = new Notification(date1, name1, avatar1, summary1, "Read", renderer1);
        Notification notification2 = new Notification(date2, name2, avatar2, summary2, "Read", renderer2);
        Notification notification3 = new Notification(date3, name3, avatar3, summary3, "Read", renderer3);
        Notification notification4 = new Notification(date4, name4, avatar4, summary4, "Read", renderer4);
        Notification notification5 = new Notification(date5, name5, avatar5, summary5, "Read", renderer5);

        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);
        notificationList.add(notification4);
        notificationList.add(notification5);

        Collections.sort(notificationList, new Comparator<Notification>(){
            @Override
            public int compare(Notification lhs, Notification rhs) {
                DateTime lhsDate = lhs.getCreationDate();
                DateTime rhsDate = rhs.getCreationDate();

                if(lhsDate.isBefore(rhsDate)) {
                    return 1;
                } else if(lhsDate.isAfter(rhsDate)) {
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
}
