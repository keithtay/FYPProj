package com.example.keith.fyp.renderers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.customviews.TextField;

/**
 * ActionRenderer is {@link Renderer} to render the action that can be done to a {@link Notification}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ActionRenderer extends Renderer {

    private Notification notification;
    private Context context;

    /**
     * Create a action renderer with the specified values.
     */
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
                TextView reasonTextView = (TextView) rootView.findViewById(R.id.notification_rejection_reason_text_view);
                reasonTextView.setText(notification.getRejectionReason());
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
                SharedPreferences pref;
                pref = context.getSharedPreferences("Login", 0);
                final int UserID = Integer.parseInt(pref.getString("userid", ""));
                int logid = notification.getLogid();
                int rowid = notification.getRa();
                int patientid = notification.getPatientID();
                String columnAffected = notification.getAdditionalInfo();
                String tablename = notification.getTa();
                String logdata = notification.getLogData();
                String[] info = logdata.split(";");
                dbfile db = new dbfile();
                int getNotificationId = notification.getType();
                if (getNotificationId == 1 || getNotificationId == 2) {
                    db.updateNotificationTables(logid, rowid, tablename, UserID);
                } else if (getNotificationId == 3) {
                    //do something

                    if (columnAffected.equals("First Name")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "firstName", UserID);
                    } else if (columnAffected.equals("Last Name")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "lastName", UserID);
                    } else if (columnAffected.equals("NRIC")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "nric", UserID);
                    } else if (columnAffected.equals("Address")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "address", UserID);
                    } else if (columnAffected.equals("Home Number")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "officeNo", UserID);
                    } else if (columnAffected.equals("Phone Number")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "handphoneNo", UserID);
                    } else if (columnAffected.equals("Gender")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "gender", UserID);
                    } else if (columnAffected.equals("Date of Birth")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "DOB", UserID);
                    } else if (columnAffected.equals("Guardian Full Name")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "guardianName", UserID);
                    } else if (columnAffected.equals("Guardian Contact Number")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "guardianContactNo", UserID);
                    } else if (columnAffected.equals("Guardian Email")) {
                        db.updateLogPatientInfo(logid, patientid, info[1], "guardianEmail", UserID);
                    }
                } else if (getNotificationId == 4) {
                    String[] newData = logdata.split(">");
                    db.updateLogPatientSpecInfo(logid, rowid, newData[0], UserID);
                }else if(getNotificationId == 12){
                    db.deletePatientInfoTable(logid, rowid, UserID);
                }
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
                    SharedPreferences pref;
                    pref = context.getSharedPreferences("Login", 0);
                    final int UserID = Integer.parseInt(pref.getString("userid", ""));
                    int logid = notification.getLogid();
                    int rowid = notification.getRa();
                    String tablename = notification.getTa();
                    dbfile db = new dbfile();
                    String name = notification.getAffectedPatient().getFirstName() + " " + notification.getAffectedPatient().getLastName();
                    int getNotificationId = notification.getType();
                    if (getNotificationId == 1 || getNotificationId == 2){
                        db.updateRejectionNotificationTables(logid, rowid, tablename, UserID, reasonStr, name);
                    }else if(getNotificationId ==3){
                        db.updateRejectionNewPatientInfo(logid,UserID,reasonStr, name);
                    }else if(getNotificationId ==4){
                        db.updateRejectionUpdatePatientInfo(logid,UserID,reasonStr, name);
                    }else if(getNotificationId == 12){
                        db.updateDeletePatientInfo(logid,UserID,reasonStr, name);
                    }
                    notification.setStatus(Notification.STATUS_REJECTED);
                    notification.setRejectionReason(reasonStr);
                    finalRootView.removeAllViews();
                    View rejectedView = inflater.inflate(R.layout.notification_detail_action_rejected_layout, null);
                    TextView reasonTextView = (TextView) rejectedView.findViewById(R.id.notification_rejection_reason_text_view);
                    reasonTextView.setText(reasonStr);
                    finalRootView.addView(rejectedView);

                    Intent intent = new Intent(Global.ACTION_NOTIFICATION_UPDATE);
                    inflater.getContext().sendBroadcast(intent);

                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * @param notification notification to be rendered
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
