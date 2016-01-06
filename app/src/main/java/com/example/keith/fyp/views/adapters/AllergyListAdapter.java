package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormAllergyFragment;
import com.example.keith.fyp.views.fragments.PatientInfoFormListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link android.support.v7.widget.RecyclerView.Adapter} to display list of allergy
 */
public class AllergyListAdapter extends RecyclerView.Adapter<AllergyListAdapter.AllergyListViewHolder> {

    private LayoutInflater inflater;
    private List<Allergy> allergyList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;
    private Context context;
    /**
     * Create allergy list adapter with the specified values
     *
     * @param context context of the application
     * @param patientInfoFormAllergyFragment fragment of the UI
     * @param allergyList list of allergy to be displayed
     * @param patient patient to be edited
     */
    public AllergyListAdapter(Context context, PatientInfoFormListFragment patientInfoFormAllergyFragment, List<Allergy> allergyList, Patient patient) {
        this.inflater = LayoutInflater.from(context);
        this.allergyList = allergyList;
        this.fragment = patientInfoFormAllergyFragment;
        this.patient = patient;
        this.context = context;
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
                    allergyName.setError(null);
                    allergyReaction.setError(null);
                    restoreOldFieldsValue();
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String allergyNameStr = allergyName.getText().toString();
                    String allergyReactionStr = allergyReaction.getText().toString();
                    String allergyNotesStr = allergyNotes.getText().toString();

                    // Inout checking
                    boolean isValidForm = true;
                    String errorMessage = v.getContext().getResources().getString(R.string.error_msg_field_required);

                    if(UtilsString.isEmpty(allergyNameStr)) {
                        allergyName.setError(errorMessage);
                        isValidForm = false;
                    }

                    if(UtilsString.isEmpty(allergyReactionStr)) {
                        allergyReaction.setError(errorMessage);
                        isValidForm = false;
                    }

                    if(isValidForm) {
                        ArrayList<Allergy> allergyList = patient.getAllergyList();
                        Allergy allergy = allergyList.get(getAdapterPosition());
                        dbfile db = new dbfile();
                        int x = db.getPatientId(patient.getNric());
                        String oldValue = allergy.getName() + ";" + allergy.getReaction() + ";" + allergy.getNotes();
                        String newValue = allergyNameStr+ ";" + allergyReactionStr + ";" + allergyNotesStr;
                        int getRowID = db.getAllergyRowId(oldValue,x);
                        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
                        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
                        db.updatePatientSpec(oldValue, newValue, x, 1, getRowID, UserTypeID, UserID);
                        if(UserTypeID == 3){
                            allergy.setName(allergyNameStr);
                            allergy.setReaction(allergyReactionStr);
                            allergy.setNotes(allergyNotesStr);
                            Toast.makeText(context, "Successfully Changed!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
                        }

                        setFormEditable(false);
                        if (expandableLayout.isOpened()) {
                            expandableLayout.hide();
                        }
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