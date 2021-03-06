package com.example.keith.fyp.renderers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.scheduler.scheduleScheduler;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.customviews.TextField;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;

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
                String nric = notification.getAffectedPatient().getNric();
                String columnAffected = notification.getAdditionalInfo();
                String tablename = notification.getTa();
                String logdata = notification.getLogData();
                String[] info = logdata.split(";");
                dbfile db = new dbfile();
                int getNotificationId = notification.getType();
                if (getNotificationId == 1 || getNotificationId == 2) {
                    db.updateNotificationTables(logid, rowid, tablename, UserID);
                    if (tablename.equals("patient") || columnAffected.equals("prescription") || columnAffected.equals("routine")){
                        schedulerFunction(nric);
                    }
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
                    } else if (columnAffected.equals("Live With")) {
                        db.updateSocialHistory(logid,patientid,info[1], 0, UserID);
                    }else if (columnAffected.equals("Diet")) {
                        db.updateSocialHistory(logid,patientid,info[1], 1, UserID);
                    }else if (columnAffected.equals("Religion")) {
                        db.updateSocialHistory(logid,patientid,info[1], 2, UserID);
                    }else if (columnAffected.equals("Sexually Active")) {
                        db.updateSocialHistory(logid,patientid,info[1], 3, UserID);
                    }else if (columnAffected.equals("Secondhand Smoker")) {
                        db.updateSocialHistory(logid,patientid,info[1], 4, UserID);
                    }else if (columnAffected.equals("Alcohol Use")) {
                        db.updateSocialHistory(logid,patientid,info[1], 5, UserID);
                    }else if (columnAffected.equals("Caffeine Use")) {
                        db.updateSocialHistory(logid,patientid,info[1], 6, UserID);
                    }else if (columnAffected.equals("Tobacco Use")) {
                        db.updateSocialHistory(logid,patientid,info[1], 7, UserID);
                    }else if (columnAffected.equals("Drug Use")) {
                        db.updateSocialHistory(logid,patientid,info[1], 8, UserID);
                    }else if (columnAffected.equals("Pet")) {
                        db.updateSocialHistory(logid,patientid,info[1], 9, UserID);
                    }else if (columnAffected.equals("Occupation")) {
                        db.updateSocialHistory(logid,patientid,info[1], 10, UserID);
                    }else if (columnAffected.equals("Like")) {
                        db.updateSocialHistory(logid,patientid,info[1], 11, UserID);
                    }else if (columnAffected.equals("Dislike")) {
                        db.updateSocialHistory(logid,patientid,info[1], 12, UserID);
                    }else if (columnAffected.equals("Hobby")) {
                        db.updateSocialHistory(logid,patientid,info[1], 13, UserID);
                    }else if (columnAffected.equals("Habbit")) {
                        db.updateSocialHistory(logid,patientid,info[1], 14, UserID);
                    }else if (columnAffected.equals("Holiday Experience")) {
                        db.updateSocialHistory(logid,patientid,info[1], 15, UserID);
                    }else if (columnAffected.equals("Education")) {
                        db.updateSocialHistory(logid,patientid,info[1], 16, UserID);
                    }else if (columnAffected.equals("Exercise")) {
                        db.updateSocialHistory(logid,patientid,info[1], 17, UserID);
                    }

                } else if (getNotificationId == 4) {
                    String[] newData = logdata.split(">");
                    db.updateLogPatientSpecInfo(logid, rowid, newData[0], UserID);
                    if (columnAffected.equals("prescription") || columnAffected.equals("routine")){
                        schedulerFunction(nric);
                    }
                }else if(getNotificationId == 12){
                    db.deletePatientInfoTable(logid, rowid, UserID);
                    if (columnAffected.equals("prescription") || columnAffected.equals("routine")){
                        schedulerFunction(nric);
                    }
                }else if(getNotificationId == 6){
                    db.updateNewUserStatus(logid, rowid, UserID);
                }
                else if(getNotificationId == 9){
                    db.updateNewGamesCat(logid, rowid, UserID);
                }
                else if(getNotificationId == 8){
                    String[] newData = logdata.split(">");
                    db.updateUserDetails(logid, rowid, newData[0], UserID);
                }
                else if(getNotificationId == 11){
                    String[] newData = logdata.split(">");
                    db.updateUserGamesCat(logid, rowid, newData[0], UserID);
                }else if(getNotificationId == 7){
                    db.deleteUser(logid, rowid, UserID);
                }else if(getNotificationId == 10){
                    db.deleteGamesCat(logid, rowid, UserID);
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
    public void schedulerFunction(String nric){
        ArrayList<Patient> patient = new ArrayList<Patient>();
        patient = DataHolder.getPatientList(context);
        Boolean check =false;
        int k = 0;
        for(int i =0; i<patient.size();i++){
            if(nric.equals(patient.get(i).getNric())){
                check = true;
                k = i;
                Log.v("Foundamatch", patient.get(i).getNric());
                break;

            }
        }
        ArrayList<DefaultEvent> de = new ArrayList<DefaultEvent>();
        patient = DataHolder.getPatientList(context);
        de = DataHolder.getDefaultEventList();
        Collections.sort(de, DefaultEvent.COMPARE_BY_TIME);
        scheduleScheduler ss = new scheduleScheduler();
        DateTime date1 = DateTime.now();
        ss.removeThenInsert(patient, de, date1, k);
        Toast.makeText(context, "Patient Schedule Successfully Changed", Toast.LENGTH_LONG).show();
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
                    }else if(getNotificationId == 6){
                        db.updateRejectionNewUser(logid,UserID,reasonStr, name);
                    }else if(getNotificationId == 9){
                        db.updateRejectGamesCat(logid,UserID,reasonStr, name);
                    }else if(getNotificationId == 8) {
                        db.updateRejectionEditUser(logid, UserID, reasonStr, name);
                    }else if(getNotificationId == 11) {
                        db.updateRejectionEditGamesCat(logid, UserID, reasonStr, name);
                    }else if(getNotificationId == 7) {
                        db.updateRejectDeleteUser(logid, UserID, reasonStr, name);
                    }else if(getNotificationId == 10) {
                        db.updateRejectionDeleteGamesCat(logid, UserID, reasonStr, name);
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
