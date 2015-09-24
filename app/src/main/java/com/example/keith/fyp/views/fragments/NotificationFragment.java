package com.example.keith.fyp.views.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
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
import com.example.keith.fyp.interfaces.NotificationCommunicator;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.views.activities.CreatePatientFormActivity;

public class NotificationFragment extends Fragment implements NotificationCommunicator {

    private View rootView;

    private NotificationListFragment notificationListFragment;
    private NotificationDetailFragment notificationDetailFragment;

    private FragmentManager fragmentManager;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_notification);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        notificationListFragment = new NotificationListFragment();
        notificationListFragment.setCommunicator(this);
        notificationDetailFragment = new NotificationDetailFragment();

        transaction.add(R.id.notification_list_fragment_container, notificationListFragment);
        transaction.add(R.id.notification_detail_fragment_container, notificationDetailFragment);
        transaction.commit();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void respond(Notification notification) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Display the notification detail
            notificationDetailFragment.renderDetail(notification);
        } else {
            // In portrait orientation

//            Intent intent = new Intent(this, CreatePatientFormActivity.class);
//            intent.putExtra("selectedCategory", index);
//            startActivity(intent);
        }
    }
}
