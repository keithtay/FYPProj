package com.example.keith.fyp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.NotificationToRendererConverter;
import com.example.keith.fyp.views.activities.DashboardActivity;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int selectedIndex = getIntent().getIntExtra("selectedIndex", 0);
        Notification notification = DataHolder.getNotificationList().get(selectedIndex);
        NotificationRenderer renderer = NotificationToRendererConverter.convert(this, notification);
        View view = renderer.render();
        ((ViewGroup) findViewById(R.id.notification_detail_container)).addView(view);
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
        output.putExtra(DashboardActivity.EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY, true);
        setResult(RESULT_OK, output);
        finish();
    }
}
