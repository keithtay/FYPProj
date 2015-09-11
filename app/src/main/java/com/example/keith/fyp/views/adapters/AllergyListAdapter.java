package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sutrisno on 11/9/2015.
 */
public class AllergyListAdapter extends RecyclerView.Adapter<AllergyListAdapter.AllergyListViewHolder> {

    private LayoutInflater inflater;
    private List<Allergy> allergyList;

    public AllergyListAdapter(Context context, List<Allergy> allergyList) {
        this.inflater = LayoutInflater.from(context);
        this.allergyList = allergyList;;
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

    class AllergyListViewHolder extends RecyclerView.ViewHolder {
        EditText allergyName;
        EditText allergyReaction;
        EditText allergyNotes;

        public AllergyListViewHolder(View itemView) {
            super(itemView);
            allergyName = (EditText) itemView.findViewById(R.id.allergy_list_name_edit_text);
            allergyReaction = (EditText) itemView.findViewById(R.id.allergy_list_reaction_edit_text);
            allergyNotes = (EditText) itemView.findViewById(R.id.allergy_list_notes_edit_text);
        }
    }
}