package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormAllergyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sutrisno on 11/9/2015.
 */
public class AllergyListAdapter extends RecyclerView.Adapter<AllergyListAdapter.AllergyListViewHolder> {

    private LayoutInflater inflater;
    private List<Allergy> allergyList;
    private CreatePatientInfoFormAllergyFragment fragment;

    public AllergyListAdapter(Context context, CreatePatientInfoFormAllergyFragment createPatientInfoFormAllergyFragment, List<Allergy> allergyList) {
        this.inflater = LayoutInflater.from(context);
        this.allergyList = allergyList;
        this.fragment = createPatientInfoFormAllergyFragment;
    }

    @Override
    public AllergyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.allergy_card, parent, false);
        AllergyListViewHolder viewHolder = new AllergyListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllergyListViewHolder holder, int position) {
        Allergy allergy = allergyList.get(position);
        holder.allergyName.setText(allergy.getName());
        holder.allergyReaction.setText(allergy.getReaction());
        holder.allergyNotes.setText(allergy.getNotes());
    }

    @Override
    public int getItemCount() {
        return allergyList.size();
    }

    class AllergyListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        EditText allergyName;
        EditText allergyReaction;
        EditText allergyNotes;
        ImageView menuButton;
        ExpandableLayout expandableLayout;
        Button cancelButton;
        Button saveButton;

        String oldAllergyName;
        String oldAllergyReaction;
        String oldAllergyNotes;

        public AllergyListViewHolder(View itemView) {
            super(itemView);
            allergyName = (EditText) itemView.findViewById(R.id.allergy_list_name_edit_text);
            allergyReaction = (EditText) itemView.findViewById(R.id.allergy_list_reaction_edit_text);
            allergyNotes = (EditText) itemView.findViewById(R.id.allergy_list_notes_edit_text);
            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_allergy_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_allergy_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_allergy_save_button);
            menuButton.setOnClickListener(this);

            setFormEditable(false);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
                    }
                    restoreOldFieldsValue();
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Allergy> allergyList = DataHolder.getCreatedPatient().getAllergyList();
                    Allergy allergy = allergyList.get(getAdapterPosition());
                    allergy.setName(allergyName.getText().toString());
                    allergy.setReaction(allergyReaction.getText().toString());
                    allergy.setNotes(allergyNotes.getText().toString());

                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
                    }
                }
            });
        }

        private void restoreOldFieldsValue() {
            allergyName.setText(oldAllergyName);
            allergyReaction.setText(oldAllergyReaction);
            allergyNotes.setText(oldAllergyNotes);
        }

        @Override
        public void onClick(View v) {
            if (v == menuButton) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_edit_item);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_item_edit:
                    oldAllergyName = allergyName.getText().toString();
                    oldAllergyReaction = allergyReaction.getText().toString();
                    oldAllergyNotes = allergyNotes.getText().toString();

                    if(!expandableLayout.isOpened()) {
                        expandableLayout.show();
                    }
                    setFormEditable(true);
                    return true;
                case R.id.action_item_delete:
                    int selectedItemIdx = getAdapterPosition();
                    fragment.deleteItem(selectedItemIdx);
                    return true;
                default:
                    return false;
            }
        }

        private void setFormEditable(boolean isEditable) {
            allergyName.setFocusable(isEditable);
            allergyReaction.setFocusable(isEditable);
            allergyNotes.setFocusable(isEditable);
            allergyName.setFocusableInTouchMode(isEditable);
            allergyReaction.setFocusableInTouchMode(isEditable);
            allergyNotes.setFocusableInTouchMode(isEditable);
        }
    }
}