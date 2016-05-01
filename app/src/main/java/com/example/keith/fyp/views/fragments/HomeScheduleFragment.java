package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.FilterList;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.activities.CreatePatientActivity;
import com.example.keith.fyp.views.customviews.ScheduleRecycleView;
import com.example.keith.fyp.views.adapters.HomeScheduleAdapter;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;

import java.sql.Connection;
//import java.sql.Date;
//import java.sql.Date;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Keith on 22/9/2015.
 */
public class HomeScheduleFragment extends Fragment {
    private View rootView;
    private ScheduleRecycleView scheduleRecyclerView;
    private HomeScheduleAdapter scheduleAdapter;
    private FloatingActionButton createPatientFab;
//    java.sql.Date date,date1,date2;
    String nextActivityTime = "-No Time-";
    String currentActivity = "-No Activity-";
    String nextActivity = "-No Activity-";
    private SpinAdapter adapter;
    Handler handler = new Handler();
    Runnable refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_schedule, container, false);

        // To be able to change the menu in action bar
        setHasOptionsMenu(true);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Patient's Current Activity");

        refresh = new Runnable() {
            public void run() {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm ");
                TextView t1 = (TextView) rootView.findViewById(R.id.currentTimeDisplay);
                String strDate = sdf.format(c.getTime());
                t1.setText("Current Time: " + strDate);
                handler.postDelayed(refresh, 10000);
            }
        };
        handler.post(refresh);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm ");
        TextView t1 = (TextView) rootView.findViewById(R.id.currentTimeDisplay);
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

        createPatientFab = (FloatingActionButton) rootView.findViewById(R.id.create_patient_fab);
        createPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForAvailablePatientDrafts();
            }
        });

        return rootView;
    }

    private void checkForAvailablePatientDrafts() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final String json = mPrefs.getString(Global.SP_CREATE_PATIENT_DRAFT, "");
        if(!UtilsString.isEmpty(json)) {
            final Gson gson = new Gson();
            HashMap draftMap = gson.fromJson(json, HashMap.class);

            String[] draftIdArray = (String[]) draftMap.keySet().toArray(new String[draftMap.size()]);
            ArrayList<String> draftIdList = new ArrayList<>(Arrays.asList(draftIdArray));
            draftIdList.add("Create blank patient");
            final String[] draftIdWithBlankArray = draftIdList.toArray(new String[draftIdList.size()]);

            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
            builder.title("Select a draft");
            builder.items(draftIdWithBlankArray);
            builder.itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    String selectedDraftId = draftIdWithBlankArray[which];
                    HashMap draftMap = gson.fromJson(json, HashMap.class);
                    if (draftMap.containsKey(selectedDraftId)) {
                        Bundle extras = new Bundle();
                        extras.putString(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID, selectedDraftId);
                        openCreatePatientActivity(extras);
                    } else {
                        openCreatePatientActivity(null);
                    }
                }
            });
            builder.show();
        } else {
            openCreatePatientActivity(null);
        }
    }

    private void openCreatePatientActivity(Bundle extras) {
        Intent intent = new Intent(getActivity(), CreatePatientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if(extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
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
        //need to store locally into pref for the next day schedule
        //have to ensure the schedule for today is stored too and filtered by date time
        SharedPreferences preferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String caregiverI = preferences.getString("userid", "");
        Log.v("UserTypeID", preferences.getString("userTypeId", ""));
        int caregiverId = Integer.parseInt(caregiverI);

        Calendar cal = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        String dateNow = timestamp.toString().substring(0,10);
        String timeNow = timestamp.toString().substring(10,19);

//        DateFormat formatter = new SimpleDateFormat("HH:mm");
//        try {
//            date = (Date)formatter.parse(timeNow);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        dbfile db = new dbfile();
        ArrayList<Schedule> patientScheduleList = new ArrayList<>();
        patientScheduleList = db.getPatientSchedule(caregiverId, dateNow, getActivity().getApplicationContext());
        List<Schedule> scheduleList = new ArrayList<>();
        if(patientScheduleList.size() != 0) {
            Collections.sort(patientScheduleList, Schedule.COMPARE_BY_SCHEDULE);
            Collections.sort(patientScheduleList, Schedule.COMPARE_BY_NAME);
            for (int i = 0; i < patientScheduleList.size(); i++) {
                Log.v("Printing:", patientScheduleList.get(i).getName() + patientScheduleList.get(i).getnActivityTime());
            }

            String holder = patientScheduleList.get(0).getNric();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            String date = timeNow;
            Log.v("Testing:", date);
            String d1 = date.substring(0, 3);
            String d2 = date.substring(4, 6);
            String d3 = d1 + "." + d2;
            float dateHour = Float.parseFloat(d3);


//        java.util.Date parsed = null;
//        try {
//            parsed = format.parse("17:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        java.sql.Date sql = new java.sql.Date(parsed.getTime());
//        Log.v("TIME IN DATE:", String.valueOf(sql.getTime()));
            boolean check = false;
            String nameCheck = "";
            for (int i = 0; i < patientScheduleList.size(); i++) {

                if(check == true && nameCheck.equals(patientScheduleList.get(i).getName())){
                    continue;
                }else{
                    check = false;
                }
                if(i+1 >= patientScheduleList.size()) {
                    holder = patientScheduleList.get(i).getNric();
                }else{
                  holder = patientScheduleList.get(i + 1).getNric();
                }

                String date2 = patientScheduleList.get(i).getnActivityTime();
                String e1 = date2.substring(0, 2);
                String e2 = date2.substring(3, 5);
                String e3 = e1 + "." + e2;
                float date2Hour = Float.parseFloat(e3);
                String date3 = patientScheduleList.get(i).getnActivity();
                String f1 = date3.substring(0, 2);
                String f2 = date3.substring(3, 5);
                String f3 = f1 + "." + f2;
                float date3Hour = Float.parseFloat(f3);
//                if (date.after(date1) && date.before(date2)) {
                //if currentTime falls within the timing interval
                //dateHOur is currentTime date2hour
                if(dateHour<date2Hour&&dateHour<date3Hour){
                    check =true;
                    nameCheck = patientScheduleList.get(i).getName();
                    nextActivityTime = patientScheduleList.get(i).getnActivityTime().substring(0, 5) + "-" + patientScheduleList.get(i).getnActivity().substring(0,5);
                    nextActivity = patientScheduleList.get(i).getcActivity();
                    Schedule schedule1 = new Schedule(patientScheduleList.get(i).getPhoto(), patientScheduleList.get(i).getName(), patientScheduleList.get(i).getNric()
                            , currentActivity, nextActivityTime, nextActivity);
                    scheduleList.add(schedule1);
                    nextActivityTime = "-No Time-";
                    currentActivity = "-No Activity-";
                    nextActivity = "-No Activity-";
                    continue;
                }else if (date2Hour <= dateHour && dateHour <= date3Hour) {
                    currentActivity = patientScheduleList.get(i).getcActivity();

                    //check if the next item in the list is the final node, if yes, get the current and stall and break loop
                    if ((i + 1) >= patientScheduleList.size()) {
                        Schedule schedule1 = new Schedule(patientScheduleList.get(i).getPhoto(), patientScheduleList.get(i).getName(), patientScheduleList.get(i).getNric()
                                , currentActivity, nextActivityTime, nextActivity);
                        scheduleList.add(schedule1);
                        break;
                    }
                    //check if the next tuple if it is stil the same user, if yes, assign the next activity
                    if (patientScheduleList.get(i + 1).getNric().equals(holder)) {
                        check=true;
                        nameCheck = patientScheduleList.get(i).getName();
                        nextActivityTime = patientScheduleList.get(i + 1).getnActivityTime().substring(0, 5) + "-" + patientScheduleList.get(i + 1).getnActivity().substring(0,5);
                        nextActivity = patientScheduleList.get(i + 1).getcActivity();
                        Schedule schedule1 = new Schedule(patientScheduleList.get(i).getPhoto(), patientScheduleList.get(i).getName(), patientScheduleList.get(i).getNric()
                                , currentActivity, nextActivityTime, nextActivity);
                        scheduleList.add(schedule1);
                        nextActivityTime = "-No Time-";
                        currentActivity = "-No Activity-";
                        nextActivity = "-No Activity-";
                        continue;
                    }else{
                        check=false;
                        nextActivityTime = "-No Time-";
                        nextActivity = "-No Activity-";
                        Schedule schedule1 = new Schedule(patientScheduleList.get(i).getPhoto(), patientScheduleList.get(i).getName(), patientScheduleList.get(i).getNric()
                                , currentActivity, nextActivityTime, nextActivity);
                        scheduleList.add(schedule1);
                        currentActivity = "-No Activity-";

                        continue;
                    }
                    //if time interval not within, check if the next tuple if it is a diff user or no more tuple
                } else if ((i + 1) >= patientScheduleList.size() || !patientScheduleList.get(i + 1).getNric().equals(holder)) {

                    String date4 = patientScheduleList.get(i).getnActivity();
                    String g1 = date4.substring(0, 2);
                    String g2 = date4.substring(3, 5);
                    String g3 = g1 + "." + g2;
                    float date4Hour = Float.parseFloat(g3);
                    if (date4Hour > dateHour) {
                        Schedule schedule1 = new Schedule(patientScheduleList.get(i).getPhoto(), patientScheduleList.get(i).getName(), patientScheduleList.get(i).getNric()
                                , currentActivity, patientScheduleList.get(i).getnActivityTime().substring(0,5) + "-" + patientScheduleList.get(i).getnActivity().substring(0,5), patientScheduleList.get(i).getcActivity());
                        scheduleList.add(schedule1);
                    } else {
                        Schedule schedule1 = new Schedule(patientScheduleList.get(i).getPhoto(), patientScheduleList.get(i).getName(), patientScheduleList.get(i).getNric()
                                , currentActivity, nextActivityTime, nextActivity);
                        scheduleList.add(schedule1);
                    }
                }
                if ((i + 1) >= patientScheduleList.size()) {
                    break;
                }
                if (!patientScheduleList.get(i + 1).getNric().equals(holder)) {
                    holder = patientScheduleList.get(i + 1).getNric();
                    nextActivityTime = "-No Time-";
                    currentActivity = "-No Activity-";
                    nextActivity = "-No Activity-";
                }

            }
            return scheduleList;
        }
//
////        Schedule schedule1 = new Schedule(photoid[i], pname[i], pid[i], cActivity[i],nActivityTime[i], nActivity[i]);
////        scheduleList.add(schedule1);
////        Log.v("Testing", patientScheduleList.get(0).getName() + patientScheduleList.get(0).getcActivity());


//        int[] photoid = {R.drawable.avatar_01,
//                R.drawable.avatar_02,
//                R.drawable.avatar_03,
//                R.drawable.avatar_04,
//                R.drawable.avatar_05,
//                R.drawable.avatar_06,
//                R.drawable.avatar_07,
//                R.drawable.avatar_08,
//                R.drawable.avatar_09,
//                R.drawable.avatar_10,
//                };
//
//        String[] pname = {"Andy",
//                "Bob",
//                "Charlie",
//                "David",
//                "Eve",
//                "Florence",
//                "Gordon",
//                "Hilda",
//                "Ivan",
//                "Justin",
//                };
//
//        String[] pid = {"123456",
//                "654321",
//                "984203",
//                "562745",
//                "234745",
//                "234643",
//                "234534",
//                "234643",
//                "345634",
//                "743164",
//                };
//
//        String[] cActivity = {"Sleep",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Sleep",
//                "Bath",
//                "Bath",
//                "Talk",
//                "Eat",
//                "Game",
//        };
//
//        String[] nActivityTime = {"16:30hrs- 17:00hrs",
//                "16:30hrs- 17:00hrs",
//                "16:35hrs- 17:00hrs",
//                "16:40hrs- 17:00hrs",
//                "16:45hrs- 17:00hrs",
//                "16:50hrs- 17:00hrs",
//                "16:55hrs- 17:00hrs",
//                "17:00hrs- 17:00hrs",
//                "17:30hrs- 18:00hrs",
//                "17:30hrs- 18:00hrs",
//        };
//        String[] nActivity = {"Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//                "Eat",
//        };
//        for (int i = 0; i < photoid.length; i++) {
//            Schedule schedule1 = new Schedule(photoid[i], pname[i], pid[i], cActivity[i],nActivityTime[i], nActivity[i]);
//            scheduleList.add(schedule1);
//        }

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
