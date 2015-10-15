package com.example.keith.fyp.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Base Activity to display the patient's schedule
 */
public class ScheduleActivity extends AppCompatActivity {

    protected ArrayList<Event> eventList;
    protected LinearLayout eventListContainer;

    protected int spacingBetweenEventView;
    protected DateTime currentTime;

    protected String selectedPatientNric;
    protected Patient viewedPatient;

    private ImageView photoImageView;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView genderTextView;
    private TextView dobTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        for(Patient patient : DataHolder.getPatientList(this)) {
            if(patient.getNric().equals(selectedPatientNric)) {
                DataHolder.setViewedPatient(patient);
                viewedPatient = patient;
                break;
            }
        }

        eventList = viewedPatient.getTodaySchedule().getEventList();

        spacingBetweenEventView = (int) getResources().getDimension(R.dimen.paper_card_padding) / 2;

        // Initialize current time (for display purpose) TODO: change with actual current time
        currentTime = DateTime.now().withTime(13,30,0,0);
    }

    private void setScheduleDate() {
        TextView todayDate = (TextView) findViewById(R.id.today_date);
        DateTime currentDate = DateTime.now();
        todayDate.setText(currentDate.toString(Global.DATE_FORMAT));
    }

    /**
     * Initilization by set the schedule date and set the patient brief information
     */
    protected void init() {
        setScheduleDate();
        setPatientBriefInfo();
    }

    private void setPatientBriefInfo() {
        photoImageView = (ImageView) findViewById(R.id.photo_image_view);
        firstNameTextView = (TextView) findViewById(R.id.first_name_text_view);
        lastNameTextView = (TextView) findViewById(R.id.last_name_text_view);
        genderTextView = (TextView) findViewById(R.id.gender_text_view);
        dobTextView = (TextView) findViewById(R.id.dob_text_view);

        photoImageView.setImageBitmap(viewedPatient.getPhoto());
        firstNameTextView.setText(viewedPatient.getFirstName());
        lastNameTextView.setText(viewedPatient.getLastName());
        String gender = "Male";
        if(viewedPatient.getGender() == 'F') {
            gender = "Female";
        }
        genderTextView.setText(gender);
        dobTextView.setText(viewedPatient.getDob().toString(Global.DATE_FORMAT));
    }
}
