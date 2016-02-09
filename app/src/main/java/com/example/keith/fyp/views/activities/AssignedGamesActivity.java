package com.example.keith.fyp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.customviews.TextField;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * Created by ks on 5/2/2016.
 */


public class AssignedGamesActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener{
    private Drawer navDrawer;
    private MiniDrawer miniDrawer;
    private TextView patientName;
    private TextView patientID;
    private int getPatientID;
    ArrayList<String> allAssignedGameNames = new ArrayList<String>();
    protected Patient viewedPatient;
    private MaterialSpinner assignedGamesSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_assigned_games);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        Intent i = getIntent();
        dbfile db = new dbfile();
        patientName = (TextView) findViewById(R.id.patient_name_assigned_games);
        patientID = (TextView) findViewById(R.id.patient_id_assigned_games);

        //retrieval of details for assignedGames.
        getPatientID = db.getPatientId(i.getStringExtra("patientNRIC"));
        Log.v("check patient nric ", i.getStringExtra("patientNRIC")); //test nric.
        Log.v("check patient ID ", ""+getPatientID); //test patient ID.
        allAssignedGameNames = db.getAssignedGamesOfPatient(getPatientID);
        Log.v("abc", "" + allAssignedGameNames); //test show all assigned game names & gameID of selected patient.

        //set textView of patient name and ID.
        patientName.setText("Patient Name: " + i.getStringExtra("patientName"));
        patientID.setText("Patient ID: " + getPatientID);

        //set spinner with the respective values.
        assignedGamesSpinner = (MaterialSpinner) findViewById(R.id.list_of_assigned_games);
        //ArrayAdapter<CharSequence> assignedGamesAdapter = ArrayAdapter.createFromResource(this,
          //      allAssignedGameNames, android.R.layout.simple_spinner_item);
        //assignedGamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //assignedGamesSpinner.setAdapter(assignedGamesAdapter);



    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        if (selectedIdentifier != Global.NAVIGATION_PATIENT_LIST_ID) {
            miniDrawer.updateItem(Global.NAVIGATION_PATIENT_LIST_ID);

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        return true;
    }
}
