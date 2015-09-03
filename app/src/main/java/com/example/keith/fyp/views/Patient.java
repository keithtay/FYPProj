package com.example.keith.fyp.views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.keith.fyp.R;

/**
 * Created by Keith on 24/8/2015.
 */
public class Patient extends ActionBarActivity {

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_patient);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }
}

