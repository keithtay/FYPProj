package com.example.keith.fyp.renderers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.activities.ViewScheduleActivity;
import com.example.keith.fyp.views.customviews.DateField;
import com.example.keith.fyp.views.customviews.SpinnerField;
import com.example.keith.fyp.views.customviews.TextField;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ActionRenderer extends Renderer {

    private Notification notification;
    private Context context;

    public ActionRenderer(LayoutInflater inflater) {
        super(inflater);
        this.context = inflater.getContext();
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
                        openRejectDialog(notification, finalRootView);
                    }
                });

                Button acceptButton = (Button) rootView.findViewById(R.id.action_accept_button);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openAcceptDialog(notification, finalRootView);
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

    private void openAcceptDialog(final Notification notification, final ViewGroup finalRootView) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(R.string.dialog_title_accept_notification);

        builder.positiveText(R.string.dialog_button_accept);
        builder.negativeText(R.string.dialog_button_cancel);

        builder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);

                notification.setStatus(Notification.STATUS_ACCEPTED);
                finalRootView.removeAllViews();
                View acceptedView = inflater.inflate(R.layout.notification_detail_action_accepted_layout, null);
                finalRootView.addView(acceptedView);

                Intent intent = new Intent(Global.ACTION_NOTIFICATION_UPDATE);
                inflater.getContext().sendBroadcast(intent);
            }
        });

        builder.show();
    }

    private void openRejectDialog(final Notification notification, final ViewGroup finalRootView) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(R.string.dialog_title_reject_notification);

        View rootView = inflater.inflate(R.layout.dialog_reject_notification, null);
        final TextField reasonTextField = (TextField) rootView.findViewById(R.id.notification_reject_reason_text_field);
        builder.customView(rootView, false);

        builder.positiveText(R.string.dialog_button_reject);
        builder.negativeText(R.string.dialog_button_cancel);

        final MaterialDialog dialog = builder.show();
        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reasonStr = reasonTextField.getText();

                boolean isValidForm = true;
                if (UtilsString.isEmpty(reasonStr)) {
                    reasonTextField.setError(context.getResources().getString(R.string.error_msg_field_required));
                    isValidForm = false;
                }

                if (isValidForm) {
                    notification.setStatus(Notification.STATUS_REJECTED);
                    finalRootView.removeAllViews();
                    View rejectedView = inflater.inflate(R.layout.notification_detail_action_rejected_layout, null);
                    finalRootView.addView(rejectedView);

                    Intent intent = new Intent(Global.ACTION_NOTIFICATION_UPDATE);
                    inflater.getContext().sendBroadcast(intent);

                    dialog.dismiss();
                }
            }
        });
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
