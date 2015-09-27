package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.CreatePatientCommunicator;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.fragments.PatientInfoCategListFragment;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class CreatePatientActivity extends AppCompatActivity implements CreatePatientCommunicator {

    private PatientInfoCategListFragment infoCategListFragment;
    private Fragment fragmentDisplayed;

    private FragmentManager fragmentManager;
    private InputMethodManager inputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        fragmentManager = getFragmentManager();
        infoCategListFragment = (PatientInfoCategListFragment) fragmentManager.findFragmentById(R.id.create_patient_info_categ_list_fragment);
        infoCategListFragment.setCommunicator(this);

        FloatingActionButton createNewPatientFab = (FloatingActionButton) findViewById(R.id.save_new_patient_fab);
        if (createNewPatientFab != null) {
            createNewPatientFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkRequiredFields();
                }
            });
        }
    }

    private void checkRequiredFields() {
        Intent intent = new Intent(Global.ACTION_CREATE_NEW_PATIENT);
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                DataHolder.resetCreatedPatient();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;
    }

    @Override
    public void respond(int index) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Hide keyboard
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            changeFragment(index);
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, CreatePatientFormActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_CATEGORY, index);
            startActivity(intent);
        }
    }

    @Override
    public void respond(int index, ArrayList<Integer> emptyFieldIdList) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Hide keyboard
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            changeFragment(index, emptyFieldIdList);
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, CreatePatientFormActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_CATEGORY, index);
            intent.putIntegerArrayListExtra(Global.EXTRA_EMPTY_FIELD_ID_LIST, emptyFieldIdList);
            startActivity(intent);
        }
    }

    private void changeFragment(int index) {
        fragmentDisplayed = CreatePatientFormFragmentDecoder.getFragment(index);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void changeFragment(int index, ArrayList<Integer> emptyFieldIdList) {
        fragmentDisplayed = CreatePatientFormFragmentDecoder.getFragment(index);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(Global.EXTRA_EMPTY_FIELD_ID_LIST, emptyFieldIdList);
        fragmentDisplayed.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataHolder.resetCreatedPatient();
    }
}
