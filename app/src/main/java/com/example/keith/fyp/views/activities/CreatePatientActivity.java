package com.example.keith.fyp.views.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.keith.fyp.R;
import com.example.keith.fyp.views.fragments.CreatePatientInfoCategListFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment;

public class CreatePatientActivity extends AppCompatActivity implements CreatePatientInfoCategListFragment.Communicator {

    private CreatePatientInfoCategListFragment infoCategListFragment;
    private CreatePatientInfoFormFragment infoFormFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getFragmentManager();
        infoCategListFragment = (CreatePatientInfoCategListFragment) fragmentManager.findFragmentById(R.id.CreatePatientInfoCategListFragment);
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
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void respond(int index) {
        infoFormFragment = (CreatePatientInfoFormFragment) fragmentManager.findFragmentById(R.id.CreatePatientInfoFormFragment);

        if(infoFormFragment != null && infoFormFragment.isVisible()) {
            // In landscape orientation
            infoFormFragment.changeData(index);
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, CreatePatientFormActivity.class);
            intent.putExtra("selectedCategory", index);
            startActivity(intent);
        }
    }
}
