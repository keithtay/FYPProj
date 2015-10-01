package com.example.keith.fyp.views.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.utils.CareCenterConfigFragmentDecoder;
import com.example.keith.fyp.views.activities.CareCenterConfigDetailActivity;


public class CareCenterConfigFragment extends Fragment implements Communicator {

    private View rootView;
    private CareCenterConfigCategFragment careCenterConfigCategFragment;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_care_center_config, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_care_center_config);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        careCenterConfigCategFragment = new CareCenterConfigCategFragment();
        careCenterConfigCategFragment.setCommunicator(this);

        transaction.add(R.id.care_center_config_categ_fragment_container, careCenterConfigCategFragment);
        transaction.commit();

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void respond(int index) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Change fragment
            Fragment fragmentToBeDisplayed = CareCenterConfigFragmentDecoder.getFragment(index);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.care_center_config_detail_fragment_container, fragmentToBeDisplayed);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            // In portrait orientation
            Intent intent = new Intent(getActivity(), CareCenterConfigDetailActivity.class);
            intent.putExtra("selectedCategory", index);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
