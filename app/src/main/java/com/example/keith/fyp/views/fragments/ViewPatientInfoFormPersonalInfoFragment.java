package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.views.CustomField;
import com.example.keith.fyp.views.SpinnerField;
import com.example.keith.fyp.views.TextField;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ViewPatientInfoFormPersonalInfoFragment extends ViewPatientInfoFormFragment {

    private LinearLayout rootView;
    private MaterialSpinner genderSpinner;
    private EditText phoneNumberTextView;
    private EditText dobTextView;
    private ImageView photoView;

    private TextField firstNameTextField;
    private SpinnerField genderSpinnerField;

    private boolean initialDisplay = true;

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
        Bitmap photoBitmap = viewedPatient.getPhoto();
        if(photoBitmap != null) {
            photoView.setImageBitmap(photoBitmap);
        }

        genderSpinner = (MaterialSpinner) rootView.findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.option_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        phoneNumberTextView = (EditText) rootView.findViewById(R.id.phone_number_edit_text);
        phoneNumberTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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








        firstNameTextField = (TextField) rootView.findViewById(R.id.first_name_text_field);
        firstNameTextField.setText(viewedPatient.getFirstName());
        firstNameTextField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
            @Override
            public void textFieldSaved(String newValue) {
                viewedPatient.setFirstName(newValue);
            }
        });









        genderSpinnerField = (SpinnerField) rootView.findViewById(R.id.gender_spinner_field);
        char genderShort = viewedPatient.getGender();
        String[] genderShortArray = getResources().getStringArray(R.array.option_gender_short);
        final ArrayList<String> genderShortArrayList = new ArrayList<String>(Arrays.asList(genderShortArray));
        int index = genderShortArrayList.indexOf(String.valueOf(genderShort));

        String[] genderArray = getResources().getStringArray(R.array.option_gender);
        final ArrayList<String> genderArrayList = new ArrayList<String>(Arrays.asList(genderArray));
        String genderStr = genderArrayList.get(index);
        genderSpinnerField.setText(genderStr);
        genderSpinnerField.setSpinnerItems(getResources().getStringArray(R.array.option_gender));

        genderSpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
            @Override
            public void spinnerFieldItemSelected(int index) {
                genderSpinnerField.changeDisplayedText(genderArrayList.get(index));
            }
        });

        genderSpinnerField.setOnCustomFieldSaveListener(new CustomField.OnCustomFieldSaveListener() {
            @Override
            public void textFieldSaved(String newValue) {
                int index = genderArrayList.indexOf(newValue);
                viewedPatient.setGender(genderShortArrayList.get(index).charAt(0));
            }
        });






        TextField lastNameTextField = (TextField) rootView.findViewById(R.id.last_name_text_field);
        TextField nricTextField = (TextField) rootView.findViewById(R.id.nric_text_field);
        TextField addressTextField = (TextField) rootView.findViewById(R.id.address_text_field);
        TextField homeNumberTextField = (TextField) rootView.findViewById(R.id.home_number_text_field);
        TextField phoneNumberTextField = (TextField) rootView.findViewById(R.id.phone_number_text_field);



//        // Fill the form with the previously added content
//        try {
//            // Patient attribute name (second attribute) should be the same with the one in Patient class
//            patientFormSpecs.add(new PatientFormSpec(R.id.first_name_edit_text, "firstName", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.last_name_edit_text, "lastName", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.nric_edit_text, "nric", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.address_edit_text, "address", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.home_number_edit_text, "homeNumber", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.phone_number_edit_text, "phoneNumber", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.gender_spinner, "gender", SPINNER_GENDER));
//            patientFormSpecs.add(new PatientFormSpec(R.id.dob_date_picker, "dob", DATE_PICKER));
//            patientFormSpecs.add(new PatientFormSpec(R.id.guardian_full_name_edit_text, "guardianFullName", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.guardian_contact_number_edit_text, "guardianContactNumber", TEXT_VIEW));
//            patientFormSpecs.add(new PatientFormSpec(R.id.guardian_email_edit_text, "guardianEmail", TEXT_VIEW));
//
//            prepareForm(rootView, patientFormSpecs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

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
