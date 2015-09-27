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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.views.adapters.AllergyListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import java.util.ArrayList;

public class ViewPatientInfoFormAllergyFragment extends ViewPatientInfoFormFragment implements PatientInfoFormListFragment {

    private LinearLayout rootView;
    private LinearLayout addNewAllergyHeaderContainer;
    private RecyclerView allergyRecyclerView;
    private LinearLayoutManager layoutManager;
    private AllergyListAdapter allergyListAdapter;
    private Button cancelNewAllergyButton;
    private Button addNewAllergyButton;
    private EditText newAllergyNameEditText;
    private EditText newAllergyReactionEditText;
    private EditText newAllergyNotesEditText;
    private ExpandableLayout addAllergyExpandableLayout;

    private View addAllergyForm;
    private RadioGroup allergyRadioGroup;
    private RadioButton hasAllergyRadioButton;
    private RadioButton hasNoAllergyRadioButton;

    private ArrayList<Allergy> allergyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_patient_info_form_allergy, container, false);

        allergyList = viewedPatient.getAllergyList();
        allergyListAdapter = new AllergyListAdapter(getActivity(), this, allergyList, viewedPatient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addAllergyForm = rootView.findViewById(R.id.add_new_allergy_form);
        allergyRadioGroup = (RadioGroup) rootView.findViewById(R.id.allergy_option_radio_group);
        hasAllergyRadioButton = (RadioButton) rootView.findViewById(R.id.has_allergy_radio_button);
        hasNoAllergyRadioButton = (RadioButton) rootView.findViewById(R.id.has_no_allergy_radio_button);

        allergyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.has_allergy_radio_button) {
                    addAllergyForm.setVisibility(View.VISIBLE);
                    viewedPatient.setHasAllergy(true);
                } else {
                    if (allergyList.size() > 0) {
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
                        builder.content("Are you sure you want that the patient has no allergy? Once confirmed, the patient allergy list will be cleared.");

                        builder.positiveText(R.string.button_yes);
                        builder.negativeText(R.string.button_cancel);

                        builder.callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                addAllergyForm.setVisibility(View.GONE);
                                viewedPatient.setHasAllergy(false);
                                viewedPatient.getAllergyList().clear();
                            }
                        });
                        builder.show();
                    } else {
                        addAllergyForm.setVisibility(View.GONE);
                        viewedPatient.setHasAllergy(false);
                        viewedPatient.getAllergyList().clear();
                    }
                }
            }
        });

        if (viewedPatient.getHasAllergy() != null) {
            if (viewedPatient.getHasAllergy()) {
                hasAllergyRadioButton.setChecked(true);
            } else {
                hasNoAllergyRadioButton.setChecked(true);
            }
        }

        allergyRecyclerView = (RecyclerView) rootView.findViewById(R.id.allergy_recycler_view);
        allergyRecyclerView.setLayoutManager(layoutManager);
        allergyRecyclerView.setAdapter(allergyListAdapter);
        allergyRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        addAllergyExpandableLayout = (ExpandableLayout) rootView.findViewById(R.id.add_allergy_expandable_layout);

        addNewAllergyHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_allergy_header_container);
        addNewAllergyHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addAllergyExpandableLayout.isOpened()) {
                    resetNewAllergyFields();
                }
                return false;
            }
        });

        cancelNewAllergyButton = (Button) rootView.findViewById(R.id.cancel_allergy_button);
        cancelNewAllergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddAllergy();
                resetNewAllergyFields();
            }
        });

        newAllergyNameEditText = (EditText) rootView.findViewById(R.id.new_allergy_name_edit_text);
        newAllergyReactionEditText = (EditText) rootView.findViewById(R.id.new_allergy_reaction_edit_text);
        newAllergyNotesEditText = (EditText) rootView.findViewById(R.id.new_allergy_notes_edit_text);

        newAllergyNotesEditText.setImeActionLabel("Add", EditorInfo.IME_ACTION_DONE);
        newAllergyNotesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAndAddAllergy();
                    return true;
                }
                return false;
            }
        });

        addNewAllergyButton = (Button) rootView.findViewById(R.id.add_new_allergy_button);
        addNewAllergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndAddAllergy();
            }
        });

        return rootView;
    }

    private void createAndAddAllergy() {
        String allergyName = newAllergyNameEditText.getText().toString();
        String allergyReaction = newAllergyReactionEditText.getText().toString();
        String allergyNotes = newAllergyNotesEditText.getText().toString();

        // TODO: check for valid entry

        Allergy newAllergy = new Allergy(allergyName, allergyReaction, allergyNotes);
        allergyList.add(0, newAllergy);
        allergyListAdapter.notifyItemInserted(0);

        resetNewAllergyFields();

        closeExpandableAddAllergy();
        hideKeyboard();
    }

    private void resetNewAllergyFields() {
        newAllergyNameEditText.setText(null);
        newAllergyReactionEditText.setText(null);
        newAllergyNotesEditText.setText(null);
    }

    private void closeExpandableAddAllergy() {
        if (addAllergyExpandableLayout.isOpened()) {
            addAllergyExpandableLayout.hide();
        }
    }

    public void deleteItem(int selectedItemIdx) {
        allergyList.remove(selectedItemIdx);
        allergyListAdapter.notifyItemRemoved(selectedItemIdx);
    }
}