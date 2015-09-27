package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.CareCenterConfigFragmentDecoder;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;

public class CareCenterConfigDetailActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_center_config_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int selectedCategoryIndex = intent.getIntExtra("selectedCategory",0);
        Fragment fragmentToBeDisplayed = CareCenterConfigFragmentDecoder.getFragment(selectedCategoryIndex);

        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.care_center_config_detail_fragment_container, fragmentToBeDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
