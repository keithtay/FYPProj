package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.EmptyAndAutofitRecyclerView;
import com.example.keith.fyp.views.adapters.PatientListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {

    private EmptyAndAutofitRecyclerView patientListRecyclerView;
    private PatientListAdapter patientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);

        // ================
        // Prepare the patient list
        // ================
        patientListAdapter = new PatientListAdapter(this, getPatientList());

        patientListRecyclerView = (EmptyAndAutofitRecyclerView) findViewById(R.id.patientListGrid);
        patientListRecyclerView.setAdapter(patientListAdapter);
        patientListRecyclerView.setNoSearchResultView(findViewById(R.id.noSearchResultContainer));
        patientListRecyclerView.setNoPatientView(findViewById(R.id.noPatientContainer));
        patientListRecyclerView.addItemDecoration(
                new SpacesItemDecoration((int) getResources().getDimension(R.dimen.activity_content_container_padding)));

        UtilsUi.setNavigationDrawer(this, savedInstanceState);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patientlist, menu);

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
            //testing
        }

        return super.onOptionsItemSelected(item);
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

    public void openCreatePatientActivity(View view)
    {
        Intent intent = new Intent(PatientListActivity.this, CreatePatientActivity.class);
        startActivity(intent);
    }
}