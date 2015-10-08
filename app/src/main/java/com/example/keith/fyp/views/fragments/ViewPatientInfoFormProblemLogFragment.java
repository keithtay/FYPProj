package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
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

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.comparators.ProblemLogComparator;
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

        ProblemLog newProblemLog = new ProblemLog(UtilsUi.generateUniqueId(), creationDate, category, notes);

        addProblemLog(newProblemLog);
    }

    private void addProblemLog(ProblemLog newProblemLog) {
        viewedPatient.getProblemLogList().add(0, newProblemLog);
        problemLogList.clear();
        problemLogList.addAll(viewedPatient.getProblemLogList());

        Collections.sort(problemLogList, problemLogComparator);
        problemLogListAdapter.updateFilterOriginalList(problemLogList);
        problemLogListAdapter.notifyDataSetChanged();

        if (selectedCategoryFilter != null) {
            problemLogListAdapter.getFilter().filter(selectedCategoryFilter);
        }

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

    public void deleteItem(int selectedItemIdx) {
        problemLogList.remove(selectedItemIdx);
        problemLogListAdapter.notifyItemRemoved(selectedItemIdx);
    }
}