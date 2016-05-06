package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.CreatePatientFormFragmentEncoder;
import com.example.keith.fyp.utils.CreatedPatientEmptyFieldChecker;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity to display the create patient form
 */
public class CreatePatientFormActivity extends PatientFormActivity implements Drawer.OnDrawerItemClickListener {

    private FloatingActionButton saveNewPatientFab;

    private FragmentManager fragmentManager;

    private Patient createdPatient;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private String selectedPatientDraftId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedPatientDraftId = getIntent().getStringExtra(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID);

        createdPatient = DataHolder.getCreatedPatient();
        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        final int UserID = Integer.parseInt(preferences.getString("userid",""));
        Intent intent = getIntent();
        int selectedCategoryIndex = intent.getIntExtra(Global.EXTRA_SELECTED_CATEGORY, 0);
        Fragment fragmentToBeDisplayed = CreatePatientFormFragmentEncoder.getFragment(selectedCategoryIndex);

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
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState, UserID);
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

        if(selectedIdentifier != Global.NAVIGATION_PATIENT_LIST_ID) {
            checkWhetherInTheMiddleOfCreatingPatient(selectedIdentifier);
        }

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void checkWhetherInTheMiddleOfCreatingPatient(final int selectedIdentifier) {
        boolean isEditing = false;

        if(UtilsString.isEmpty(selectedPatientDraftId)) {
            isEditing =  !DataHolder.getCreatedPatient().equals(new Patient());
        } else {
            isEditing = !DataHolder.getCreatedPatient().equals(DataHolder.getCreatedPatientEditInitial());
        }

        if(isEditing) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            if(UtilsString.isEmpty(selectedPatientDraftId)) {
                builder.title("You are in the middle of creating a patient. Do you want to save current patient as draft?");
                builder.positiveText(R.string.button_proceed_with_save);
            } else {
                builder.title("You have changed the patient draft. Do you want to save the changes?");
                builder.positiveText(R.string.button_save_draft_changes);
            }

            builder.neutralText(R.string.button_no);
            builder.negativeText(R.string.button_cancel);

            builder.callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    savePatientAsDraft();
                    openAnotherTabInDashboardActivity(selectedIdentifier);
                }

                @Override
                public void onNeutral(MaterialDialog dialog) {
                    super.onNeutral(dialog);
                    openAnotherTabInDashboardActivity(selectedIdentifier);
                }
            });
            builder.show();
        } else {
            openAnotherTabInDashboardActivity(selectedIdentifier);
        }
    }

    private void savePatientAsDraft() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String json = mPrefs.getString(Global.SP_CREATE_PATIENT_DRAFT, "");
        HashMap<String, Patient> draftMap = new HashMap();

        if(!UtilsString.isEmpty(json)) {
            Type type = new TypeToken<HashMap<String, Patient>>(){}.getType();
            draftMap = gson.fromJson(json, type);
        }

        String mapKey;
        if(!UtilsString.isEmpty(selectedPatientDraftId)) {
            mapKey = selectedPatientDraftId;
        } else {
            DateTime now = DateTime.now();
            mapKey = now.toString(Global.DATE_TIME_FORMAT);
        }
        draftMap.put(mapKey, DataHolder.getCreatedPatient());

        json = gson.toJson(draftMap);
        prefsEditor.putString(Global.SP_CREATE_PATIENT_DRAFT, json);
        prefsEditor.commit();
    }

    private void openAnotherTabInDashboardActivity(int selectedIdentifier) {
        DataHolder.resetCreatedPatient();

        miniDrawer.updateItem(Global.NAVIGATION_PATIENT_LIST_ID);

        Intent intent = new Intent(CreatePatientFormActivity.this, DashboardActivity.class);
        intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
