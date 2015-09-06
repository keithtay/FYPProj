package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.keith.fyp.R;
import com.example.keith.fyp.views.fragments.CreatePatientInfoCategListFragment;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment;

public class CreatePatientFormActivity extends AppCompatActivity {

    private CreatePatientInfoFormFragment infoFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int selectedCategory = intent.getIntExtra("selectedCategory",0);
        infoFormFragment = (CreatePatientInfoFormFragment) getFragmentManager().findFragmentById(R.id.CreatePatientInfoFormFragment);
        infoFormFragment.changeData(selectedCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_patient_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
