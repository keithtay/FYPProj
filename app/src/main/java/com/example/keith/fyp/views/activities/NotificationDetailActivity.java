package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.interfaces.OnNotificationUpdateListener;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.NotificationToRendererConverter;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.activities.DashboardActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class NotificationDetailActivity extends AppCompatActivity implements OnNotificationUpdateListener, Drawer.OnDrawerItemClickListener {

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private NotificationUpdateReceiver notificationUpdateReceiver;

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

        int selectedIndex = getIntent().getIntExtra("selectedIndex", 0);
        Notification notification = DataHolder.getNotificationList().get(selectedIndex);
        NotificationRenderer renderer = NotificationToRendererConverter.convert(this, notification);
        View view = renderer.render();
        ((ViewGroup) findViewById(R.id.activity_content_container)).addView(view);

        notificationUpdateReceiver = new NotificationUpdateReceiver(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != notificationUpdateReceiver) {
            registerReceiver(notificationUpdateReceiver, new IntentFilter(Global.ACTION_NOTIFICATION_UPDATE));
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
        miniDrawer.updateItem(Global.NAVIGATION_NOTIFICATION_ID);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
