package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.utils.ViewPatientFormFragmentDecoder;
import com.example.keith.fyp.views.EmptyAndAutofitRecyclerView;
import com.example.keith.fyp.views.PatientListFragment;
import com.example.keith.fyp.views.adapters.PatientListAdapter;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        fragmentManager = getFragmentManager();
//        Fragment fragmentToBeDisplayed = new PatientListFragment();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.dashboard_fragment_container, fragmentToBeDisplayed);
//        transaction.addToBackStack(null);
//        transaction.commit();

        Drawer navDrawer = UtilsUi.setNavigationDrawer(this, savedInstanceState);
        navDrawer.setSelection(1);
    }
}