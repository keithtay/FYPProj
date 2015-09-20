package com.example.keith.fyp.views;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.views.activities.CreatePatientActivity;
import com.example.keith.fyp.views.adapters.PatientListAdapter;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PatientListFragment extends Fragment {

    private View rootView;
    private EmptyAndAutofitRecyclerView patientListRecyclerView;
    private PatientListAdapter patientListAdapter;
    private FloatingActionButton createPatientFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_patient_list, container, false);

        setHasOptionsMenu(true);

        // ================
        // Prepare the patient list
        // ================
        patientListAdapter = new PatientListAdapter(getActivity(), getPatientList());

        patientListRecyclerView = (EmptyAndAutofitRecyclerView) rootView.findViewById(R.id.patient_list_grid);
        patientListRecyclerView.setAdapter(patientListAdapter);
        patientListRecyclerView.setNoSearchResultView(rootView.findViewById(R.id.no_search_result_container));
        patientListRecyclerView.setNoPatientView(rootView.findViewById(R.id.no_patient_container));
        patientListRecyclerView.addItemDecoration(
                new SpacesItemDecoration((int) getResources().getDimension(R.dimen.activity_content_container_padding)));

        createPatientFab = (FloatingActionButton) rootView.findViewById(R.id.create_patient_fab);
        createPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreatePatientActivity();
            }
        });

        return rootView;
    }

    // TODO: Receive patient data from database, instead of using hardcoded data
    private List<Patient> getPatientList() {
        List<Patient> patientList = new ArrayList<>();

        int[] patientPhotos = {R.drawable.avatar_01,
                R.drawable.avatar_02,
                R.drawable.avatar_03,
                R.drawable.avatar_04,
                R.drawable.avatar_05,
                R.drawable.avatar_06,
                R.drawable.avatar_07,
                R.drawable.avatar_08,
                R.drawable.avatar_09,
                R.drawable.avatar_10,
                R.drawable.avatar_11,
                R.drawable.avatar_12,
                R.drawable.avatar_13,
                R.drawable.avatar_14,
                R.drawable.avatar_15,
                R.drawable.avatar_16,
                R.drawable.avatar_17,
                R.drawable.avatar_18,
                R.drawable.avatar_19,
                R.drawable.avatar_20};

        String[] patientNames = {"Andy",
                "Bob",
                "Charlie",
                "David",
                "Eve",
                "Florence",
                "Gordon",
                "Hilda",
                "Ivan",
                "Justin",
                "Kelvin",
                "Loreen",
                "Mario",
                "Nellie",
                "Olive",
                "Prince",
                "Ross",
                "Susan",
                "Tanya",
                "Valerie"};

        String[] patientNric = {"123456",
                "654321",
                "984203",
                "562745",
                "234745",
                "234643",
                "234534",
                "234643",
                "345634",
                "743164",
                "346343",
                "256344",
                "234654",
                "234653",
                "753134",
                "356723",
                "257455",
                "634234",
                "345634",
                "745234"};

        for (int i = 0; i < patientPhotos.length; i++) {
            Patient newPatient = new Patient(patientNames[i], patientNric[i], patientPhotos[i]);
            patientList.add(newPatient);
        }

        return patientList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_patientlist, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.action_patient_search));

        patientListRecyclerView.setSearchFieldView(searchView);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                patientListAdapter.getFilter().filter(query);
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
            outRect.left = space / 2;
            outRect.right = space / 2;
            outRect.bottom = space / 2;
            outRect.top = space / 2;

            int numOfColumn = 4;
            int numOfChild = parent.getChildCount();
            int numOfItemInLastCol = (numOfChild % numOfColumn) + 1;
            int lastRowStartIndex = numOfChild - numOfItemInLastCol;

            // First row
            if(parent.getChildAdapterPosition(view) / numOfColumn == 0) {
                outRect.top = space;
            }

            // Last row
            if(parent.getChildAdapterPosition(view) >= lastRowStartIndex) {
                outRect.bottom = space;
            }
        }
    }

    public void openCreatePatientActivity()
    {
        Intent intent = new Intent(getActivity(), CreatePatientActivity.class);
        startActivity(intent);
    }
}
