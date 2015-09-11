package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Patient;
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

        public AllergyListViewHolder(View itemView) {
            super(itemView);
            allergyName = (EditText) itemView.findViewById(R.id.allergy_list_name_edit_text);
            allergyReaction = (EditText) itemView.findViewById(R.id.allergy_list_reaction_edit_text);
            allergyNotes = (EditText) itemView.findViewById(R.id.allergy_list_notes_edit_text);
            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            menuButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == menuButton) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_allergy_item);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int selectedItemIdx = getAdapterPosition();

            switch (item.getItemId()) {
                case R.id.action_allergy_edit:
                    return true;
                case R.id.action_allergy_delete:
                    fragment.deleteItem(selectedItemIdx);
                    return true;
                default:
                    return false;
            }
        }
    }
}