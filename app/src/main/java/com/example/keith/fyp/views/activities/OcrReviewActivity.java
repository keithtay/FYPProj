package com.example.keith.fyp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.CreatePatientCommunicator;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Activity to display the OCR result
 */
public class OcrReviewActivity extends AppCompatActivity  implements Drawer.OnDrawerItemClickListener {

    private CheckBox selectAllCheckBox;
    private EditText nricEditText;
    private CheckBox nricCheckBox;
    private EditText firstNameEditText;
    private CheckBox firstNameCheckBox;
    private EditText lastNameEditText;
    private CheckBox lastNameCheckBox;
    private EditText dobEditText;
    private CheckBox dobCheckBox;
    private MaterialSpinner genderSpinner;
    private CheckBox genderCheckBox;
    private Button cancelButton;
    private Button autofillButton;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private String selectedPatientDraftId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        final int UserID = Integer.parseInt(preferences.getString("userid",""));
        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState, UserID);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        selectedPatientDraftId = mPrefs.getString(Global.STATE_SELECTED_PATIENT_DRAFT_ID, null);

        /** ===================================================
         *
         * VIEWS INITIALIZATION
         *
         ====================================================== */
        selectAllCheckBox = (CheckBox) findViewById(R.id.select_all_check_box);
        nricEditText = (EditText) findViewById(R.id.nric_edit_text);
        nricCheckBox = (CheckBox) findViewById(R.id.nric_check_box);
        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        firstNameCheckBox = (CheckBox) findViewById(R.id.first_name_check_box);
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        lastNameCheckBox = (CheckBox) findViewById(R.id.last_name_check_box);
        dobEditText = (EditText) findViewById(R.id.dob_edit_text);
        dobCheckBox = (CheckBox) findViewById(R.id.dob_check_box);
        genderSpinner = (MaterialSpinner) findViewById(R.id.gender_spinner);
        genderCheckBox = (CheckBox) findViewById(R.id.gender_check_box);
        cancelButton = (Button) findViewById(R.id.cancel_ocr_button);
        autofillButton = (Button) findViewById(R.id.autofill_ocr_button);

        selectAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nricCheckBox.setChecked(isChecked);
                firstNameCheckBox.setChecked(isChecked);
                lastNameCheckBox.setChecked(isChecked);
                dobCheckBox.setChecked(isChecked);
                genderCheckBox.setChecked(isChecked);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(OcrReviewActivity.this);
            }
        });

        autofillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidForm = true;

                boolean includeNric = nricCheckBox.isChecked();
                String nricStr = nricEditText.getText().toString();
                if (includeNric && UtilsString.isEmpty(nricStr)) {
                    isValidForm = false;
                    nricEditText.setError(getResources().getString(R.string.error_msg_field_required));
                }

                boolean includeFirstName = firstNameCheckBox.isChecked();
                String firstNameStr = firstNameEditText.getText().toString();
                if (includeFirstName && UtilsString.isEmpty(firstNameStr)) {
                    isValidForm = false;
                    firstNameEditText.setError(getResources().getString(R.string.error_msg_field_required));
                }

                boolean includeLastName = lastNameCheckBox.isChecked();
                String lastNameStr = lastNameEditText.getText().toString();
                if (includeLastName && UtilsString.isEmpty(lastNameStr)) {
                    isValidForm = false;
                    lastNameEditText.setError(getResources().getString(R.string.error_msg_field_required));
                }

                boolean includeDob = dobCheckBox.isChecked();
                String dobStr = dobEditText.getText().toString();
                if (includeDob && UtilsString.isEmpty(dobStr)) {
                    isValidForm = false;
                    dobEditText.setError(getResources().getString(R.string.error_msg_field_required));
                }

                boolean includeGender = genderCheckBox.isChecked();
                String[] genderArray = getResources().getStringArray(R.array.option_gender);
                ArrayList<String> genderList = new ArrayList<String>(Arrays.asList(genderArray));
                String genderStr = genderSpinner.getSelectedItem().toString();
                if (includeGender && !genderList.contains(genderStr)) {
                    isValidForm = false;
                    genderSpinner.setError(getResources().getString(R.string.error_msg_field_required));
                }

                if (isValidForm) {
                    Patient createdPatient = DataHolder.getCreatedPatient();

                    if (includeNric) {
                        createdPatient.setNric(nricStr);
                    }

                    if (includeFirstName) {
                        createdPatient.setFirstName(firstNameStr);
                    }

                    if (includeLastName) {
                        createdPatient.setLastName(lastNameStr);
                    }

                    if (includeDob) {
                        createdPatient.setDob(Global.DATE_FORMAT.parseDateTime(dobStr));
                    }

                    if (includeGender) {
                        char gender = 'M';
                        if (genderList.indexOf(genderStr) == 1) {
                            gender = 'F';
                        }
                        createdPatient.setGender(gender);
                    }

                    NavUtils.navigateUpFromSameTask(OcrReviewActivity.this);
                }
            }
        });

        UtilsUi.setupEditTextToBeDatePicker(dobEditText, "Select Date of Birth");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.option_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        /** ===================================================
         *
         * GET OCR RESULT
         *
         ====================================================== */
        ArrayList<String> recognizedText = getIntent().getStringArrayListExtra(Global.EXTRA_RECOGNIZED_TEXT);

        // Get NRIC
        String[] nricPart = recognizedText.get(0).split(" ");
        String nric = null;
        for (int i = nricPart.length - 1; i >= 0; i--) {
            if (nricPart[i].length() > 3) {
                nric = nricPart[i];
                break;
            }
        }

        // Get First Name and Last Name
        String fullName = recognizedText.get(1);
        int offset = 0;
        if (recognizedText.size() == 4) {
            offset = 1;
            fullName += " " + recognizedText.get(2);
        }
        String[] namePart = fullName.trim().split(" ");
        String firstName = UtilsString.capsFirstLetter(namePart[0]);

        String[] lastNameArray = new String[namePart.length - 1];
        System.arraycopy(namePart, 1, lastNameArray, 0, lastNameArray.length);
        String lastName = UtilsString.arrayToString(lastNameArray);
        lastName = UtilsString.capsFirstLetter(lastName);

        // Get Date of Birth and Sex
        String dobAndGenderStr = recognizedText.get(2 + offset);
        String[] dobAndGenderPart = dobAndGenderStr.split(" ");
        String dobStr = dobAndGenderPart[0];
        if (dobStr.length() == 10) {
            dobStr = dobStr.substring(0, 2) + "-" + dobStr.substring(3, 5) + "-" + dobStr.substring(6);
        } else {
            dobStr = null;
        }
        String gender = null;
        for (int i = dobAndGenderPart.length - 1; i >= 1; i--) {
            if (dobAndGenderPart[i].equals("M") || dobAndGenderPart[i].equals("F")) {
                gender = dobAndGenderPart[i];
                break;
            }
        }

        /** ===================================================
         *
         * SET OCR RESULT TO THE FIELDS
         *
         ====================================================== */
        if (!UtilsString.isEmpty(nric)) {
            nricEditText.setText(nric);
            nricCheckBox.setChecked(true);
        }
        if (!UtilsString.isEmpty(firstName)) {
            firstNameEditText.setText(firstName);
            firstNameCheckBox.setChecked(true);
        }
        if (!UtilsString.isEmpty(lastName)) {
            lastNameEditText.setText(lastName);
            lastNameCheckBox.setChecked(true);
        }
        if (!UtilsString.isEmpty(dobStr)) {
            DateTime dob = Global.NRIC_DATE_FORMAT.parseDateTime(dobStr);
            dobEditText.setText(dob.toString(Global.DATE_FORMAT));
            dobCheckBox.setChecked(true);
        }
        if (!UtilsString.isEmpty(gender)) {
            if(gender.equals("M")) {
                genderSpinner.setSelection(1);
            } else {
                genderSpinner.setSelection(2);
            }
            genderCheckBox.setChecked(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        final int selectedIdentifier = drawerItem.getIdentifier();

        if (selectedIdentifier != Global.NAVIGATION_PATIENT_LIST_ID) {
            checkWhetherInTheMiddleOfCreatingPatient(selectedIdentifier);
        }

        return true;
    }


    private void checkWhetherInTheMiddleOfCreatingPatient(final int selectedIdentifier) {
        boolean isEditing = false;

        if (UtilsString.isEmpty(selectedPatientDraftId)) {
            isEditing = !DataHolder.getCreatedPatient().equals(new Patient());
        } else {
            isEditing = !DataHolder.getCreatedPatient().equals(DataHolder.getCreatedPatientEditInitial());
        }

        if (isEditing) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            if (UtilsString.isEmpty(selectedPatientDraftId)) {
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

        if (!UtilsString.isEmpty(json)) {
            Type type = new TypeToken<HashMap<String, Patient>>() {
            }.getType();
            draftMap = gson.fromJson(json, type);
        }

        Patient createdPatient = DataHolder.getCreatedPatient();
        String mapKey;
        if (!UtilsString.isEmpty(selectedPatientDraftId)) {
            mapKey = selectedPatientDraftId;
        } else {
            DateTime now = DateTime.now();
            mapKey = now.toString(Global.DATE_TIME_FORMAT);

            String firstName = createdPatient.getFirstName();
            if (!UtilsString.isEmpty(firstName)) {
                mapKey += " | " + firstName;
            }
        }
        draftMap.put(mapKey, createdPatient);

        json = gson.toJson(draftMap);
        prefsEditor.putString(Global.SP_CREATE_PATIENT_DRAFT, json);
        prefsEditor.commit();
    }

    private void openAnotherTabInDashboardActivity(int selectedIdentifier) {
        DataHolder.resetCreatedPatient();

        miniDrawer.updateItem(Global.NAVIGATION_PATIENT_LIST_ID);

        Intent intent = new Intent(OcrReviewActivity.this, DashboardActivity.class);
        intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
