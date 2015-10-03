package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.CreatePatientCommunicator;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.CreatePatientFormFragmentDecoder;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.PatientInfoCategListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.iconics.utils.Utils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class CreatePatientActivity extends AppCompatActivity implements CreatePatientCommunicator, Drawer.OnDrawerItemClickListener {

    private PatientInfoCategListFragment infoCategListFragment;
    private Fragment fragmentDisplayed;

    private FragmentManager fragmentManager;
    private InputMethodManager inputManager;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private static final String TAG = "CaptureImageOcr";

    private String selectedPatientDraftId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedPatientDraftId = getIntent().getStringExtra(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID);
        if(!UtilsString.isEmpty(selectedPatientDraftId)) {
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String json = mPrefs.getString(Global.SP_CREATE_PATIENT_DRAFT, "");
            if(!UtilsString.isEmpty(json)) {
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, Patient>>(){}.getType();
                HashMap<String, Patient> draftMap = gson.fromJson(json, type);
                Patient patient = draftMap.get(selectedPatientDraftId);
                DataHolder.setCreatedPatientEditInitial(patient);
            }
        }

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        fragmentManager = getFragmentManager();
        infoCategListFragment = (PatientInfoCategListFragment) fragmentManager.findFragmentById(R.id.create_patient_info_categ_list_fragment);
        infoCategListFragment.setCommunicator(this);

        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        FloatingActionButton createNewPatientFab = (FloatingActionButton) findViewById(R.id.save_new_patient_fab);
        if (createNewPatientFab != null) {
            createNewPatientFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkRequiredFields();
                }
            });
        }
    }

    private void checkRequiredFields() {
        Intent intent = new Intent(Global.ACTION_CREATE_NEW_PATIENT);
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_patient, menu);
        return true;
    }

    @Override
    public void respond(int index) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Hide keyboard
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            changeFragment(index);
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, CreatePatientFormActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_CATEGORY, index);
            startActivity(intent);
        }
    }

    @Override
    public void respond(int index, ArrayList<Integer> emptyFieldIdList) {
        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape orientation

            // Hide keyboard
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            changeFragment(index, emptyFieldIdList);
        } else {
            // In portrait orientation
            Intent intent = new Intent(this, CreatePatientFormActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_CATEGORY, index);
            intent.putIntegerArrayListExtra(Global.EXTRA_EMPTY_FIELD_ID_LIST, emptyFieldIdList);
            startActivity(intent);
        }
    }

    private void changeFragment(int index) {
        fragmentDisplayed = CreatePatientFormFragmentDecoder.getFragment(index);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void changeFragment(int index, ArrayList<Integer> emptyFieldIdList) {
        fragmentDisplayed = CreatePatientFormFragmentDecoder.getFragment(index);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(Global.EXTRA_EMPTY_FIELD_ID_LIST, emptyFieldIdList);
        fragmentDisplayed.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        checkWhetherInTheMiddleOfCreatingPatient(Global.NAVIGATION_PATIENT_LIST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "resultCode: " + resultCode);

        if (resultCode == -1) {
//            onPhotoTaken();
        } else {
            Log.v(TAG, "User cancelled");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_ocr:
//                prepareOcrEnvirontment();
//                startCaptureImageOcrActivity();
        }
        return true;
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        final int selectedIdentifier = drawerItem.getIdentifier();

        if(selectedIdentifier != Global.NAVIGATION_PATIENT_LIST_ID) {

            checkWhetherInTheMiddleOfCreatingPatient(selectedIdentifier);
        }

        return true;
    }

    private void checkWhetherInTheMiddleOfCreatingPatient(final int selectedIdentifier) {
        boolean isEditing = false;

        if(UtilsString.isEmpty(selectedPatientDraftId)) {
            isEditing =  !DataHolder.getCreatedPatient().equals(new Patient());
        } else {
            isEditing = !DataHolder.getCreatedPatient().equals(DataHolder.getCreatedPatientEditInitial());
        }

        if(isEditing) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            if(UtilsString.isEmpty(selectedPatientDraftId)) {
                builder.title("You are in the middle of creating a patient. Do you want to save current patient as draft?");
                builder.positiveText(R.string.button_proceed_with_save);
            } else {
                builder.title("You have changed the patient draft. Do you want to save the changes?");
                builder.positiveText(R.string.button_save_draft_changes);
            }

            builder.neutralText(R.string.button_no);
            builder.negativeText(R.string.button_cancel);

            builder.callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    savePatientAsDraft();
                    openAnotherTabInDashboardActivity(selectedIdentifier);
                }

                @Override
                public void onNeutral(MaterialDialog dialog) {
                    super.onNeutral(dialog);
                    openAnotherTabInDashboardActivity(selectedIdentifier);
                }
            });
            builder.show();
        } else {
            openAnotherTabInDashboardActivity(selectedIdentifier);
        }
    }

    private void savePatientAsDraft() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String json = mPrefs.getString(Global.SP_CREATE_PATIENT_DRAFT, "");
        HashMap<String, Patient> draftMap = new HashMap();

        if(!UtilsString.isEmpty(json)) {
            Type type = new TypeToken<HashMap<String, Patient>>(){}.getType();
            draftMap = gson.fromJson(json, type);
        }

        String mapKey;
        if(!UtilsString.isEmpty(selectedPatientDraftId)) {
            mapKey = selectedPatientDraftId;
        } else {
            DateTime now = DateTime.now();
            mapKey = now.toString(Global.DATE_TIME_FORMAT);
        }
        draftMap.put(mapKey, DataHolder.getCreatedPatient());

        json = gson.toJson(draftMap);
        prefsEditor.putString(Global.SP_CREATE_PATIENT_DRAFT, json);
        prefsEditor.commit();
    }

    private void openAnotherTabInDashboardActivity(int selectedIdentifier) {
        DataHolder.resetCreatedPatient();

        miniDrawer.updateItem(Global.NAVIGATION_PATIENT_LIST_ID);

        Intent intent = new Intent(CreatePatientActivity.this, DashboardActivity.class);
        intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

//    private void prepareOcrEnvirontment() {
//        String lang = Global.LANG;
//        String DATA_PATH = Global.DATA_PATH;
//
//        String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };
//
//        for (String path : paths) {
//            File dir = new File(path);
//            if (!dir.exists()) {
//                if (!dir.mkdirs()) {
//                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
//                    return;
//                } else {
//                    Log.v(TAG, "Created directory " + path + " on sdcard");
//                }
//            }
//
//        }
//
//        // lang.traineddata file with the app (in assets folder)
//        // You can get them at:
//        // http://code.google.com/p/tesseract-ocr/downloads/list
//        // This area needs work and optimization
//        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
//            try {
//
//                AssetManager assetManager = getAssets();
//                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
//                //GZIPInputStream gin = new GZIPInputStream(in);
//                OutputStream out = new FileOutputStream(DATA_PATH
//                        + "tessdata/" + lang + ".traineddata");
//
//                // Transfer bytes from in to out
//                byte[] buf = new byte[1024];
//                int len;
//                //while ((lenf = gin.read(buff)) > 0) {
//                while ((len = in.read(buf)) > 0) {
//                    out.write(buf, 0, len);
//                }
//                in.close();
//                //gin.close();
//                out.close();
//
//                Log.v(TAG, "Copied " + lang + " traineddata");
//            } catch (IOException e) {
//                Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
//            }
//        }
//    }
//
//    private String path = Global.DATA_PATH + "ocr.jpg";;
//
//    private void startCaptureImageOcrActivity() {
//        File file = new File(path);
//        Uri outputFileUri = Uri.fromFile(file);
//
//        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//
//        startActivityForResult(intent, 0);
//    }
//
//    private void onPhotoTaken() {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
//
//        try {
//            ExifInterface exif = new ExifInterface(path);
//            int exifOrientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_NORMAL);
//
//            Log.v(TAG, "Orient: " + exifOrientation);
//
//            int rotate = 0;
//
//            switch (exifOrientation) {
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    rotate = 90;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    rotate = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    rotate = 270;
//                    break;
//            }
//
//            Log.v(TAG, "Rotation: " + rotate);
//
//            if (rotate != 0) {
//
//                // Getting width & height of the given image.
//                int w = bitmap.getWidth();
//                int h = bitmap.getHeight();
//
//                // Setting pre rotate
//                Matrix mtx = new Matrix();
//                mtx.preRotate(rotate);
//
//                // Rotating Bitmap
//                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
//            }
//
//            // Convert to ARGB_8888, required by tess
//            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//
//        } catch (IOException e) {
//            Log.e(TAG, "Couldn't correct orientation: " + e.toString());
//        }
//
//        // _image.setImageBitmap( bitmap );
//
//        Log.v(TAG, "Before baseApi");
//
//        TessBaseAPI baseApi = new TessBaseAPI();
//        baseApi.setDebug(true);
//        baseApi.init(Global.DATA_PATH, Global.LANG);
//        baseApi.setImage(bitmap);
//
//        String recognizedText = baseApi.getUTF8Text();
//
//        baseApi.end();
//
//        // You now have the text in recognizedText var, you can do anything with it.
//        // We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
//        // so that garbage doesn't make it to the display.
//
//        Log.v(TAG, "OCRED TEXT: " + recognizedText);
//
//
//        recognizedText = recognizedText.trim();
//
//        if ( recognizedText.length() != 0 ) {
//            Intent intent = new Intent(this, OcrReviewActivity.class);
//            intent.putExtra(Global.EXTRA_RECOGNIZED_TEXT, recognizedText);
//            startActivity(intent);
//        }
//    }
}
