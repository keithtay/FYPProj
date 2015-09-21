package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.views.activities.CreatePatientActivity;

public class PatientInfoCategListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView infoCategListView;
    private Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_info_categ_list, container, false);

        infoCategListView = (ListView) view.findViewById(R.id.info_categ_list_view);

        String[] infoCategList;

        if(getActivity() instanceof CreatePatientActivity) {
            infoCategList = getResources().getStringArray(R.array.info_category_create_patient);
        } else {
            infoCategList = getResources().getStringArray(R.array.info_category_view_patient);
        }


        ArrayAdapter<String> infoCategAdapter = new ArrayAdapter<>(getActivity(), R.layout.info_categ_list_item, infoCategList);
        infoCategListView.setAdapter(infoCategAdapter);
        infoCategListView.setOnItemClickListener(this);

        // Select the first item of the listView (only for landscape mode)
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewTreeObserver observer = infoCategListView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    infoCategListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    infoCategListView.performItemClick(
                            infoCategListView.getAdapter().getView(0, null, null),
                            0,
                            infoCategListView.getAdapter().getItemId(0));
                    infoCategListView.setItemChecked(0,true);
                }
            });
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicator.respond(position);
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }
}
