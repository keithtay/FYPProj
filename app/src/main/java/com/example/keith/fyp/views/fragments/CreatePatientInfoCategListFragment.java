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

public class CreatePatientInfoCategListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView infoCategListView;
    private Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_patient_info_categ_list, container, false);

        infoCategListView = (ListView) view.findViewById(R.id.infoCategListView);

        String[] infoCategList = {
                "Personal Information",
                "Allergy",
                "Vital",
                "Social History",
                "Prescription",
                "Routinity",};

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

    public interface Communicator {
        public void respond(int index);
    }
}
