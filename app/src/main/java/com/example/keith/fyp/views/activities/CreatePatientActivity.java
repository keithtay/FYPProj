package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.views.fragments.CreatePatientInfoCategListFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormEmergencyContactFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPersonalInfoFragment;

public class CreatePatientActivity extends AppCompatActivity implements CreatePatientInfoCategListFragment.Communicator {

    private CreatePatientInfoCategListFragment infoCategListFragment;
    private CreatePatientInfoFormPersonalInfoFragment infoFormContainerFragment;

    private FragmentManager fragmentManager;
    private CreatePatientInfoFormPersonalInfoFragment personalInfoFragment;
    private CreatePatientInfoFormEmergencyContactFragment emergencyContactFragment;

    private Patient createdPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getFragmentManager();
        infoCategListFragment = (CreatePatientInfoCategListFragment) fragmentManager.findFragmentById(R.id.CreatePatientInfoCategListFragment);
        infoCategListFragment.setCommunicator(this);

        createdPatient = DataHolder.getCreatedPatient();
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

        Fragment fragmentToBeDisplayed = CreatePatientFormFragmentDecoder.getFragment(index);

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.CreatePatientInfoFormFragmentContainer, fragmentToBeDisplayed);
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
