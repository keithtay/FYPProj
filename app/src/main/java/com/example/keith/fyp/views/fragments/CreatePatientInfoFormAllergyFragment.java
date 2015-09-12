package com.example.keith.fyp.views.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.views.adapters.AllergyListAdapter;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 11/9/2015.
 */
public class CreatePatientInfoFormAllergyFragment extends CreatePatientInfoFormFragment {
    private LinearLayout rootView;
    private RecyclerView allergyRecyclerView;
    private LinearLayoutManager layoutManager;
    private AllergyListAdapter allergyListAdapter;
    private Button cancelNewAllergyButton;
    private Button addNewAllergyButton;
    private EditText newAllergyNameEditText;
    private EditText newAllergyReactionEditText;
    private EditText newAllergyNotesEditText;
    private ExpandableLayout addAllergyExpandableLayout;
    private ArrayList<Allergy> allergyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_allergy, container, false);

        allergyList = DataHolder.getCreatedPatient().getAllergyList();
        allergyListAdapter = new AllergyListAdapter(getActivity(), this, allergyList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        allergyRecyclerView = (RecyclerView) rootView.findViewById(R.id.allergy_recycler_view);
        allergyRecyclerView.setLayoutManager(layoutManager);
        allergyRecyclerView.setAdapter(allergyListAdapter);
        allergyRecyclerView.addItemDecoration(
                new SpacesCardItemDecoration((int) getResources().getDimension(R.dimen.paper_card_row_spacing)));

        addAllergyExpandableLayout = (ExpandableLayout) rootView.findViewById(R.id.add_allergy_expandable_layout);

        cancelNewAllergyButton = (Button) rootView.findViewById(R.id.cancel_allergy_button);
        cancelNewAllergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeExpandableAddAllergy();
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
        allergyListAdapter.notifyDataSetChanged();

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
        allergyListAdapter.notifyDataSetChanged();
    }

    class SpacesCardItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesCardItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space / 2;
            outRect.top = space / 2;

            int numOfChild = parent.getChildCount();

            // Last item
            if (parent.getChildAdapterPosition(view) + 1 >= numOfChild) {
                outRect.bottom = space * 2;
            }
        }
    }
}
