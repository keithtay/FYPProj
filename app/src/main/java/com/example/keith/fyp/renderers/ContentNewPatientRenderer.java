package com.example.keith.fyp.renderers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.activities.ViewPatientActivity;
import com.example.keith.fyp.views.activities.ViewScheduleActivity;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * ContentNewPatientRenderer is {@link Renderer} to render the content of a {@link com.example.keith.fyp.models.Notification} with type {@link com.example.keith.fyp.models.Notification#TYPE_NEW_PATIENT}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ContentNewPatientRenderer extends ContentRenderer {

    private Patient newPatient;

    /**
     * Create a content new patient renderer with the specified values.
     */
    public ContentNewPatientRenderer(LayoutInflater inflater, Patient newPatient) {
        super(inflater);
        this.newPatient = newPatient;
    }

    @Override
    public View render() {
        final Context context = inflater.getContext();

        View rootView = inflater.inflate(R.layout.notification_detail_content_new_patient, null);

        CardView backgroundCardView = (CardView) rootView.findViewById(R.id.background_card_view);
        backgroundCardView.setCardElevation(4);

        FancyButton viewNewPatientButton = (FancyButton) rootView.findViewById(R.id.view_more_button);
        if (notification.getStatus() != Notification.STATUS_NONE) {
            UtilsUi.removeView(viewNewPatientButton);
        } else {
            viewNewPatientButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString(Global.STATE_SELECTED_PATIENT_NRIC, newPatient.getNric());
                    editor.commit();
                    Intent intent = new Intent(context, ViewScheduleActivity.class);
//                    Intent intent = new Intent(context, ViewPatientActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }
            });
        }

        UtilsUi.removeView(rootView.findViewById(R.id.action_button_divider));
        UtilsUi.removeView(rootView.findViewById(R.id.log_problem_button));

        ImageView photoImageView = (ImageView) rootView.findViewById(R.id.photo_image_view);
        TextView firstNameTextView = (TextView) rootView.findViewById(R.id.first_name_text_view);
        TextView lastNameTextView = (TextView) rootView.findViewById(R.id.last_name_text_view);
        TextView genderTextView = (TextView) rootView.findViewById(R.id.gender_text_view);
        TextView dobTextView = (TextView) rootView.findViewById(R.id.dob_text_view);

        photoImageView.setImageBitmap(newPatient.getPhoto());
        firstNameTextView.setText(newPatient.getFirstName());
        lastNameTextView.setText(newPatient.getLastName());

        char genderChar = newPatient.getGender();
        String genderStr = "Male";
        if (genderChar == 'F') {
            genderStr = "Female";
        }
        genderTextView.setText(genderStr);

        dobTextView.setText(newPatient.getDob().toString(Global.DATE_FORMAT));

        return rootView;
    }
}
