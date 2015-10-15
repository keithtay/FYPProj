package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the personal information form in creating patient
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class CreatePatientInfoFormPersonalInfoFragment extends CreatePatientInfoFormFragment {

    private LinearLayout rootView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText nricEditText;
    private EditText addressEditText;
    private EditText homeNumberEditText;
    private EditText phoneNumberEditText;
    private MaterialSpinner genderSpinner;
    private EditText dobEditText;
    private EditText guardianNameEditText;
    private EditText guardianContactNumberEditText;
    private EditText guardianEmailEditText;
    private ImageView photoView;

    private static final int SELECT_PICTURE = 1;
    private static final int CROP_PICTURE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.init();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_create_patient_info_form_personal_info, container, false);

        firstNameEditText = (EditText) rootView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) rootView.findViewById(R.id.last_name_edit_text);
        nricEditText = (EditText) rootView.findViewById(R.id.nric_edit_text);
        addressEditText = (EditText) rootView.findViewById(R.id.address_edit_text);
        homeNumberEditText = (EditText) rootView.findViewById(R.id.home_number_edit_text);

        guardianNameEditText = (EditText) rootView.findViewById(R.id.guardian_full_name_edit_text);
        guardianContactNumberEditText = (EditText) rootView.findViewById(R.id.guardian_contact_number_edit_text);
        guardianEmailEditText = (EditText) rootView.findViewById(R.id.guardian_email_edit_text);

        photoView = (ImageView) rootView.findViewById(R.id.photo_image_view);
        photoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
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
        Bitmap photoBitmap = DataHolder.getCreatedPatient().getPhoto();
        if (photoBitmap != null) {
            photoView.setImageBitmap(photoBitmap);
        }

        dobEditText = (EditText) rootView.findViewById(R.id.dob_date_picker);
        UtilsUi.setupEditTextToBeDatePicker(dobEditText, getString(R.string.select_date_of_birth));

        genderSpinner = (MaterialSpinner) rootView.findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        phoneNumberEditText = (EditText) rootView.findViewById(R.id.phone_number_edit_text);
        phoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    hideKeyboard();
                    genderSpinner.requestFocus();
                    genderSpinner.performClick();
                    textView.clearFocus();
                }
                return true;
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            highlightEmptyField(bundle);
        }

        // Fill the form with the previously added content
        try {
            // Patient attribute name (second attribute) should be the same with the one in Patient class
            patientFormSpecs.add(new PatientFormSpec(R.id.first_name_edit_text, "firstName", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.last_name_edit_text, "lastName", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.nric_edit_text, "nric", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.address_edit_text, "address", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.home_number_edit_text, "homeNumber", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.phone_number_edit_text, "phoneNumber", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.gender_spinner, "gender", SPINNER_GENDER));
            patientFormSpecs.add(new PatientFormSpec(R.id.dob_date_picker, "dob", DATE_PICKER));
            patientFormSpecs.add(new PatientFormSpec(R.id.guardian_full_name_edit_text, "guardianFullName", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.guardian_contact_number_edit_text, "guardianContactNumber", TEXT_VIEW));
            patientFormSpecs.add(new PatientFormSpec(R.id.guardian_email_edit_text, "guardianEmail", TEXT_VIEW));

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

    private void highlightEmptyField(Bundle bundle) {
        ArrayList<Integer> emptyFieldIdList = bundle.getIntegerArrayList(Global.EXTRA_EMPTY_FIELD_ID_LIST);

        if (emptyFieldIdList.contains(Global.PHOTO)) {
            Toast.makeText(getActivity(), R.string.error_msg_no_photo, Toast.LENGTH_SHORT).show();
        }
        if (emptyFieldIdList.contains(Global.FIRST_NAME_FIELD)) {
            firstNameEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.LAST_NAME_FIELD)) {
            lastNameEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.NRIC_FIELD)) {
            nricEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.ADDRESS_FIELD)) {
            addressEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.HOME_NUMBER_FIELD)) {
            homeNumberEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.PHONE_NUMBER_FIELD)) {
            phoneNumberEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.GENDER_FIELD)) {
            genderSpinner.setError(R.string.error_msg_field_required);
        }
        if (emptyFieldIdList.contains(Global.DOB_FIELD)) {
            dobEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.GUARDIAN_NAME_FIELD)) {
            guardianNameEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.GUARDIAN_CONTACT_NUMBER_FIELD)) {
            guardianContactNumberEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
        if (emptyFieldIdList.contains(Global.GUARDIAN_EMAIL_FIELD)) {
            guardianEmailEditText.setError(activity.getString(R.string.error_msg_field_required));
        }
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

                DataHolder.getCreatedPatient().setPhoto(croppedImageBitmap);
        }
    }
}
