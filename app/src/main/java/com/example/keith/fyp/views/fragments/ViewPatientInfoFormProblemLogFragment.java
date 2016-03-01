package com.example.keith.fyp.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.comparators.ProblemLogComparator;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.ProblemLogListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;
import com.mikepenz.iconics.utils.Utils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's problem log information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientInfoFormProblemLogFragment extends ViewPatientInfoFormFragment implements PatientInfoFormListFragment {

    private LinearLayout rootView;
    private LinearLayout addNewProblemLogHeaderContainer;
    private RecyclerView problemLogRecyclerView;
    private LinearLayoutManager layoutManager;
    private ProblemLogListAdapter problemLogListAdapter;
    private Button cancelNewProblemLogButton;
    private Button addNewProblemLogButton;
    private MaterialSpinner newProblemLogCategorySpinner;
    private EditText addProblemLogFromDateEditText;
    private EditText newProblemLogNotesEditText;
    private ExpandableLayout addProblemLogExpandableLayout;
    private Spinner problemLogFilterSpinner;

    private ArrayList<ProblemLog> problemLogList;
    private ProblemLogComparator problemLogComparator = new ProblemLogComparator();

    private String selectedCategoryFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_info_form_problem_log, container, false);

        problemLogList = new ArrayList<>();
        problemLogList.addAll(viewedPatient.getProblemLogList());
        Collections.sort(problemLogList, problemLogComparator);
        problemLogListAdapter = new ProblemLogListAdapter(getActivity(), this, problemLogList, viewedPatient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addProblemLogFromDateEditText = (EditText) rootView.findViewById(R.id.add_problem_log_from_date_edit_text);
        UtilsUi.setupEditTextToBeDatePicker(addProblemLogFromDateEditText, "Select problem log from date");

        problemLogRecyclerView = (RecyclerView) rootView.findViewById(R.id.problem_log_recycler_view);
        problemLogRecyclerView.setLayoutManager(layoutManager);
        problemLogRecyclerView.setAdapter(problemLogListAdapter);
        problemLogRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        addProblemLogExpandableLayout = (ExpandableLayout) rootView.findViewById(R.id.add_problem_log_expandable_layout);

        addNewProblemLogHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_problem_log_header_container);
        addNewProblemLogHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addProblemLogExpandableLayout.isOpened()) {
                    resetNewProblemLogFields();
                } else {
                    DateTime currentDateTime = DateTime.now();
                    String currentDateStr = currentDateTime.toString(Global.DATE_FORMAT);
                    addProblemLogFromDateEditText.setText(currentDateStr);
                }
                return false;
            }
        });

        cancelNewProblemLogButton = (Button) rootView.findViewById(R.id.cancel_add_problem_log_button);
        cancelNewProblemLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddProblemLog();
                resetNewProblemLogFields();
            }
        });

        newProblemLogCategorySpinner = (MaterialSpinner) rootView.findViewById(R.id.add_problem_log_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_problem_log_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newProblemLogCategorySpinner.setAdapter(adapter);

        newProblemLogNotesEditText = (EditText) rootView.findViewById(R.id.add_problem_log_notes_edit_text);

        newProblemLogNotesEditText.setImeActionLabel("Add", EditorInfo.IME_ACTION_DONE);
        newProblemLogNotesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAndAddProblemLog();
                    return true;
                }
                return false;
            }
        });

        addNewProblemLogButton = (Button) rootView.findViewById(R.id.add_new_problem_log_button);
        addNewProblemLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddProblemLog();
            }
        });

        problemLogFilterSpinner = (Spinner) rootView.findViewById(R.id.problem_log_category_filter_spinner);
        final ArrayAdapter<CharSequence> problemLogFilterAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.option_problem_log_category_filter, android.R.layout.simple_spinner_item);
        problemLogFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problemLogFilterSpinner.setAdapter(problemLogFilterAdapter);
        problemLogFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryFilter = (String) parent.getItemAtPosition(position);
                if (selectedCategoryFilter.equals("None")) {
                    selectedCategoryFilter = null;
                }
                problemLogListAdapter.getFilter().filter(selectedCategoryFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return rootView;
    }

    private void createAndAddProblemLog() {

             DateTime creationDate = Global.DATE_FORMAT.parseDateTime(addProblemLogFromDateEditText.getText().toString());
             String category = newProblemLogCategorySpinner.getSelectedItem().toString();
             String notes = newProblemLogNotesEditText.getText().toString();
        if(notes.isEmpty()) {
            newProblemLogNotesEditText.setError("This field is required!");
        }else{
            ProblemLog newProblemLog = new ProblemLog(UtilsUi.generateUniqueId(), creationDate, category, notes);
            addProblemLog(newProblemLog);
        }


    }

    private void addProblemLog(ProblemLog newProblemLog) {
        SharedPreferences pref;
        pref = getActivity().getSharedPreferences("Login", 0);
        final int UserID = Integer.parseInt(pref.getString("userid", ""));
        final int UserTypeID = Integer.parseInt(pref.getString("userTypeId", ""));
        if(UserTypeID ==3) {
            viewedPatient.getProblemLogList().add(0, newProblemLog);
            problemLogList.clear();
            problemLogList.addAll(viewedPatient.getProblemLogList());
            Collections.sort(problemLogList, problemLogComparator);
            problemLogListAdapter.updateFilterOriginalList(problemLogList);
            problemLogListAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getActivity(),"Pending Supervisor Approval", Toast.LENGTH_LONG).show();
        }
        if (selectedCategoryFilter != null) {
            problemLogListAdapter.getFilter().filter(selectedCategoryFilter);
        }
        String info = newProblemLog.getCreationDate() + ";" + newProblemLog.getCategory() + ";" + newProblemLog.getNotes();
        dbfile db = new dbfile();
        int x = db.getPatientId(viewedPatient.getNric());
        db.insertPatientSpec(info, x, 12, UserTypeID,UserID);
        resetNewProblemLogFields();

        closeExpandableAddProblemLog();
        hideKeyboard();
    }

    private void resetNewProblemLogFields() {
        addProblemLogFromDateEditText.setText(null);
        newProblemLogCategorySpinner.setSelection(0);
        newProblemLogNotesEditText.setText(null);
    }

    private void closeExpandableAddProblemLog() {
        if (addProblemLogExpandableLayout.isOpened()) {
            addProblemLogExpandableLayout.hide();
        }
    }

    /**
     * Delete an problem log from the list
     *
     * @param selectedItemIdx index of problem log to be deleted
     */
    public void deleteItem(int selectedItemIdx) {
        String oldValue = problemLogList.get(selectedItemIdx).getCreationDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toString() + ";" + problemLogList.get(selectedItemIdx).getCategory() +";" +
                problemLogList.get(selectedItemIdx).getNotes();
        dbfile db = new dbfile();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        int x = db.getPatientId(selectedPatientNric);
        int getRowID = db.getAllergyRowId(oldValue, x);
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        db.deletePatientSpec(oldValue, x, 12, getRowID, UserTypeID, UserID);
        if(UserTypeID == 3) {
             problemLogList.remove(selectedItemIdx);
            problemLogListAdapter.notifyItemRemoved(selectedItemIdx);
            Toast.makeText(getActivity(), "Successfully Changed", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Pending Supervisor Approval!", Toast.LENGTH_LONG).show();
        }
    }
}