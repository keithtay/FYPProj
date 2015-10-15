package com.example.keith.fyp.views.activities;

import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

/**
 * Base Activity to display the patient creation form
 */
public class PatientFormActivity extends AppCompatActivity {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Go back to parent activity since this activity is mainly for portrait
            NavUtils.navigateUpFromSameTask(this);
        }
    }
}
