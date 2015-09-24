package com.example.keith.fyp.renderers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.Global;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ActionRenderer extends Renderer {

    private Notification notification;

    public ActionRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        ViewGroup rootView = null;

        switch (notification.getStatus()) {
            case Notification.STATUS_NONE:
                rootView = (ViewGroup) inflater.inflate(R.layout.notification_detail_action_accept_reject_layout, null);

                final ViewGroup finalRootView = rootView;
                Button rejectButton = (Button) rootView.findViewById(R.id.action_reject_button);
                rejectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notification.setStatus(Notification.STATUS_REJECTED);
                        finalRootView.removeAllViews();
                        View acceptedView = inflater.inflate(R.layout.notification_detail_action_rejected_layout, null);
                        finalRootView.addView(acceptedView);

                        Intent intent = new Intent(Global.ACTION_NOTIFICATION_UPDATE);
                        inflater.getContext().sendBroadcast(intent);
                    }
                });

                Button acceptButton = (Button) rootView.findViewById(R.id.action_accept_button);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notification.setStatus(Notification.STATUS_ACCEPTED);
                        finalRootView.removeAllViews();
                        View acceptedView = inflater.inflate(R.layout.notification_detail_action_accepted_layout, null);
                        finalRootView.addView(acceptedView);

                        Intent intent = new Intent(Global.ACTION_NOTIFICATION_UPDATE);
                        inflater.getContext().sendBroadcast(intent);
                    }
                });
                break;
            case Notification.STATUS_ACCEPTED:
                rootView = (ViewGroup) inflater.inflate(R.layout.notification_detail_action_accepted_layout, null);
                break;
            case Notification.STATUS_REJECTED:
                rootView = (ViewGroup) inflater.inflate(R.layout.notification_detail_action_rejected_layout, null);
                break;
        }

        return rootView;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
