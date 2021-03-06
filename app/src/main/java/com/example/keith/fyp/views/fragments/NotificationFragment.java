package com.example.keith.fyp.views.fragments;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.views.activities.NotificationDetailActivity;
import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.Communicator;

/**
 * Fragment to display the notification
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class NotificationFragment extends Fragment implements Communicator {

    private View rootView;

    private NotificationListFragment notificationListFragment;
    private NotificationDetailFragment notificationDetailFragment;

    private FragmentManager fragmentManager;

    public static final int REQUEST_OPEN_NOTIFICATION_DETAIL = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_notification);


        notificationListFragment = (NotificationListFragment) getChildFragmentManager().findFragmentById(R.id.notification_list_fragment_container);

        if(notificationListFragment == null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            notificationListFragment = new NotificationListFragment();
            transaction.add(R.id.notification_list_fragment_container, notificationListFragment);
            transaction.commit();
        }

        if(rootView.findViewById(R.id.notification_detail_fragment_container) != null) {
            notificationDetailFragment = (NotificationDetailFragment) getChildFragmentManager().findFragmentById(R.id.notification_detail_fragment_container);

            if(notificationDetailFragment == null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                notificationDetailFragment = new NotificationDetailFragment();
                transaction.add(R.id.notification_detail_fragment_container, notificationDetailFragment);
                transaction.commit();
            }
        }

        notificationListFragment.setCommunicator(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void respond(int position) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Display the notification detail
            notificationDetailFragment.renderDetail(position);
        } else {
            // In portrait orientation

            Intent intent = new Intent(getActivity(), NotificationDetailActivity.class);
            intent.putExtra("selectedIndex", position);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, REQUEST_OPEN_NOTIFICATION_DETAIL);
        }
    }
}
