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
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.views.adapters.ProblemLogListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import org.joda.time.DateTime;

import java.util.ArrayList;

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
    private EditText newProblemLogNotesEditText;
    private ExpandableLayout addProblemLogExpandableLayout;

    private ArrayList<ProblemLog> problemLogList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_info_form_problem_log, container, false);

        problemLogList = viewedPatient.getProblemLogList();
        problemLogListAdapter = new ProblemLogListAdapter(getActivity(), this, problemLogList, viewedPatient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

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

        return rootView;
    }

    private void createAndAddProblemLog() {
        DateTime creationDate = DateTime.now();
        String category = newProblemLogCategorySpinner.getSelectedItem().toString();
        String notes = newProblemLogNotesEditText.getText().toString();

        // TODO: check for valid entry

        ProblemLog newProblemLog = new ProblemLog(creationDate, category, notes);

        problemLogList.add(0, newProblemLog);
        problemLogListAdapter.notifyItemInserted(0);

        resetNewProblemLogFields();

        closeExpandableAddProblemLog();
        hideKeyboard();
    }

    private void resetNewProblemLogFields() {
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