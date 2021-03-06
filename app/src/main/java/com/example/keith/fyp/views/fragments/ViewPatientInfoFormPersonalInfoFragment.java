package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;

import java.lang.reflect.InvocationTargetException;

/**
 * Fragment to display the patient's personal information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientInfoFormPersonalInfoFragment extends ViewPatientInfoFormFragment {

    private LinearLayout rootView;
    private ImageView photoView;

    private static final int SELECT_PICTURE = 1;
    private static final int CROP_PICTURE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_info_form_personal_info, container, false);

        photoView = (ImageView) rootView.findViewById(R.id.photo_image_view);
        photoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String pickTitle = "Select or take a new Picture";
                Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
                chooserIntent.putExtra
                        (
                                Intent.EXTRA_INITIAL_INTENTS,
                                new Intent[]{takePhotoIntent}
                        );

                startActivityForResult(chooserIntent, SELECT_PICTURE);
                return false;
            }
        });


        Bitmap photoBitmap = viewedPatient.getPhoto();
        if (photoBitmap != null) {
            photoView.setImageBitmap(photoBitmap);
        }

        // Patient attribute name (second attribute) should be the same with the one in Patient class
        patientFormSpecs.add(new PatientFormSpec(R.id.first_name_text_field, "firstName", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.last_name_text_field, "lastName", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.nric_text_field, "nric", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.address_text_field, "address", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.home_number_text_field, "homeNumber", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.phone_number_text_field, "phoneNumber", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.gender_spinner_field, "gender", SPINNER_FIELD_GENDER));
        patientFormSpecs.add(new PatientFormSpec(R.id.dob_date_field, "dob", DATE_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.guardian_full_name_text_field, "guardianFullName", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.guardian_contact_number_text_field, "guardianContactNumber", TEXT_FIELD));
        patientFormSpecs.add(new PatientFormSpec(R.id.guardian_email_text_field, "guardianEmail", TEXT_FIELD));

        try {
            prepareForm(rootView, patientFormSpecs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case SELECT_PICTURE:
                if (data == null) {
                    // TODO: Display an error
                    return;
                }

                Intent cropIntent = new Intent("com.android.camera.action.CROP");

                Uri picUri = data.getData();
                cropIntent.setDataAndType(picUri, "image/*");
                cropIntent.putExtra("crop", "true");
                cropIntent.putExtra("outputX", 600);
                cropIntent.putExtra("outputY", 800);
                cropIntent.putExtra("aspectX", 3);
                cropIntent.putExtra("aspectY", 4);
                cropIntent.putExtra("scale", true);
                cropIntent.putExtra("return-data", true);
                startActivityForResult(cropIntent, CROP_PICTURE);

                break;
            case CROP_PICTURE:
                Bundle extras = data.getExtras();
                Bitmap croppedImageBitmap = extras.getParcelable("data");
                photoView.setImageBitmap(croppedImageBitmap);

                DataHolder.getViewedPatient().setPhoto(croppedImageBitmap);
        }
    }
}
