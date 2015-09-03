package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sutrisno on 4/9/2015.
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientListViewHolder> {

    private LayoutInflater inflater;
    private List<Patient> patientList = Collections.emptyList();

    public PatientListAdapter(Context context, List<Patient> patientList) {
        this.inflater = LayoutInflater.from(context);
        this.patientList = patientList;
    }

    @Override
    public PatientListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.patient_card, parent, false);
        PatientListViewHolder viewHolder = new PatientListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientListViewHolder holder, int position) {
        Patient currentPatient = patientList.get(position);
        holder.patientPhoto.setImageResource(currentPatient.getPhotoId());
        holder.patientName.setText(currentPatient.getName());
        holder.patientNric.setText(currentPatient.getNric());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class PatientListViewHolder extends RecyclerView.ViewHolder {
        ImageView patientPhoto;
        TextView patientName;
        TextView patientNric;

        public PatientListViewHolder(View itemView) {
            super(itemView);

            patientPhoto = (ImageView) itemView.findViewById(R.id.patientPhoto);
            patientName = (TextView) itemView.findViewById(R.id.patientName);
            patientNric = (TextView) itemView.findViewById(R.id.patientNric);
        }
    }
}
