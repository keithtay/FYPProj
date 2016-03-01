package com.example.keith.fyp.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Vital;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.VitalListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's vital information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientInfoFormVitalFragment extends ViewPatientInfoFormFragment implements PatientInfoFormListFragment {
    private LinearLayout rootView;
    private LinearLayout addNewVitalHeaderContainer;
    private ExpandableLayout addVitalExpandable;

    private EditText vitalDateTakenEditText;
    private EditText vitalTimeTakenEditText;
    private EditText temperatureEditText;
    private EditText bloodPressureSystolEditText;
    private EditText bloodPressureDiastolEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private EditText notesEditText;
    private MaterialSpinner vitalLabelSpinner;

    private RecyclerView vitalRecyclerView;

    private Button cancelNewVitalButton;
    private Button addNewVitalButton;

    private ArrayList<Vital> vitalList;
    private VitalListAdapter vitalListAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_vital, container, false);

        temperatureEditText = (EditText) rootView.findViewById(R.id.temperature_edit_text);
        bloodPressureSystolEditText = (EditText) rootView.findViewById(R.id.blood_pressure_systol_edit_text);
        bloodPressureDiastolEditText = (EditText) rootView.findViewById(R.id.blood_pressure_diastol_edit_text);
        heightEditText = (EditText) rootView.findViewById(R.id.height_edit_text);
        weightEditText = (EditText) rootView.findViewById(R.id.weight_edit_text);
        notesEditText = (EditText) rootView.findViewById(R.id.vital_notes_edit_text);

        notesEditText.setImeActionLabel("Add", EditorInfo.IME_ACTION_DONE);
        notesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAndAddVital();
                    return true;
                }
                return false;
            }
        });

        vitalDateTakenEditText = (EditText) rootView.findViewById(R.id.vital_date_picker);
        UtilsUi.setupEditTextToBeDatePicker(vitalDateTakenEditText, getString(R.string.select_vital_date));

        vitalTimeTakenEditText = (EditText) rootView.findViewById(R.id.vital_time_picker);
        UtilsUi.setupEditTextToBeTimePicker(vitalTimeTakenEditText, getString(R.string.select_time_add_new_vital));

        addVitalExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_vital_expandable_layout);

        addNewVitalHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_vital_header_container);
        addNewVitalHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!addVitalExpandable.isOpened()) {
                    DateTime currentDateTime = DateTime.now();
                    String currentDateStr = currentDateTime.toString(Global.DATE_FORMAT);
                    String currentTimeStr = currentDateTime.toString(Global.TIME_FORMAT);
                    vitalDateTakenEditText.setText(currentDateStr);
                    vitalTimeTakenEditText.setText(currentTimeStr);
                } else {
                    resetNewVitalFields();
                }
                return false;
            }
        });

        vitalLabelSpinner = (MaterialSpinner) rootView.findViewById(R.id.vital_label_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_vital_label, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vitalLabelSpinner.setAdapter(adapter);

        vitalList = viewedPatient.getVitalList();
        vitalListAdapter = new VitalListAdapter(getActivity(), this, vitalList, viewedPatient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        vitalRecyclerView = (RecyclerView) rootView.findViewById(R.id.vital_recycler_view);
        vitalRecyclerView.setLayoutManager(layoutManager);
        vitalRecyclerView.setAdapter(vitalListAdapter);
        vitalRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        cancelNewVitalButton = (Button) rootView.findViewById(R.id.cancel_add_vital_button);
        cancelNewVitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddVital();
                resetNewVitalFields();
            }
        });

        addNewVitalButton = (Button) rootView.findViewById(R.id.add_new_vital_button);
        addNewVitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddVital();
            }
        });

        return rootView;
    }

    /**
     * Delete an vital from the list
     *
     * @param selectedItemIdx index of vital to be deleted
     */
    public void deleteItem(int selectedItemIdx) {
        String isBeforeAfterMealValue;
        if(vitalList.get(selectedItemIdx).isBeforeMeal() == false){
            isBeforeAfterMealValue = "false";
        }else{
            isBeforeAfterMealValue = "true";

        }
        String oldValue = vitalList.get(selectedItemIdx).getDateTimeTaken().toString() + ";" + isBeforeAfterMealValue + ";" +
                String.valueOf(vitalList.get(selectedItemIdx).getTemperature()) + ";" + String.valueOf(vitalList.get(selectedItemIdx).getBloodPressureSystol()) + ";"+
                String.valueOf(vitalList.get(selectedItemIdx).getBloodPressureDiastol()) + ";" + String.valueOf(vitalList.get(selectedItemIdx).getHeight()) + ";" +
                String.valueOf(vitalList.get(selectedItemIdx).getWeight()) + ";" + vitalList.get(selectedItemIdx).getNotes();
        dbfile db = new dbfile();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        int x = db.getPatientId(selectedPatientNric);
        int getRowID = db.getAllergyRowId(oldValue, x);
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        db.deletePatientSpec(oldValue, x, 2, getRowID, UserTypeID, UserID);
        if(UserTypeID == 3){
            vitalList.remove(selectedItemIdx);
            vitalListAdapter.notifyItemRemoved(selectedItemIdx);
            Toast.makeText(getActivity(), "Successfully Changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
        }
        }

    private void closeExpandableAddVital() {
        if (addVitalExpandable.isOpened()) {
            addVitalExpandable.hide();
        }
    }

    private void resetNewVitalFields() {
        vitalDateTakenEditText.setText(null);
        vitalTimeTakenEditText.setText(null);
        temperatureEditText.setText(null);
        bloodPressureSystolEditText.setText(null);
        bloodPressureDiastolEditText.setText(null);
        heightEditText.setText(null);
        weightEditText.setText(null);
        notesEditText.setText(null);
        vitalLabelSpinner.setSelection(0);
    }

    private void createAndAddVital() {
        String dateTakenStr = vitalDateTakenEditText.getText().toString();
        String timeTakenStr = vitalTimeTakenEditText.getText().toString();
        Boolean isValid = true;
        Float temperature = null;
        String temperatureStr = temperatureEditText.getText().toString();
        if (temperatureStr != null && !temperatureStr.isEmpty()) {
            temperature = Float.parseFloat(temperatureStr);
        } else {
            temperatureEditText.setError("This field is required!");
            isValid = false;
        }

        Float bloodPressureSystol = null;
        String bloodPressureSystolStr = bloodPressureSystolEditText.getText().toString();
        if (bloodPressureSystolStr != null && !bloodPressureSystolStr.isEmpty()) {
            bloodPressureSystol = Float.parseFloat(bloodPressureSystolStr);
        } else {
            bloodPressureSystolEditText.setError("This field is required!");
            isValid = false;
        }

        Float bloodPressureDiastol = null;
        String bloodPressureDiastolStr = bloodPressureDiastolEditText.getText().toString();
        if (bloodPressureDiastolStr != null && !bloodPressureDiastolStr.isEmpty()) {
            bloodPressureDiastol = Float.parseFloat(bloodPressureDiastolStr);
        } else {
            bloodPressureDiastolEditText.setError("This field is required!");
            isValid = false;
        }

        Float height = null;
        String heightStr = heightEditText.getText().toString();
        if (heightStr != null && !heightStr.isEmpty()) {
            height = Float.parseFloat(heightStr);
        } else {
            heightEditText.setError("This field is required!");
            isValid = false;
        }

        Float weight = null;
        String weightStr = weightEditText.getText().toString();
        if (weightStr != null && !weightStr.isEmpty()) {
            weight = Float.parseFloat(weightStr);
        } else {
            weightEditText.setError("This field is required!");
            isValid = false;
        }

        String notes = notesEditText.getText().toString();

        if (notes.equals(null) || notes.isEmpty()) {
            notesEditText.setError("This field is required!");
            isValid = false;
        }
        String beforeOrAfterMealStr;
        Boolean isBeforeMeal = null;
        if (vitalLabelSpinner.getSelectedItemPosition() != 0) {
            beforeOrAfterMealStr = vitalLabelSpinner.getSelectedItem().toString();

            if (beforeOrAfterMealStr.equals("Before meal")) {
                isBeforeMeal = true;
            } else if (beforeOrAfterMealStr.equals("After meal")) {
                isBeforeMeal = false;
            }else{
                isBeforeMeal = null;
                vitalLabelSpinner.setError("This field is required");
                isValid = false;
            }
        }

        DateTime dateTaken = Global.DATE_FORMAT.parseDateTime(dateTakenStr);
        DateTime timeTaken = Global.TIME_FORMAT.parseDateTime(timeTakenStr);
        DateTime dateTimeToSave = DateTime.now();
        dateTimeToSave = dateTimeToSave.withYear(dateTaken.getYear());
        dateTimeToSave = dateTimeToSave.withMonthOfYear(dateTaken.getMonthOfYear());
        dateTimeToSave = dateTimeToSave.withDayOfMonth(dateTaken.getDayOfMonth());
        dateTimeToSave = dateTimeToSave.withHourOfDay(timeTaken.getHourOfDay());
        dateTimeToSave = dateTimeToSave.withMinuteOfHour(timeTaken.getMinuteOfHour());

        if (isValid) {
            SharedPreferences pref;
            pref = getActivity().getSharedPreferences("Login", 0);
            final int UserID = Integer.parseInt(pref.getString("userid", ""));
            final int UserTypeID = Integer.parseInt(pref.getString("userTypeId", ""));
            String info = dateTimeToSave.toString() + ";" + isBeforeMeal.toString() + ";" + String.valueOf(temperature) + ";" +
                    String.valueOf(bloodPressureSystol) + ";" + String.valueOf(bloodPressureDiastol)
                    + ";" + String.valueOf(height) + ";" + String.valueOf(weight) + ";" + notes;
            dbfile db = new dbfile();


            int x = db.getPatientId(viewedPatient.getNric());

            db.insertPatientSpec(info, x, 2, UserTypeID, UserID);
            if (UserTypeID == 3) {
                Vital newVital = new Vital(dateTimeToSave, isBeforeMeal, temperature, bloodPressureSystol, bloodPressureDiastol, height, weight, notes);
                vitalList.add(0, newVital);
                vitalListAdapter.notifyItemInserted(0);
            } else {
                Toast.makeText(getActivity(), "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
            }

            resetNewVitalFields();

            closeExpandableAddVital();
            hideKeyboard();
        }
    }
}