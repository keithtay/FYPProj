package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.views.activities.CreatePatientFormActivity;

public class NotificationFragment extends Fragment implements Communicator {

    private View rootView;

    private NotificationListFragment notificationListFragment;
    private NotificationDetailFragment notificationDetailFragment;

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_notification);

        fragmentManager = getFragmentManager();
        notificationListFragment = (NotificationListFragment) fragmentManager.findFragmentById(R.id.notification_list_fragment);
        notificationListFragment.setCommunicator(this);

        notificationDetailFragment = (NotificationDetailFragment) fragmentManager.findFragmentById(R.id.notification_detail_fragment);

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

    @Override
    public void respond(int index) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Display the notification detail
            notificationDetailFragment.renderDetail();
        } else {
            // In portrait orientation

//            Intent intent = new Intent(this, CreatePatientFormActivity.class);
//            intent.putExtra("selectedCategory", index);
//            startActivity(intent);
        }
    }
}
