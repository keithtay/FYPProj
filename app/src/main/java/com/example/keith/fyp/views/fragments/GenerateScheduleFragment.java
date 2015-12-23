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
    private RadioButton sound, vibration;
    private Button button;
    private TextView userName, userPw;
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

        sound = (RadioButton) rootView.findViewById(R.id.radioDay);
        vibration = (RadioButton) rootView.findViewById(R.id.radioWeek);

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
                    dbfile db = new dbfile();
                    ArrayList<Integer> al = new ArrayList<Integer>();
                    al = db.checkUserValid(UserID,userName.getText().toString(),userPw.getText().toString());
                    if(al.size() != 0){
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == sound.getId()) {
                            ArrayList<Patient> patient = new ArrayList<Patient>();
                            ArrayList<DefaultEvent> de = new ArrayList<DefaultEvent>();
                            patient = DataHolder.getPatientList(getActivity());
                            de = DataHolder.getDefaultEventList();
                            Collections.sort(de, DefaultEvent.COMPARE_BY_TIME);
                            scheduleScheduler ss = new scheduleScheduler();
                            Toast.makeText(getActivity(), "Please wait while the scheduler process", Toast.LENGTH_LONG).show();
                            ss.insertNewSchedules(patient, de);
                            Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_LONG).show();
                        }else{

                        }
                    }else{
                        Toast.makeText(getActivity(), "Sorry, wrong credentials entered",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }



}
