package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.models.SocialHistory;
import com.example.keith.fyp.models.Vital;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.ViewPatientFormFragmentDecoder;
import com.example.keith.fyp.views.fragments.PatientInfoCategListFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ViewPatientActivity extends AppCompatActivity  implements PatientInfoCategListFragment.Communicator {

    private PatientInfoCategListFragment infoCategListFragment;
    private FragmentManager fragmentManager;
    private InputMethodManager inputManager;
    private Patient viewedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getPatientInfoDetails();

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        fragmentManager = getFragmentManager();
        infoCategListFragment = (PatientInfoCategListFragment) fragmentManager.findFragmentById(R.id.patient_info_categ_list_fragment);
        infoCategListFragment.setCommunicator(this);
    }

    private void getPatientInfoDetails() {
        viewedPatient = DataHolder.getViewedPatient();

        // TODO: replace using backend API to retrieve patient info details

        // Patient's personal info
        viewedPatient.setFirstName("Andy");
        viewedPatient.setLastName("Grammer");
        viewedPatient.setNric("G1162834J");
        viewedPatient.setAddress("32 Nanyang Lake, Hall of Residence 19 #64-3-1221");
        viewedPatient.setHomeNumber("+65 8900 9800");
        viewedPatient.setPhoneNumber("+65 1234 5678");
        viewedPatient.setGender('M');
        viewedPatient.setDob(DateTime.now().withYear(1955).withMonthOfYear(3).withDayOfMonth(23));
        viewedPatient.setGuardianFullName("Bob Grammer");
        viewedPatient.setGuardianContactNumber("+65 5555 3333");
        viewedPatient.setGuardianEmail("bobgrammer@gmail.com");

        Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01);
        viewedPatient.setPhoto(photo);

        // Patient's allergy list
        ArrayList<Allergy> allergyList = new ArrayList<>();

        Allergy allergy1 = new Allergy("Cheese", "Swolen lips", "Take 5mg Paracetamol");
        Allergy allergy2 = new Allergy("Peanut", "Itchy skin", "Wash the skin with cold cloth");

        allergyList.add(allergy1);
        allergyList.add(allergy2);

        viewedPatient.setAllergyList(allergyList);

        // Patient's vital list

        // Patient's prescription list

        // Patient's routine list

        // Patient's social history
        SocialHistory socialHistory = new SocialHistory();
        socialHistory.setLiveWith("Wife and 2 children");
        socialHistory.setDiet("Vegetarian");
        socialHistory.setReligion("Christian");
        socialHistory.setIsSexuallyActive(false);
        socialHistory.setIsSecondhandSmoker(false);
        socialHistory.setAlcoholUse("Seldom");
        socialHistory.setCaffeineUse("Regular");
        socialHistory.setTobaccoUse("Never");
        socialHistory.setDrugUse("Never");
        socialHistory.setPet("2 dogs");
        socialHistory.setOccupation("Entrepreneur");
        socialHistory.setLike("Sandwich, salad");
        socialHistory.setDislike("Soft drinks");
        socialHistory.setHolidayExperience("2002 - Thailand, 2010 - Balie");
        socialHistory.setEducation("1990 - Princeton University");
        socialHistory.setExercise("Tennis, swimming");
        viewedPatient.setSocialHistory(socialHistory);
    }

    @Override
    public void respond(int index) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Hide keyboard
            if(getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            // Change fragment
            Fragment fragmentToBeDisplayed = ViewPatientFormFragmentDecoder.getFragment(index);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.view_patient_info_form_fragment_container, fragmentToBeDisplayed);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, ViewPatientFormActivity.class);
            intent.putExtra("selectedCategory", index);
            startActivity(intent);
        }
    }
}
