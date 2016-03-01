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
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.adapters.AllergyListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;

import java.util.ArrayList;

/**
 * Fragment to display the allergy form in creating patient
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class CreatePatientInfoFormAllergyFragment extends CreatePatientInfoFormFragment implements PatientInfoFormListFragment {
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

        allergyList = createdPatient.getAllergyList();
        allergyListAdapter = new AllergyListAdapter(getActivity(), this, allergyList, createdPatient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addAllergyForm = rootView.findViewById(R.id.add_new_allergy_form);
        allergyRadioGroup = (RadioGroup) rootView.findViewById(R.id.allergy_option_radio_group);
        hasAllergyRadioButton = (RadioButton) rootView.findViewById(R.id.has_allergy_radio_button);
        hasNoAllergyRadioButton = (RadioButton) rootView.findViewById(R.id.has_no_allergy_radio_button);

        allergyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.has_allergy_radio_button) {
                    addAllergyForm.setVisibility(View.VISIBLE);
                    createdPatient.setHasAllergy(true);
                } else {
                    addAllergyForm.setVisibility(View.GONE);
                    createdPatient.setHasAllergy(false);
                    createdPatient.getAllergyList().clear();
                }
            }
        });

        if(createdPatient.getHasAllergy() != null) {
            if(createdPatient.getHasAllergy()) {
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

                newAllergyNameEditText.setError(null);
                newAllergyReactionEditText.setError(null);
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            highlightEmptyField(bundle);
        }

        return rootView;
    }

    private void highlightEmptyField(Bundle bundle) {
        ArrayList<Integer> emptyFieldIdList = bundle.getIntegerArrayList(Global.EXTRA_EMPTY_FIELD_ID_LIST);

        if (emptyFieldIdList.contains(Global.HAS_ALLERGY_RADIO_GROUP)) {
            Toast.makeText(getActivity(), R.string.error_msg_no_required_allergy, Toast.LENGTH_SHORT).show();
        }
    }

    private void createAndAddAllergy() {
        String allergyName = newAllergyNameEditText.getText().toString();
        String allergyReaction = newAllergyReactionEditText.getText().toString();
        String allergyNotes = newAllergyNotesEditText.getText().toString();

        // Input checking
        boolean isValidForm = true;
        String errorMessage = getResources().getString(R.string.error_msg_field_required);

        if(UtilsString.isEmpty(allergyName)) {
            newAllergyNameEditText.setError(errorMessage);
            isValidForm = false;
        }
        if(UtilsString.isEmpty(allergyNotes)) {
            newAllergyNotesEditText.setError(errorMessage);
            isValidForm = false;
        }
        if(UtilsString.isEmpty(allergyReaction)) {
            newAllergyReactionEditText.setError(errorMessage);
            isValidForm = false;
        }

        if(isValidForm) {
            Allergy newAllergy = new Allergy(allergyName, allergyReaction, allergyNotes);
            allergyList.add(0, newAllergy);
            allergyListAdapter.notifyItemInserted(0);

            resetNewAllergyFields();

            closeExpandableAddAllergy();
            hideKeyboard();
        }
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

    /**
     * Delete an allergy from the list
     *
     * @param selectedItemIdx index of allergy to be deleted
     */
    public void deleteItem(int selectedItemIdx) {
        allergyList.remove(selectedItemIdx);
        allergyListAdapter.notifyItemRemoved(selectedItemIdx);
    }
}
