package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.keith.fyp.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.CareCenterConfigFragmentDecoder;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class CareCenterConfigDetailActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private FragmentManager fragmentManager;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_center_config_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper,
                this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        navDrawer.setSelection(Global.NAVIGATION_CARE_CENTER_CONFIG_ID);
        refreshMiniDrawer();

        Intent intent = getIntent();
        int selectedCategoryIndex = intent.getIntExtra("selectedCategory",0);
        Fragment fragmentToBeDisplayed = CareCenterConfigFragmentDecoder.getFragment(selectedCategoryIndex);

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
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        if(selectedIdentifier != Global.NAVIGATION_CARE_CENTER_CONFIG_ID) {
            miniDrawer.updateItem(Global.NAVIGATION_CARE_CENTER_CONFIG_ID);

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
            startActivity(intent);
        }

        return true;
    }
}
