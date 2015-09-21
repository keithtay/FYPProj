package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.R;

public class NotificationFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_notification);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment notifListFragment = (getFragmentManager().findFragmentById(R.id.notification_list_fragment));
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.remove(notifListFragment);

        Fragment notifDetailFragment = (getFragmentManager().findFragmentById(R.id.notification_detail_fragment));
        ft.remove(notifDetailFragment);
        ft.commit();
    }
}
