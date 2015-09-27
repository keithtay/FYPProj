package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.views.activities.ViewScheduleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keith on 22/9/2015.
 */
public class HomeScheduleAdapter extends RecyclerView.Adapter<HomeScheduleAdapter.HomeScheduleHolder> implements Filterable {
    private homeScheduleFilter homeFilter;
    private List<Schedule> scheduleList;
    private List<Schedule> filteredScheduleList;
    private Context context;
    private LayoutInflater inflater;

    public HomeScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.scheduleList = new ArrayList<>();
        this.scheduleList.addAll(scheduleList);
        this.filteredScheduleList = new ArrayList<>();
        this.filteredScheduleList.addAll(scheduleList);
    }

    @Override
    public HomeScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.home_schedule_data, parent, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewScheduleActivity.class);
                context.startActivity(intent);
            }
        });
        HomeScheduleHolder viewHolder = new HomeScheduleHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeScheduleHolder holder, int position) {
        Schedule currentSchedule = filteredScheduleList.get(position);
        holder.patientPhoto.setImageResource(currentSchedule.getPhotoid());
        holder.patientName.setText(currentSchedule.getName());
        holder.patientNric.setText(currentSchedule.getNric());
        holder.currActivity.setText(currentSchedule.getcActivity());
        holder.nextActivity.setText(currentSchedule.getnActivity());

    }


    @Override
    public int getItemCount() {
        return filteredScheduleList.size();
    }

    public Filter getFilter() {
        if (homeFilter == null) {
            homeFilter = new homeScheduleFilter(this, scheduleList);
        }
        return homeFilter;
    }

    class HomeScheduleHolder extends RecyclerView.ViewHolder {
        ImageButton patientPhoto;
        TextView patientName;
        TextView patientNric;
        TextView currActivity;
        TextView nextActivity;

        public HomeScheduleHolder(View itemView) {
            super(itemView);
            patientPhoto = (ImageButton) itemView.findViewById(R.id.patientImage1);
            patientName = (TextView) itemView.findViewById(R.id.pid);
            patientNric = (TextView) itemView.findViewById(R.id.pname);
            currActivity = (TextView) itemView.findViewById(R.id.cActivity);
            nextActivity = (TextView) itemView.findViewById(R.id.nActivity);
        }
    }

    class homeScheduleFilter extends Filter {
        private HomeScheduleAdapter adapter;
        private List<Schedule> patientList;
        private List<Schedule> filteredPatientList;

        public homeScheduleFilter(HomeScheduleAdapter homeScheduleAdapter, List<Schedule> patientList) {
            super();
            this.adapter = homeScheduleAdapter;
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
                for (final Schedule schedule : this.patientList) {
                    if (schedule.getName().toLowerCase().contains(constaintStr)) {
                        this.filteredPatientList.add(schedule);
                    }
                }
            }
            results.values = this.filteredPatientList;
            results.count = this.filteredPatientList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredScheduleList.clear();
            adapter.filteredScheduleList.addAll((ArrayList<Schedule>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
