package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.PrescriptionListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's prescription information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientInfoFormPrescriptionFragment extends ViewPatientInfoFormFragment implements PatientInfoFormListFragment {
    private LinearLayout rootView;

    private ExpandableLayout addPrescriptionExpandable;

    private EditText nameEditText;
    private EditText dosageEditText;
    private EditText freqEditText;
    private EditText instructionEditText;
    private EditText startDatePicker;
    private EditText endDatePicker;
    private EditText notesEditText;
    private MaterialSpinner beforeAfterMealSpinner;

    private ArrayList<Prescription> prescriptionList;
    private PrescriptionListAdapter prescriptionListAdapter;

    private LinearLayout addNewPrescriptionHeaderContainer;
    private Button cancelNewPrescriptionButton;
    private Button addNewPrescriptionButton;
    private RecyclerView prescriptionRecyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_patient_info_form_prescription, container, false);

        addPrescriptionExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_prescription_expandable_layout);

        nameEditText = (EditText) rootView.findViewById(R.id.prescription_name_edit_text);
        dosageEditText = (EditText) rootView.findViewById(R.id.dosage_edit_text);
        freqEditText = (EditText) rootView.findViewById(R.id.prescription_freq_edit_text);
        instructionEditText = (EditText) rootView.findViewById(R.id.instruction_edit_text);
        startDatePicker = (EditText) rootView.findViewById(R.id.presc_start_date_picker);
        endDatePicker = (EditText) rootView.findViewById(R.id.presc_end_date_picker);
        notesEditText = (EditText) rootView.findViewById(R.id.presc_notes_edit_text);
        beforeAfterMealSpinner = (MaterialSpinner) rootView.findViewById(R.id.before_after_meal_spinner);

        UtilsUi.setupEditTextToBeDatePicker(startDatePicker, getString(R.string.select_prescription_start_date));
        UtilsUi.setupEditTextToBeDatePicker(endDatePicker, getString(R.string.select_prescription_end_date));

        prescriptionList = viewedPatient.getPrescriptionList();
        prescriptionListAdapter = new PrescriptionListAdapter(getActivity(), this, prescriptionList, viewedPatient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        notesEditText.setImeActionLabel("Add", EditorInfo.IME_ACTION_DONE);
        notesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAndAddPrescription();
                    return true;
                }
                return false;
            }
        });

        addNewPrescriptionHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_prescription_header_container);
        addNewPrescriptionHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addPrescriptionExpandable.isOpened()) {
                    resetNewPrescriptionFields();
                }
                return false;
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_prescription_before_after_meal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beforeAfterMealSpinner.setAdapter(adapter);

        prescriptionRecyclerView = (RecyclerView) rootView.findViewById(R.id.prescription_recycler_view);
        prescriptionRecyclerView.setLayoutManager(layoutManager);
        prescriptionRecyclerView.setAdapter(prescriptionListAdapter);
        prescriptionRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        cancelNewPrescriptionButton = (Button) rootView.findViewById(R.id.cancel_add_prescription_button);
        cancelNewPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddPrescription();
                resetNewPrescriptionFields();

                nameEditText.setError(null);
                dosageEditText.setError(null);
                freqEditText.setError(null);
                startDatePicker.setError(null);
                endDatePicker.setError(null);
                beforeAfterMealSpinner.setError(null);
            }
        });

        addNewPrescriptionButton = (Button) rootView.findViewById(R.id.add_new_prescription_button);
        addNewPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddPrescription();
            }
        });

        return rootView;
    }

    private void createAndAddPrescription() {
        String name = nameEditText.getText().toString();
        String dosage = dosageEditText.getText().toString();

        Integer freqPerDay = null;
        String freqStr = freqEditText.getText().toString();
        if (freqStr != null && !freqStr.isEmpty()) {
            freqPerDay = Integer.parseInt(freqStr);
        }

        String instruction = instructionEditText.getText().toString();

        String startDateStr = startDatePicker.getText().toString();
        DateTime startDate = null;
        if(startDateStr != null && !startDateStr.isEmpty()) {
            startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
        }

        String endDateStr = endDatePicker.getText().toString();
        DateTime endDate = null;
        if(endDateStr != null && !endDateStr.isEmpty()) {
            endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
        }

        String beforeAfterMealStr = null;
        if(beforeAfterMealSpinner.getSelectedItemPosition() != 0) {
            beforeAfterMealStr = beforeAfterMealSpinner.getSelectedItem().toString();
        }

        String notes = notesEditText.getText().toString();


        // Input checking
        boolean isValidForm = true;
        String errorMessage = getResources().getString(R.string.error_msg_field_required);

        if(UtilsString.isEmpty(name)) {
            nameEditText.setError(errorMessage);
            isValidForm = false;
        }

        if(UtilsString.isEmpty(dosage)) {
            dosageEditText.setError(errorMessage);
            isValidForm = false;
        }

        if(UtilsString.isEmpty(freqStr)) {
            freqEditText.setError(errorMessage);
            isValidForm = false;
        }

        if(UtilsString.isEmpty(startDateStr)) {
            startDatePicker.setError(errorMessage);
            isValidForm = false;
        }

        if(UtilsString.isEmpty(endDateStr)) {
            endDatePicker.setError(errorMessage);
            isValidForm = false;
        }

        if(UtilsString.isEmpty(beforeAfterMealStr)) {
            beforeAfterMealSpinner.setError(errorMessage);
            isValidForm = false;
        }

        if(startDate != null && endDate != null) {
            if(startDate.isAfter(endDate)) {
                endDatePicker.setError(activity.getString(R.string.error_msg_end_date_must_be_after_start_date));
                isValidForm = false;
            }
        }

        if(isValidForm) {
            Prescription newPrescription = new Prescription(name, dosage, freqPerDay, instruction, startDate, endDate, beforeAfterMealStr, notes);
            prescriptionList.add(0, newPrescription);
            prescriptionListAdapter.notifyItemInserted(0);

            resetNewPrescriptionFields();

            closeExpandableAddPrescription();
            hideKeyboard();
        }
    }

    private void resetNewPrescriptionFields() {
        nameEditText.setText(null);
        dosageEditText.setText(null);
        freqEditText.setText(null);
        instructionEditText.setText(null);
        startDatePicker.setText(null);
        endDatePicker.setText(null);
        notesEditText.setText(null);
        beforeAfterMealSpinner.setSelection(0);
    }

    private void closeExpandableAddPrescription() {
        if (addPrescriptionExpandable.isOpened()) {
            addPrescriptionExpandable.hide();
        }
    }

    /**
     * Delete an prescription from the list
     *
     * @param selectedItemIdx index of prescription to be deleted
     */
    public void deleteItem(int selectedItemIdx) {
        prescriptionList.remove(selectedItemIdx);
        prescriptionListAdapter.notifyItemRemoved(selectedItemIdx);
    }
}
