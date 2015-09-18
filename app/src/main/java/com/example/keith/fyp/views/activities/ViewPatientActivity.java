package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.ViewPatientFormFragmentDecoder;
import com.example.keith.fyp.views.fragments.PatientInfoCategListFragment;

public class ViewPatientActivity extends AppCompatActivity  implements PatientInfoCategListFragment.Communicator {

    private PatientInfoCategListFragment infoCategListFragment;
    private FragmentManager fragmentManager;
    private InputMethodManager inputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        fragmentManager = getFragmentManager();
        infoCategListFragment = (PatientInfoCategListFragment) fragmentManager.findFragmentById(R.id.patient_info_categ_list_fragment);
        infoCategListFragment.setCommunicator(this);
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
            Fragment fragmentToBeDisplayed = ViewPatientFormFragmentDecoder.getFragment(index);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.view_patient_info_form_fragment_container, fragmentToBeDisplayed);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, ViewPatientFormActivity.class);
            intent.putExtra("selectedCategory", index);
            startActivity(intent);
        }
    }
}
