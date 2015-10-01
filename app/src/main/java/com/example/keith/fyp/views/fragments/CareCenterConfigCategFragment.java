package com.example.keith.fyp.views.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;


public class CareCenterConfigCategFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;
    private ListView careCenterConfigCategListView;

    private Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_care_center_config_categ, container, false);

        careCenterConfigCategListView = (ListView) rootView.findViewById(R.id.care_center_config_categ_list_view);

        String[] infoCategList = getResources().getStringArray(R.array.care_center_config_category);

        ArrayAdapter<String> infoCategAdapter = new ArrayAdapter<>(getActivity(), R.layout.text_list_item_layout, infoCategList);
        careCenterConfigCategListView.setAdapter(infoCategAdapter);
        careCenterConfigCategListView.setOnItemClickListener(this);

        // Select the first item of the listView (only for landscape mode)
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewTreeObserver observer = careCenterConfigCategListView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    careCenterConfigCategListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    careCenterConfigCategListView.performItemClick(
                            careCenterConfigCategListView.getAdapter().getView(0, null, null),
                            0,
                            careCenterConfigCategListView.getAdapter().getItemId(0));
                    careCenterConfigCategListView.setItemChecked(0, true);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(communicator != null) {
            communicator.respond(position);
        }
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }
}
