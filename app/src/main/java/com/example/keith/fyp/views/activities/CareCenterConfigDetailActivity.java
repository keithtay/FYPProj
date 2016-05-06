package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.CareCenterConfigFragmentEncoder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Activity to display the care centre configuration detail
 */
public class CareCenterConfigDetailActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private FragmentManager fragmentManager;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_center_config_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        final int UserID = Integer.parseInt(preferences.getString("userid",""));
        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper,
                this, savedInstanceState, UserID);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        navDrawer.setSelection(Global.NAVIGATION_CARE_CENTER_CONFIG_ID);
        refreshMiniDrawer();

        Intent intent = getIntent();
        int selectedCategoryIndex = intent.getIntExtra("selectedCategory",0);
        Fragment fragmentToBeDisplayed = CareCenterConfigFragmentEncoder.getFragment(selectedCategoryIndex);

        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.care_center_config_detail_fragment_container, fragmentToBeDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void refreshMiniDrawer() {
        int itemCount = navDrawer.getDrawerItems().size();
        for(int i=1; i<=itemCount; i++) {
            miniDrawer.updateItem(i);
        }
    }

    @Override
    public void onBackPressed() {
        goBackToCareCenterConfigListActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goBackToCareCenterConfigListActivity() {
        Intent output = new Intent();
        output.putExtra(Global.EXTRA_FROM_NOTIFICATION_DETAIL_ACTIVITY, true);
        setResult(RESULT_OK, output);
        finish();
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        if(selectedIdentifier != Global.NAVIGATION_CARE_CENTER_CONFIG_ID) {
            miniDrawer.updateItem(Global.NAVIGATION_CARE_CENTER_CONFIG_ID);

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        return true;
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
