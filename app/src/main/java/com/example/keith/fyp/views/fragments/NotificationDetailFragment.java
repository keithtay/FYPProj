package com.example.keith.fyp.views.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.NotificationToRendererConverter;

public class NotificationDetailFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification_detail, container, false);
        return rootView;
    }

    public void renderDetail(int position) {
        removeAllViewsViewInLayout();

        Notification notification = DataHolder.getNotificationList().get(position);
        NotificationRenderer renderer = NotificationToRendererConverter.convert(getActivity(), notification);
        View view = renderer.render();
        ((ViewGroup) rootView).addView(view);
    }

    private void removeAllViewsViewInLayout() {
        ((ViewGroup) rootView).removeAllViewsInLayout();
    }
}
