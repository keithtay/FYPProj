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
import com.example.keith.fyp.models.TextTooltipPair;
import com.example.keith.fyp.views.adapters.TextTooltipPairListAdapter;

import java.util.ArrayList;

/**
 * Fragment to display the list of care centre configuration category list
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
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

        ArrayList<TextTooltipPair> textTooltipPairList = new ArrayList<>();

        for(String entry:infoCategList) {
            String[] splitResult = entry.split("\\|", 2);
            textTooltipPairList.add(new TextTooltipPair(splitResult[0], splitResult[1]));
        }

        TextTooltipPairListAdapter listAdapter = new TextTooltipPairListAdapter(getActivity(), textTooltipPairList);
        careCenterConfigCategListView.setAdapter(listAdapter);
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

    /**
     * @param communicator communicator to pass data to the other fragment
     */
    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }
}
