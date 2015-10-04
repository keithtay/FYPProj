package com.example.keith.fyp.views.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.keith.fyp.broadcastreceiver.NotificationGroupUpdateReceiver;
import com.example.keith.fyp.comparators.NotificationComparator;
import com.example.keith.fyp.interfaces.OnNotificationGroupUpdateListener;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.models.Patient;
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
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity implements OnNotificationGroupUpdateListener, Drawer.OnDrawerItemClickListener {

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private NotificationGroupUpdateReceiver notificationGroupUpdateReceiver;

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

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(Global.STATE_SELECTED_PATIENT_DRAFT_ID);
        editor.commit();

        notificationGroupUpdateReceiver = new NotificationGroupUpdateReceiver(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Global.STATE_LAST_DISPLAYED_FRAGMENT_ID, UtilsUi.getCurrentDisplayedFragmentId());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != notificationGroupUpdateReceiver) {
            registerReceiver(notificationGroupUpdateReceiver, new IntentFilter(Global.ACTION_NOTIFICATION_GROUP_UPDATE));
        }
    }

    public void retrieveNotification() {

        // =====================================================
        // Creating Notification Model
        // =====================================================

        Bitmap patientAvatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_18);
        Bitmap patientAvatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_19);
        Bitmap patientAvatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_20);

        Patient patient1 = new Patient("Garry", "Reese", "123AB456", patientAvatar1);
        Patient patient2 = new Patient("Lillian", "Wade", "987CD654", patientAvatar2);
        Patient patient3 = new Patient("Laura", "Freeman", "AV12G64", patientAvatar3);

        Bitmap caregiverAvatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01);
        Bitmap caregiverAvatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02);
        Bitmap caregiverAvatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03);
        Bitmap caregiverAvatar4 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_04);
        Bitmap caregiverAvatar5 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_05);

        DateTime date1 = DateTime.now();
        DateTime date2 = DateTime.now().minusDays(2).minusHours(2);
        DateTime date3 = DateTime.now().minusDays(5).plusHours(1);
        DateTime date4 = DateTime.now().minusDays(4).minusHours(2);
        DateTime date5 = DateTime.now().minusDays(1).plusHours(1);
        DateTime date6 = DateTime.now().minusDays(3).plusHours(5);

        String caregiverName1 = "John Doe";
        String caregiverName2 = "Jane Doe";
        String caregiverName3 = "Tracy Lee";
        String caregiverName4 = "Apple Tan";
        String caregiverName5 = "Bethany Mandler";

        String summary1 = "Updated information of patient";
        String summary2 = "Log new problem for patient";
        String summary3 = "Create new patient Laura Freeman";
        String summary4 = "Recommended the game category memory to patient";
        String summary5 = "Updated the milk allergy of patient Hilda";
        String summary6 = "Log new problem for patient";

        Notification notification1 = new Notification(date1, caregiverName1, caregiverAvatar1, summary1, patient1, Notification.STATUS_NONE, Notification.TYPE_UPDATE_INFO_FIELD);
        Notification notification2 = new Notification(date2, caregiverName2, caregiverAvatar2, summary2, patient2, Notification.STATUS_NONE, Notification.TYPE_NEW_LOG);
        Notification notification3 = new Notification(date3, caregiverName3, caregiverAvatar3, summary3, patient3, Notification.STATUS_NONE, Notification.TYPE_NEW_PATIENT);
        Notification notification4 = new Notification(date4, caregiverName4, caregiverAvatar4, summary4, patient1, Notification.STATUS_NONE, Notification.TYPE_GAME_RECOMMENDATION);
        Notification notification5 = new Notification(date5, caregiverName5, caregiverAvatar5, summary5, patient2, Notification.STATUS_NONE, Notification.TYPE_UPDATE_INFO_OBJECT);
        Notification notification6 = new Notification(date6, caregiverName3, caregiverAvatar3, summary6, patient1, Notification.STATUS_NONE, Notification.TYPE_NEW_LOG);

        ArrayList<Notification> notificationList = new ArrayList<>();
        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);
        notificationList.add(notification4);
        notificationList.add(notification5);
        notificationList.add(notification6);

        // =====================================================
        // Building the Notification Group based on the notification
        // =====================================================

        HashMap patientAndNotificationGroupMap = new HashMap();

        for(Notification notification:notificationList) {
            Patient currentPatient = notification.getAffectedPatient();
            String patientNric = currentPatient.getNric();

            if (patientAndNotificationGroupMap.containsKey(patientNric)) {
                NotificationGroup notifGroup = (NotificationGroup) patientAndNotificationGroupMap.get(patientNric);
                if(notification.getStatus() == Notification.STATUS_NONE) {
                    notifGroup.getUnprocessedNotif().add(notification);
                } else {
                    notifGroup.getProcessedNotif().add(notification);
                }
            } else {
                NotificationGroup newNotifGroup = new NotificationGroup();
                newNotifGroup.setAffectedPatient(currentPatient);
                if(notification.getStatus() == Notification.STATUS_NONE) {
                    newNotifGroup.getUnprocessedNotif().add(notification);
                } else {
                    newNotifGroup.getProcessedNotif().add(notification);
                }
                patientAndNotificationGroupMap.put(patientNric, newNotifGroup);
            }
        }

        ArrayList<NotificationGroup> notificationGroupList = new ArrayList<>();
        NotificationComparator comparator = new NotificationComparator();
        for (Object obj : patientAndNotificationGroupMap.values()) {
            NotificationGroup notificationGroup = (NotificationGroup) obj;

            // Set notification status
            UtilsUi.setNotificationGroupStatus(this, notificationGroup);

            // Sort the notifications by date
            Collections.sort(notificationGroup.getProcessedNotif(), comparator);
            Collections.sort(notificationGroup.getUnprocessedNotif(), comparator);

            // Set the summary
            UtilsUi.setNotificationGroupSummary(this, notificationGroup);

            notificationGroupList.add(notificationGroup);
        }

        DataHolder.setNotificationGroupList(notificationGroupList);
    }

    @Override
    public void onNotificationGroupUpdate() {
        int notificationCount = UtilsUi.countUnprocessedNotificationGroup();

        PrimaryDrawerItem notificationNav = (PrimaryDrawerItem) navDrawer.getDrawerItem(Global.NAVIGATION_NOTIFICATION_ID);

        if(notificationCount == 0) {
            notificationNav = notificationNav.withBadgeStyle(UtilsUi.getInvisibleBadgeStyle(this));
        } else {
            String notificationCountStr = Integer.toString(notificationCount);
            notificationNav = notificationNav.withBadgeStyle(UtilsUi.getVisibleBadgeStyle(this));
            notificationNav = notificationNav.withBadge(notificationCountStr);

        }
        navDrawer.updateItem(notificationNav);
        miniDrawer.updateItem(Global.NAVIGATION_NOTIFICATION_ID);
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

    private void refreshMiniDrawer() {
        int itemCount = navDrawer.getDrawerItems().size();
        for(int i=1; i<=itemCount; i++) {
            miniDrawer.updateItem(i);
        }
    }

    @Override
    public void onBackPressed() {
    }
}