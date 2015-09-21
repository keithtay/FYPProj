package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.keith.fyp.R;

public class NotificationDetailFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_detail, container, false);
        return rootView;
    }

    public void renderDetail() {
        View view = new View(getActivity());
        view.setLayoutParams(new LinearLayout.LayoutParams(300, 300));

        double rand = Math.random() * 8;
        int randInt = (int) Math.floor(rand);

        int color = R.color.black_57;

        switch(randInt) {
            case 0:
                color = R.color.red_500;
                break;
            case 1:
                color = R.color.orange_500;
                break;
            case 2:
                color = R.color.yellow_500;
                break;
            case 3:
                color = R.color.green_500;
                break;
            case 4:
                color = R.color.blue_500;
                break;
            case 5:
                color = R.color.purple_500;
                break;
            case 6:
                color = R.color.pink_500;
                break;
            case 7:
                color = R.color.brown_500;
                break;
        }

        view.setBackgroundColor(getResources().getColor(color));

        ((ViewGroup) rootView).addView(view);
    }
}
