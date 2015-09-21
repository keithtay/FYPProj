package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.views.fragments.PatientInfoCategListFragment;

public class CreatePatientActivity extends AppCompatActivity implements Communicator {

    private PatientInfoCategListFragment infoCategListFragment;

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

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Hide keyboard
            if(getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            // Change fragment
            Fragment fragmentToBeDisplayed = CreatePatientFormFragmentDecoder.getFragment(index);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentToBeDisplayed);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, CreatePatientFormActivity.class);
            intent.putExtra("selectedCategory", index);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataHolder.resetCreatedPatient();
    }
}
