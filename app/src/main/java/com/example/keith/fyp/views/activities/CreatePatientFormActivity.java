package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.keith.fyp.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.utils.CreatedPatientEmptyFieldChecker;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class CreatePatientFormActivity extends PatientFormActivity implements Drawer.OnDrawerItemClickListener {

    private FloatingActionButton saveNewPatientFab;

    private FragmentManager fragmentManager;

    private Patient createdPatient;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createdPatient = DataHolder.getCreatedPatient();

        Intent intent = getIntent();
        int selectedCategoryIndex = intent.getIntExtra(Global.EXTRA_SELECTED_CATEGORY, 0);
        Fragment fragmentToBeDisplayed = CreatePatientFormFragmentDecoder.getFragment(selectedCategoryIndex);

        ArrayList<Integer> emptyFieldIdList = intent.getIntegerArrayListExtra(Global.EXTRA_EMPTY_FIELD_ID_LIST);
        if(emptyFieldIdList != null && emptyFieldIdList.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList(Global.EXTRA_EMPTY_FIELD_ID_LIST, emptyFieldIdList);
            fragmentToBeDisplayed.setArguments(bundle);
        }

        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentToBeDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();

        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        saveNewPatientFab = (FloatingActionButton) findViewById(R.id.save_new_patient_fab);
        saveNewPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> emptyFieldIdList = CreatedPatientEmptyFieldChecker.checkPersonalInfo();

                if (emptyFieldIdList.size() > 0) {
                    Toast.makeText(CreatePatientFormActivity.this, R.string.error_msg_fill_in_all_personal_info, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreatePatientFormActivity.this, R.string.error_msg_no_required_allergy, Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        miniDrawer.updateItem(Global.NAVIGATION_PATIENT_LIST_ID);

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
        startActivity(intent);

        return true;
    }
}
