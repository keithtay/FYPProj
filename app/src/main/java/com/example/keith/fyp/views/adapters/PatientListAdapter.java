package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.activities.ViewScheduleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sutrisno on 4/9/2015.
 */
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientListViewHolder> implements Filterable {

    private PatientFilter patientFilter;
    private LayoutInflater inflater;
    private List<Patient> patientList;
    private List<Patient> filteredPatientList;
    private Context context;

    public PatientListAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.patientList = new ArrayList<>();
        this.patientList.addAll(patientList);
        this.filteredPatientList = new ArrayList<>();
        this.filteredPatientList.addAll(patientList);
    }

    @Override
    public PatientListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rootView = inflater.inflate(R.layout.patient_card, parent, false);
        PatientListViewHolder viewHolder = new PatientListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientListViewHolder holder, int position) {
        Patient currentPatient = filteredPatientList.get(position);
        holder.patientPhoto.setImageBitmap(currentPatient.getPhoto());
        holder.patientName.setText(currentPatient.getFullName());
        holder.patientNric.setText(currentPatient.getNric());
    }

    @Override
    public int getItemCount() {
        return filteredPatientList.size();
    }

    @Override
    public Filter getFilter() {
        if (patientFilter == null) {
            patientFilter = new PatientFilter(this, patientList);
        }
        return patientFilter;
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString(Global.STATE_SELECTED_PATIENT_NRIC, patientNric.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(context, ViewScheduleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }
            });
        }
    }

    class PatientFilter extends Filter {
        private PatientListAdapter adapter;
        private List<Patient> patientList;
        private List<Patient> filteredPatientList;

        public PatientFilter(PatientListAdapter patientListAdapter, List<Patient> patientList) {
            super();
            this.adapter = patientListAdapter;
            this.patientList = new ArrayList<>(patientList);
            this.filteredPatientList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            this.filteredPatientList.clear();
            final FilterResults results = new FilterResults();

            String constaintStr = constraint.toString().toLowerCase();

            if (constraint.length() == 0) {
                this.filteredPatientList.addAll(this.patientList);
            } else {
                for (final Patient patient : this.patientList) {
                    if (patient.getFirstName().toLowerCase().contains(constaintStr)) {
                        this.filteredPatientList.add(patient);
                    }
                }
            }
            results.values = this.filteredPatientList;
            results.count = this.filteredPatientList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredPatientList.clear();
            adapter.filteredPatientList.addAll((ArrayList<Patient>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
