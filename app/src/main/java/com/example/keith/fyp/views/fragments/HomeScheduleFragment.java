package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.views.EmptyAndAutofitRecyclerView;
import com.example.keith.fyp.views.ScheduleRecycleView;
import com.example.keith.fyp.views.adapters.HomeScheduleAdapter;
import com.example.keith.fyp.views.adapters.PatientListAdapter;
import com.melnykov.fab.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Keith on 22/9/2015.
 */
public class HomeScheduleFragment extends Fragment {
    private View rootView;
    private ScheduleRecycleView scheduleRecyclerView;
    private HomeScheduleAdapter scheduleAdapter;

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
        // ================
        // Prepare the patient list
        // ================
        scheduleAdapter = new HomeScheduleAdapter(getActivity(), getScheduleList());

        scheduleRecyclerView = (ScheduleRecycleView) rootView.findViewById(R.id.test);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
        scheduleRecyclerView.setNoSearchResultView(rootView.findViewById(R.id.no_search_result_container1));
//        scheduleRecyclerView.setNoPatientView(rootView.findViewById(R.id.no_patient_container));
//        scheduleRecyclerView.addItemDecoration(
//                new SpacesItemDecoration((int) getResources().getDimension(R.dimen.activity_content_container_padding)));
//
//        createPatientFab = (FloatingActionButton) rootView.findViewById(R.id.create_patient_fab);
//        createPatientFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openCreatePatientActivity();
//            }
//        });

        return rootView;
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
            Schedule schedule1 = new Schedule(photoid[i], pname[i], pid[i], cActivity[i], nActivity[i]);
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
