package com.example.keith.fyp.views.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.CrossfadeWrapper;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.HomeScheduleFragment;
import com.example.keith.fyp.views.fragments.NotificationFragment;
import com.example.keith.fyp.views.fragments.PatientListFragment;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DashboardActivity extends AppCompatActivity implements OnNotificationUpdateListener {

    private final int NAVIGATION_PATIENT_LIST_ID = 1;
    private final int NAVIGATION_NOTIFICATION_ID = 2;
    private final int NAVIGATION_CARE_CENTER_CONFIG_ID = 4;
    private final String STATE_LAST_DISPLAYED_FRAGMENT_ID = "LAST_DISPLAYED_FRAGMENT_ID";
    public static final String EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY = "EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY";

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private NotificationUpdateReceiver notificationUpdateReceiver;

    // Indicate which page of the navigation option is currently displayed
    private int currentDisplayedFragmentId;

    private BadgeStyle visibleStyle;
    private BadgeStyle invisibleStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        retrieveNotification();

        // Style of notification count badge (navigation drawer)
        visibleStyle = new BadgeStyle(getResources().getColor(R.color.red_100), getResources().getColor(R.color.red_100));
        visibleStyle.withTextColor(getResources().getColor(R.color.text_color_default));
        invisibleStyle = new BadgeStyle(getResources().getColor(R.color.transparent), getResources().getColor(R.color.transparent));
        invisibleStyle.withTextColor(getResources().getColor(R.color.transparent));

        setNavigationDrawer(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(STATE_LAST_DISPLAYED_FRAGMENT_ID)) {
            int lastFragmentId = savedInstanceState.getInt(STATE_LAST_DISPLAYED_FRAGMENT_ID);
            navDrawer.setSelection(lastFragmentId);
            miniDrawer.updateItem(lastFragmentId);
        } else {
            navDrawer.setSelection(NAVIGATION_PATIENT_LIST_ID);
            miniDrawer.updateItem(NAVIGATION_PATIENT_LIST_ID);
        }

        notificationUpdateReceiver = new NotificationUpdateReceiver(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_LAST_DISPLAYED_FRAGMENT_ID, currentDisplayedFragmentId);
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
            notificationNav = notificationNav.withBadgeStyle(invisibleStyle);
        } else {
            String notificationCountStr = Integer.toString(notificationCount);
            notificationNav = notificationNav.withBadgeStyle(visibleStyle);
            notificationNav = notificationNav.withBadge(notificationCountStr);
        }
        navDrawer.setItemAtPosition(notificationNav, 2);
        miniDrawer.updateItem(2);
    }

    public void setNavigationDrawer(Bundle savedInstanceState) {
        Resources resource = getResources();
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(resource.getDrawable(R.drawable.avatar1));

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.account_header)
                .withTranslucentStatusBar(false)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_patient_list)
                .withIcon(GoogleMaterial.Icon.gmd_home)
                .withIdentifier(NAVIGATION_PATIENT_LIST_ID);
        String notificationCount = Integer.toString(UtilsUi.countUnacceptedAndUnrejectedNotification());
        PrimaryDrawerItem notificationDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_notification)
                .withIcon(GoogleMaterial.Icon.gmd_notifications)
                .withBadge(notificationCount)
                .withBadgeStyle(visibleStyle)
                .withIdentifier(NAVIGATION_NOTIFICATION_ID);
        PrimaryDrawerItem accountDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_account)
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withIdentifier(3);
        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_settings)
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(NAVIGATION_CARE_CENTER_CONFIG_ID);

        Drawer navigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        homeDrawerItem,
                        notificationDrawerItem,
                        accountDrawerItem,
                        settingsDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int selectedIdentifier = drawerItem.getIdentifier();

                        // Change fragment when the selected navigation is not
                        // the one currently being displayed
                        if (selectedIdentifier != currentDisplayedFragmentId || selectedIdentifier == 1) {
                            FragmentManager fragmentManager = getFragmentManager();
                            Fragment fragmentToBeDisplayed = null;

                            switch (selectedIdentifier) {
                                case NAVIGATION_PATIENT_LIST_ID:
                                    fragmentToBeDisplayed = new PatientListFragment();
                                    break;
                                case NAVIGATION_NOTIFICATION_ID:
                                    fragmentToBeDisplayed = new NotificationFragment();
                                    break;
                                case 3:
                                    fragmentToBeDisplayed = new HomeScheduleFragment();
                                    break;
                                case NAVIGATION_CARE_CENTER_CONFIG_ID:
                                    fragmentToBeDisplayed = new PatientListFragment();
                                    break;
                            }

                            miniDrawer.updateItem(currentDisplayedFragmentId);
                            
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.dashboard_fragment_container, fragmentToBeDisplayed);
                            transaction.addToBackStack(null);
                            transaction.commit();

                            currentDisplayedFragmentId = selectedIdentifier;
                        }

                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .buildView();

        this.miniDrawer = new MiniDrawer()
                .withDrawer(navigationDrawer)
                .withInnerShadow(true)
                .withAccountHeader(accountHeader);

        int first = (int) UIUtils.convertDpToPixel(300, this);
        int second = (int) UIUtils.convertDpToPixel(72, this);

        View contentWrapper = findViewById(R.id.dashboard_fragment_container);

        Crossfader crossFader = new Crossfader()
                .withContent(contentWrapper)
                .withFirst(navigationDrawer.getSlider(), first)
                .withSecond(miniDrawer.build(this), second)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));

        this.navDrawer = navigationDrawer;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (NotificationFragment.REQUEST_OPEN_NOTIFICATION_DETAIL) : {
                if (resultCode == Activity.RESULT_OK) {
                    boolean isFromNotificationDetailActivity = data.getBooleanExtra(EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY, false);
                    if(isFromNotificationDetailActivity) {
                        navDrawer.setSelection(NAVIGATION_NOTIFICATION_ID);
                        miniDrawer.updateItem(NAVIGATION_NOTIFICATION_ID);
                    }
                }
                break;
            }
        }
    }
}