package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.CreateNewPatientReceiver;
import com.example.keith.fyp.broadcastreceiver.NotificationUpdateReceiver;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.interfaces.CreatePatientCommunicator;
import com.example.keith.fyp.interfaces.OnCreateNewPatientListener;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.activities.CreatePatientActivity;

import java.util.ArrayList;

public class PatientInfoCategListFragment extends Fragment implements AdapterView.OnItemClickListener, OnCreateNewPatientListener {

    private ListView infoCategListView;
    private CreatePatientCommunicator communicator;
    private CreateNewPatientReceiver createNewPatientReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_info_categ_list, container, false);

        infoCategListView = (ListView) view.findViewById(R.id.info_categ_list_view);

        createNewPatientReceiver = new CreateNewPatientReceiver(this);

        String[] infoCategList;

        if(getActivity() instanceof CreatePatientActivity) {
            infoCategList = getResources().getStringArray(R.array.info_category_create_patient);
        } else {
            infoCategList = getResources().getStringArray(R.array.info_category_view_patient);
        }


        ArrayAdapter<String> infoCategAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_list_item_layout, infoCategList);
        infoCategListView.setAdapter(infoCategAdapter);
        infoCategListView.setOnItemClickListener(this);

        // Select the first item of the listView (only for landscape mode)
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewTreeObserver observer = infoCategListView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    infoCategListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    performClickOnItemWithPosition(0);
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != createNewPatientReceiver) {
            getActivity().registerReceiver(createNewPatientReceiver, new IntentFilter(Global.ACTION_CREATE_NEW_PATIENT));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicator.respond(position);
    }

    public void setCommunicator(CreatePatientCommunicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void onCreateNewPatient() {
        Patient createdPatient = DataHolder.getCreatedPatient();

        ArrayList<Integer> emptyFieldIdList = new ArrayList<>();

        if (UtilsString.isEmpty(createdPatient.getFirstName())) {
            emptyFieldIdList.add(Global.FIRST_NAME_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getLastName())) {
            emptyFieldIdList.add(Global.LAST_NAME_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getNric())) {
            emptyFieldIdList.add(Global.NRIC_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getAddress())) {
            emptyFieldIdList.add(Global.ADDRESS_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getHomeNumber())) {
            emptyFieldIdList.add(Global.HOME_NUMBER_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getPhoneNumber())) {
            emptyFieldIdList.add(Global.PHONE_NUMBER_FIELD);
        }

        if (createdPatient.getGender() == 0) {
            emptyFieldIdList.add(Global.GENDER_FIELD);
        }

        if (createdPatient.getDob() == null) {
            emptyFieldIdList.add(Global.DOB_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getGuardianFullName())) {
            emptyFieldIdList.add(Global.GUARDIAN_NAME_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getGuardianContactNumber())) {
            emptyFieldIdList.add(Global.GUARDIAN_CONTACT_NUMBER_FIELD);
        }

        if (UtilsString.isEmpty(createdPatient.getGuardianEmail())) {
            emptyFieldIdList.add(Global.GUARDIAN_EMAIL_FIELD);
        }

        if (createdPatient.getPhoto() == null) {
            emptyFieldIdList.add(Global.PHOTO);
        }

        if (emptyFieldIdList.size() > 0) {
            infoCategListView.setItemChecked(0,true);
            communicator.respond(0, emptyFieldIdList);
        } else {
            // TODO: check if allergy has been filled
        }
    }

    private void performClickOnItemWithPosition(int position) {
        infoCategListView.performItemClick(
                infoCategListView.getAdapter().getView(position, null, null),
                0,
                infoCategListView.getAdapter().getItemId(position));
        infoCategListView.setItemChecked(position,true);
    }
}
