package com.example.keith.fyp.views.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.keith.fyp.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.CareCenterConfigFragment;
import com.example.keith.fyp.views.fragments.HomeScheduleFragment;
import com.example.keith.fyp.views.fragments.NotificationFragment;
import com.example.keith.fyp.views.fragments.PatientListFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DashboardActivity extends AppCompatActivity implements OnNotificationUpdateListener, Drawer.OnDrawerItemClickListener {

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private NotificationUpdateReceiver notificationUpdateReceiver;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fragmentManager = getFragmentManager();

        retrieveNotification();

        View contentWrapper = findViewById(R.id.dashboard_fragment_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        Intent intent = getIntent();

        if(savedInstanceState != null && savedInstanceState.containsKey(Global.STATE_LAST_DISPLAYED_FRAGMENT_ID)) {
            int lastFragmentId = savedInstanceState.getInt(Global.STATE_LAST_DISPLAYED_FRAGMENT_ID);
            navDrawer.setSelection(lastFragmentId);
            refreshMiniDrawer();
        } else if(intent.hasExtra(Global.EXTRA_SELECTED_NAVIGATION_ID)) {
            int selectedNavigationId = intent.getIntExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, 0);
            navDrawer.setSelection(selectedNavigationId);
            refreshMiniDrawer();
        } else {
            navDrawer.setSelection(Global.NAVIGATION_PATIENT_LIST_ID);
            refreshMiniDrawer();
        }

        notificationUpdateReceiver = new NotificationUpdateReceiver(this);
    }

    private void refreshMiniDrawer() {
        miniDrawer.updateItem(0);
        miniDrawer.updateItem(1);
        miniDrawer.updateItem(2);
        miniDrawer.updateItem(3);
        miniDrawer.updateItem(4);
        miniDrawer.updateItem(5);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Global.STATE_LAST_DISPLAYED_FRAGMENT_ID, UtilsUi.getCurrentDisplayedFragmentId());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != notificationUpdateReceiver) {
            registerReceiver(notificationUpdateReceiver, new IntentFilter(Global.ACTION_NOTIFICATION_UPDATE));
        }
    }

    public void retrieveNotification() {
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

        DataHolder.setNotificationList(notificationList);
    }

    @Override
    public void onNotificationUpdate() {
        int notificationCount = UtilsUi.countUnacceptedAndUnrejectedNotification();

        PrimaryDrawerItem notificationNav = (PrimaryDrawerItem) navDrawer.getDrawerItem(2);

        if(notificationCount == 0) {
            notificationNav = notificationNav.withBadgeStyle(UtilsUi.getInvisibleBadgeStyle(this));
        } else {
            String notificationCountStr = Integer.toString(notificationCount);
            notificationNav = notificationNav.withBadgeStyle(UtilsUi.getVisibleBadgeStyle(this));
            notificationNav = notificationNav.withBadge(notificationCountStr);

        }
        navDrawer.updateItem(notificationNav);
        miniDrawer.updateItem(2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (NotificationFragment.REQUEST_OPEN_NOTIFICATION_DETAIL) : {
                if (resultCode == Activity.RESULT_OK) {
                    boolean isFromNotificationDetailActivity = data.getBooleanExtra(Global.EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY, false);
                    if(isFromNotificationDetailActivity) {
                        navDrawer.setSelection(Global.NAVIGATION_NOTIFICATION_ID);
                        miniDrawer.updateItem(Global.NAVIGATION_NOTIFICATION_ID);
                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        // Change fragment when the selected navigation is not
        // the one currently being displayed
        int currentDisplayedFragmentId = UtilsUi.getCurrentDisplayedFragmentId();
        if (selectedIdentifier != currentDisplayedFragmentId || currentDisplayedFragmentId == Global.NAVIGATION_PATIENT_LIST_ID) {
            Fragment fragmentToBeDisplayed = null;

            switch (selectedIdentifier) {
                case Global.NAVIGATION_PATIENT_LIST_ID:
                    fragmentToBeDisplayed = new HomeScheduleFragment();
                    break;
                case Global.NAVIGATION_NOTIFICATION_ID:
                    fragmentToBeDisplayed = new NotificationFragment();
                    break;
                case 3:
                    fragmentToBeDisplayed = new PatientListFragment();
                    break;
                case Global.NAVIGATION_CARE_CENTER_CONFIG_ID:
                    fragmentToBeDisplayed = new CareCenterConfigFragment();
                    break;
            }

            miniDrawer.updateItem(currentDisplayedFragmentId);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.dashboard_fragment_container, fragmentToBeDisplayed);
            transaction.addToBackStack(null);
            transaction.commit();

            UtilsUi.setCurrentDisplayedFragmentId(selectedIdentifier);
        }

        return true;
    }
}