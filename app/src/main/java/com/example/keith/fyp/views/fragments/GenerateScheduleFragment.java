package com.example.keith.fyp.views.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.models.ScheduleList;
import com.example.keith.fyp.scheduler.scheduleScheduler;
import com.example.keith.fyp.utils.DataHolder;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Keith on 21/12/2015.
 */
public class GenerateScheduleFragment extends Fragment {
    private View rootView;
    private RadioGroup radioGroup;
    private RadioButton day, week, tomorrow;
    private Button button;
    private TextView userName, userPw, userNRIC;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.generateschedule, container, false);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.myRadioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioDay) {
                    Toast.makeText(getActivity(), "Choice: Generate Day",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.radioWeek) {
                    Toast.makeText(getActivity(), "Choice: Generate Week",
                            Toast.LENGTH_SHORT).show();
            }

        }});

        day = (RadioButton) rootView.findViewById(R.id.radioDay);
        tomorrow = (RadioButton) rootView.findViewById(R.id.radioTomorrow);
        week = (RadioButton) rootView.findViewById(R.id.radioWeek);
        userNRIC = (TextView) rootView.findViewById(R.id.patientNRICtext);
        userName = (TextView) rootView.findViewById(R.id.userName);
        userPw = (TextView) rootView.findViewById(R.id.userPw);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));

        button = (Button)rootView.findViewById(R.id.generateButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                // find which radioButton is checked by id
//                if (selectedId == sound.getId()) {
//                    Toast.makeText(getActivity(), "Choice: Generate Day",
//                            Toast.LENGTH_SHORT).show();
//                } else if (selectedId == vibration.getId()) {
//                    Toast.makeText(getActivity(), "Choice: Generate Week",
//                            Toast.LENGTH_SHORT).show();
//                }
                if (UserTypeID != 3){
                    Toast.makeText(getActivity(), "Sorry, you do not have the privilege to access this function",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if(userNRIC.getText().toString().equals(null) || userNRIC.getText().toString().equals("")||userNRIC.getText().toString().equals(" ")) {
                        dbfile db = new dbfile();
                        ArrayList<Integer> al = new ArrayList<Integer>();
                        al = db.checkUserValid(UserID, userName.getText().toString(), userPw.getText().toString());
                        if (al.size() != 0) {
                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            DateTime date1;
                            ArrayList<Patient> patient = new ArrayList<Patient>();
                            ArrayList<DefaultEvent> de = new ArrayList<DefaultEvent>();
                            patient = DataHolder.getPatientList(getActivity());
                            de = DataHolder.getDefaultEventList();
                            Collections.sort(de, DefaultEvent.COMPARE_BY_TIME);
                            scheduleScheduler ss = new scheduleScheduler();
                            if (selectedId == day.getId()) {
                                Toast.makeText(getActivity(), "Please wait while the scheduler process", Toast.LENGTH_LONG).show();
                                date1 = DateTime.now();
                                ss.insertNewSchedules(patient, de, date1);
                                Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_LONG).show();
                            } else if (selectedId == tomorrow.getId()) {
                                Toast.makeText(getActivity(), "Please wait while the scheduler process", Toast.LENGTH_LONG).show();
                                date1 = DateTime.now().plusDays(1);
                                ss.insertNewSchedules(patient, de, date1);
                                Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_LONG).show();
                            } else if (selectedId == week.getId()) {

                            }
                        } else {
                            Toast.makeText(getActivity(), "Sorry, wrong credentials entered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        ArrayList<Patient> patient = new ArrayList<Patient>();
                        patient = DataHolder.getPatientList(getActivity());
                        Boolean check =false;
                        int k = 0;
                        for(int i =0; i<patient.size();i++){
                            if(userNRIC.getText().toString().equals(patient.get(i).getNric())){
                                check = true;
                                k = i;
                                break;
                            }
                        }
                        if (check == false){
                            Toast.makeText(getActivity(), "No such NRIC detected", Toast.LENGTH_LONG).show();
                        }else{
                            dbfile db = new dbfile();
                            ArrayList<Integer> al = new ArrayList<Integer>();
                            al = db.checkUserValid(UserID, userName.getText().toString(), userPw.getText().toString());
                            if (al.size() != 0) {
                                int selectedId = radioGroup.getCheckedRadioButtonId();
                                DateTime date1;
                                ArrayList<DefaultEvent> de = new ArrayList<DefaultEvent>();
                                patient = DataHolder.getPatientList(getActivity());
                                de = DataHolder.getDefaultEventList();
                                Collections.sort(de, DefaultEvent.COMPARE_BY_TIME);
                                scheduleScheduler ss = new scheduleScheduler();
                                if (selectedId == day.getId()) {
                                    Toast.makeText(getActivity(), "Please wait while the scheduler process", Toast.LENGTH_LONG).show();
                                    date1 = DateTime.now();
                                    ss.insertNewSpecificPatient(patient, de, date1, k);
                                    Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_LONG).show();
                                } else if (selectedId == tomorrow.getId()) {
                                    Toast.makeText(getActivity(), "Please wait while the scheduler process", Toast.LENGTH_LONG).show();
                                    date1 = DateTime.now().plusDays(1);
                                    ss.insertNewSpecificPatient(patient, de, date1,k);
                                    Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_LONG).show();
                                } else if (selectedId == week.getId()) {

                                }
                            } else {
                                Toast.makeText(getActivity(), "Sorry, wrong credentials entered",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        return rootView;
    }



}
