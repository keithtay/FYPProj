package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.FilterList;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.views.customviews.ScheduleRecycleView;
import com.example.keith.fyp.views.adapters.HomeScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Keith on 22/9/2015.
 */
public class HomeScheduleFragment extends Fragment {
    private View rootView;
    private ScheduleRecycleView scheduleRecyclerView;
    private HomeScheduleAdapter scheduleAdapter;
    private SpinAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_schedule, container, false);

        // To be able to change the menu in action bar
        setHasOptionsMenu(true);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Patient's Current Activity");


        TextView t1 = (TextView) rootView.findViewById(R.id.currentTimeDisplay);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm ");
        String strDate = sdf.format(c.getTime());
        t1.setText("Current Time: " + strDate);

        FilterList[] filter = new FilterList[3];
        filter[0] = new FilterList();
        filter[0].setId(1);
        filter[0].setName("Filter By: Patient's Name");
        filter[1] = new FilterList();
        filter[1].setId(2);
        filter[1].setName("Filter By: Patient's ID");
        filter[2] = new FilterList();
        filter[2].setId(3);
        filter[2].setName("Filter By: Next Activity Time");

         adapter = new SpinAdapter(getActivity(), android.R.layout.simple_spinner_item, filter);
        Spinner s = (Spinner) rootView.findViewById(R.id.spinnerFilter);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                FilterList filter = adapter.getItem(position);
                List<Schedule> getFilterList = new ArrayList<Schedule>();
                getFilterList = getScheduleList();
                // Here you can do the action you want to...
                if(filter.getId() == 1){
                    Collections.sort(getFilterList, Schedule.COMPARE_BY_NAME);
                }else if(filter.getId() == 2 ){
                    Collections.sort(getFilterList, Schedule.COMPARE_BY_ID);
                }else{
                    Collections.sort(getFilterList, Schedule.COMPARE_BY_SCHEDULE);
                }
                scheduleAdapter = new HomeScheduleAdapter(getActivity(), getFilterList);
                scheduleRecyclerView = (ScheduleRecycleView) rootView.findViewById(R.id.test);
                scheduleRecyclerView.setAdapter(scheduleAdapter);
                Toast.makeText(getActivity(), "Name: " + filter.getName(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        // ================
        // Prepare the patient list
        // ================
        scheduleAdapter = new HomeScheduleAdapter(getActivity(), getScheduleList());

        scheduleRecyclerView = (ScheduleRecycleView) rootView.findViewById(R.id.test);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
        scheduleRecyclerView.setNoSearchResultView(rootView.findViewById(R.id.no_search_result_container1));

        return rootView;
    }
    public class SpinAdapter extends ArrayAdapter<FilterList>{

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private FilterList[] values;

        public SpinAdapter(Context context, int textViewResourceId,
                           FilterList[] values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        public int getCount(){
            return values.length;
        }

        public FilterList getItem(int position){
            return values[position];
        }

        public long getItemId(int position){
            return position;
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = new TextView(context);
            label.setTextColor(Color.RED);
            label.setTextSize(17);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            label.setText(values[position].getName());

            // And finally return your dynamic (or custom) view for each spinner item
            return label;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(values[position].getName());
            label.setTextSize(17);

            return label;
        }
    }

    private List<Schedule> getScheduleList() {
        List<Schedule> scheduleList = new ArrayList<>();

        int[] photoid = {R.drawable.avatar_01,
                R.drawable.avatar_02,
                R.drawable.avatar_03,
                R.drawable.avatar_04,
                R.drawable.avatar_05,
                R.drawable.avatar_06,
                R.drawable.avatar_07,
                R.drawable.avatar_08,
                R.drawable.avatar_09,
                R.drawable.avatar_10,
                };

        String[] pname = {"Andy",
                "Bob",
                "Charlie",
                "David",
                "Eve",
                "Florence",
                "Gordon",
                "Hilda",
                "Ivan",
                "Justin",
                };

        String[] pid = {"123456",
                "654321",
                "984203",
                "562745",
                "234745",
                "234643",
                "234534",
                "234643",
                "345634",
                "743164",
                };

        String[] cActivity = {"Sleep",
                "Eat",
                "Eat",
                "Eat",
                "Sleep",
                "Bath",
                "Bath",
                "Talk",
                "Eat",
                "Game",
        };

        String[] nActivityTime = {"16:30hrs- 17:00hrs",
                "16:30hrs- 17:00hrs",
                "16:35hrs- 17:00hrs",
                "16:40hrs- 17:00hrs",
                "16:45hrs- 17:00hrs",
                "16:50hrs- 17:00hrs",
                "16:55hrs- 17:00hrs",
                "17:00hrs- 17:00hrs",
                "17:30hrs- 18:00hrs",
                "17:30hrs- 18:00hrs",
        };
        String[] nActivity = {"Eat",
                "Eat",
                "Eat",
                "Eat",
                "Eat",
                "Eat",
                "Eat",
                "Eat",
                "Eat",
                "Eat",
        };
        for (int i = 0; i < photoid.length; i++) {
            Schedule schedule1 = new Schedule(photoid[i], pname[i], pid[i], cActivity[i],nActivityTime[i], nActivity[i]);
            scheduleList.add(schedule1);
        }

        return scheduleList;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_patientlist, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.action_patient_search));

        scheduleRecyclerView.setSearchFieldView(searchView);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                scheduleAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
    }
    class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space ;
            outRect.right = space ;
            outRect.bottom = space ;
            outRect.top = space ;

            int numOfColumn = 4;
            int numOfChild = parent.getChildCount();
            int numOfItemInLastCol = (numOfChild % numOfColumn) + 1;
            int lastRowStartIndex = numOfChild - numOfItemInLastCol;

//             First row
            if(parent.getChildAdapterPosition(view) / numOfColumn == 0) {
                outRect.top = space;
            }

            // Last row
            if(parent.getChildAdapterPosition(view) >= lastRowStartIndex) {
                outRect.bottom = space;
            }
        }
    }
}
