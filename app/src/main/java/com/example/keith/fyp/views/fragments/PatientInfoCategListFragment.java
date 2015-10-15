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
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.broadcastreceiver.CreateNewPatientReceiver;
import com.example.keith.fyp.interfaces.CreatePatientCommunicator;
import com.example.keith.fyp.interfaces.OnCreateNewPatientListener;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.TextTooltipPair;
import com.example.keith.fyp.utils.CreatedPatientEmptyFieldChecker;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.activities.CreatePatientActivity;
import com.example.keith.fyp.views.adapters.TextTooltipPairListAdapter;

import java.util.ArrayList;

/**
 * Fragment to display the list of patient's information categories
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class PatientInfoCategListFragment extends Fragment implements AdapterView.OnItemClickListener, OnCreateNewPatientListener {

    private ListView infoCategListView;
    private CreatePatientCommunicator communicator;
    private CreateNewPatientReceiver createNewPatientReceiver;

    private Integer openTabIndex;

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

        ArrayList<TextTooltipPair> textTooltipPairList = new ArrayList<>();

        for(String entry:infoCategList) {
            String[] splitResult = entry.split("\\|", 2);
            textTooltipPairList.add(new TextTooltipPair(splitResult[0], splitResult[1]));
        }

        TextTooltipPairListAdapter listAdapter = new TextTooltipPairListAdapter(getActivity(), textTooltipPairList);
        infoCategListView.setAdapter(listAdapter);
        infoCategListView.setOnItemClickListener(this);

        openTabIndex = null;

        // click the first category in landscape mode
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            openTabIndex = 0;
        }

        if(openTabIndex != null) {
            ViewTreeObserver observer = infoCategListView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    infoCategListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    performClickOnItemWithPosition(openTabIndex);
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

    /**
     * @param communicator communicator to send data to the other fragment
     */
    public void setCommunicator(CreatePatientCommunicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void onCreateNewPatient() {
        Patient createdPatient = DataHolder.getCreatedPatient();

        ArrayList<Integer> emptyFieldIdList = CreatedPatientEmptyFieldChecker.checkPersonalInfo();

        if (emptyFieldIdList.size() > 0) {
            infoCategListView.setItemChecked(0,true);
            communicator.respond(0, emptyFieldIdList);
        } else {
            emptyFieldIdList = CreatedPatientEmptyFieldChecker.checkAllergy();
            if(emptyFieldIdList.size() > 0) {
                infoCategListView.setItemChecked(1,true);
                communicator.respond(1, emptyFieldIdList);
            }
        }
    }

    private void performClickOnItemWithPosition(int position) {
        infoCategListView.performItemClick(
                infoCategListView.getAdapter().getView(position, null, null),
                position,
                infoCategListView.getAdapter().getItemId(position));
        infoCategListView.setItemChecked(position,true);
    }
}
