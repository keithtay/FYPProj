package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.keith.fyp.broadcastreceiver.NotificationGroupUpdateReceiver;
import com.example.keith.fyp.interfaces.OnNotificationGroupUpdateListener;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.NotificationToRendererConverter;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.NotificationDetailListAdapter;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class NotificationDetailActivity extends AppCompatActivity implements OnNotificationGroupUpdateListener, Drawer.OnDrawerItemClickListener {

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;
    private ListView notificationDetailListView;

    private ArrayList<Notification> displayedNotificationDetailList;

    private NotificationDetailListAdapter listAdapter;

    private NotificationGroupUpdateReceiver notificationGroupUpdateReceiver;

    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper,
                this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        navDrawer.setSelection(Global.NAVIGATION_NOTIFICATION_ID);
        refreshMiniDrawer();

        selectedIndex = getIntent().getIntExtra("selectedIndex", 0);
        NotificationGroup notificationGroup = DataHolder.getNotificationGroupList().get(selectedIndex);

        displayedNotificationDetailList = new ArrayList<>();
        ArrayList<Notification> unprocessedNotifList = (ArrayList<Notification>) notificationGroup.getUnprocessedNotif().clone();
        ArrayList<Notification> processedNotifList = (ArrayList<Notification>) notificationGroup.getProcessedNotif().clone();
        displayedNotificationDetailList.addAll(unprocessedNotifList);
        displayedNotificationDetailList.addAll(processedNotifList);

        notificationDetailListView = (ListView) findViewById(R.id.notification_detail_list_view);
        listAdapter = new NotificationDetailListAdapter(this, displayedNotificationDetailList);
        notificationDetailListView.setAdapter(listAdapter);

        notificationGroupUpdateReceiver = new NotificationGroupUpdateReceiver(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != notificationGroupUpdateReceiver) {
            registerReceiver(notificationGroupUpdateReceiver, new IntentFilter(Global.ACTION_NOTIFICATION_GROUP_UPDATE));
        }
    }

    private void refreshMiniDrawer() {
        int itemCount = navDrawer.getDrawerItems().size();
        for(int i=1; i<=itemCount; i++) {
            miniDrawer.updateItem(i);
        }
    }

    @Override
    public void onBackPressed() {
        goBackToNotificationListActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                goBackToNotificationListActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goBackToNotificationListActivity() {
        Intent output = new Intent();
        output.putExtra(Global.EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY, true);
        setResult(RESULT_OK, output);
        finish();
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        if(selectedIdentifier != Global.NAVIGATION_NOTIFICATION_ID) {
            miniDrawer.updateItem(Global.NAVIGATION_NOTIFICATION_ID);

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        return true;
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

        NotificationGroup notificationGroup = DataHolder.getNotificationGroupList().get(selectedIndex);
        displayedNotificationDetailList.clear();
        ArrayList<Notification> unprocessedNotifList = (ArrayList<Notification>) notificationGroup.getUnprocessedNotif().clone();
        ArrayList<Notification> processedNotifList = (ArrayList<Notification>) notificationGroup.getProcessedNotif().clone();
        displayedNotificationDetailList.addAll(unprocessedNotifList);
        displayedNotificationDetailList.addAll(processedNotifList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Go back to parent activity since this activity is mainly for portrait
            finish();
        }
    }
}
