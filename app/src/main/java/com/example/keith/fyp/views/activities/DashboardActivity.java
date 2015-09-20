package com.example.keith.fyp.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.UtilsUi;
import com.mikepenz.materialdrawer.Drawer;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Drawer navDrawer = UtilsUi.setNavigationDrawer(this, savedInstanceState);
        navDrawer.setSelection(1);
    }
}