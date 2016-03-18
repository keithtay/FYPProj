package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
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
    private Button runSelectedGameButton;
    private TextView playedGames;



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
        runSelectedGameButton = (Button) findViewById(R.id.run_game_button);
        playedGames = (TextView) findViewById(R.id.played_games);

        //retrieval of details for assignedGames.
        getPatientID = db.getPatientId(i.getStringExtra("patientNRIC"));
        Log.v("check patient nric ", i.getStringExtra("patientNRIC")); //test nric.
        Log.v("check patient ID ", ""+getPatientID); //test patient ID.
        allAssignedGameNames = db.getAssignedGamesOfPatient(getPatientID);
        Log.v("abc", "" + allAssignedGameNames); //test show all assigned game names, gameID & manifest of selected patient.

        //set textView of patient name and ID.
        patientName.append(" " + i.getStringExtra("patientName"));
        patientID.append(" " + getPatientID);

        final String [] fullManifestName = new String[allAssignedGameNames.size()];
        final String [] gameName = new String[allAssignedGameNames.size()];
        final String [] gameID = new String[allAssignedGameNames.size()];
        String [] splitedString;
        for (int j = 0; j< allAssignedGameNames.size(); j++){
            splitedString = allAssignedGameNames.get(j).split("-");
            gameName[j] = splitedString[0]; //check game name.
            gameID[j] = splitedString[1]; //check game ID.
            fullManifestName[j] = splitedString[2]; //check manifest name.
            Log.v("xyz2",gameName[j]);
            Log.v("xyz2",gameID[j]);
            Log.v("xyz2",fullManifestName[j]);
        }
        //populate spinner with assigned games name.
        assignedGamesSpinner = (MaterialSpinner) findViewById(R.id.list_of_assigned_games);
        final ArrayAdapter<String> assignedGamesAdapter = new ArrayAdapter<String>(this, R.layout.spinner_dropdown_item_photoalbum_assignedgames, gameName);
        assignedGamesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list_photoalbum_assignedgames);
        assignedGamesSpinner.setAdapter(assignedGamesAdapter);

        //'run game' button on click listener.
        runSelectedGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int chosenItemPosition;
                String errorMessage = getResources().getString(R.string.error_msg_field_required);
                if (assignedGamesSpinner.getSelectedItemPosition() != 0) {
                    chosenItemPosition = assignedGamesSpinner.getSelectedItemPosition() - 1; //-1 because array starts from 0 and spinner starts from 1.
                    Log.v("item Pos", "" + chosenItemPosition);
                    Intent launchGameIntent = getPackageManager().getLaunchIntentForPackage(fullManifestName[chosenItemPosition]);
                    launchGameIntent.putExtra("selectedPatientID", String.valueOf(getPatientID));
                    launchGameIntent.putExtra("selectedGameID", gameID[chosenItemPosition]);
                    Log.v("patientID", "intent:" + String.valueOf(getPatientID)); //check patient ID to be passed on to nxt intent.
                    Log.v("gameID", "intent:" + gameID[chosenItemPosition]); //check game ID to be passed on to nxt intent.
                    startActivity(launchGameIntent);
                    playedGames.append(" "+ gameName[chosenItemPosition]+" "); //log down what kind of games played.
                } else {
                    assignedGamesSpinner.setError(errorMessage);
                }
            }
        });

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