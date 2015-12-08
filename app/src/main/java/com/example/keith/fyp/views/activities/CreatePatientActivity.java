package com.example.keith.fyp.views.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.CreatePatientCommunicator;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.CreatePatientFormFragmentEncoder;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.PatientInfoCategListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.tesseract.android.ResultIterator;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.joda.time.DateTime;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Activity to display the create patient form
 */
public class CreatePatientActivity extends AppCompatActivity implements CreatePatientCommunicator, Drawer.OnDrawerItemClickListener {

//    public static final TessBaseAPI BASE_API = new TessBaseAPI();
    private PatientInfoCategListFragment infoCategListFragment;
    private Fragment fragmentDisplayed;

    private FragmentManager fragmentManager;
    private InputMethodManager inputManager;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    private static final String TAG = "CaptureImageOcr";

    private String selectedPatientDraftId;

    private SharedPreferences mPrefs;

    private static final int ACTION_CAPTURE_PICTURE = 1;
    private static final int ACTION_CROP_PICTURE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        selectedPatientDraftId = mPrefs.getString(Global.STATE_SELECTED_PATIENT_DRAFT_ID, null);
        if (UtilsString.isEmpty(selectedPatientDraftId)) {
            selectedPatientDraftId = getIntent().getStringExtra(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID);
        }

        if (!UtilsString.isEmpty(selectedPatientDraftId)) {
            String json = mPrefs.getString(Global.SP_CREATE_PATIENT_DRAFT, "");
            if (!UtilsString.isEmpty(json)) {
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, Patient>>() {
                }.getType();
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
                    Patient createdPatient = DataHolder.getCreatedPatient();
                    String fName = createdPatient.getFirstName();
                    String lName = createdPatient.getLastName();
                    String nric = createdPatient.getNric();
                    String phoneNumber = createdPatient.getPhoneNumber();
                    String gufullname = createdPatient.getGuardianFullName();
                    String guContact = createdPatient.getGuardianContactNumber();
                    if(fName == null || lName == null || nric == null || phoneNumber == null || gufullname == null || guContact == null){
                        Log.v("Never Fill up fields", "Testing");
                    }else{
                        dbfile db = new dbfile();
                        db.insertNewPatient(createdPatient.getFirstName(), createdPatient.getLastName(), createdPatient.getNric(), createdPatient.getAddress(), createdPatient.getHomeNumber(), createdPatient.getPhoneNumber(), createdPatient.getGender(), createdPatient.getDob().toString(), createdPatient.getGuardianFullName(), createdPatient.getGuardianContactNumber(), createdPatient.getGuardianEmail());

                        int id = db.getPatientId(createdPatient.getNric());
                        String seeid = String.valueOf(id);
                        Log.v("User id is", seeid);
                        if (createdPatient.getAllergyList().size() >=1 ) {
                            ArrayList<Allergy> t1 = createdPatient.getAllergyList();
                            for (int i = 0; i < t1.size(); i++) {
                                String name = t1.get(i).getName();
                                String reaction = t1.get(i).getReaction();
                                String notes = t1.get(i).getNotes();
                                Log.v("Allergy Type", name + " " + reaction + " " + notes);
                            }
                        }
                        DataHolder.resetCreatedPatient();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);


                        //allergy = 1; vital =2; social history=3; prescription =4; routine = 5;

                    }
//                    if (!createdpatient.getFirstName().equals(null)){
//                        Log.v("Testing if htis works:", "Testing");
//                    }
//                    Toast.makeText(getBaseContext(),"Successfully Added!",Toast.LENGTH_LONG);
//                    dbfile db = new dbfile();
//                    Patient createdPatient;
//                    createdPatient = DataHolder.getCreatedPatient();
//                    if(createdPatient.getFirstName() != "" && createdPatient.getLastName() != "") {
//                        db.insertNewPatient(createdPatient.getFirstName(), createdPatient.getLastName(), createdPatient.getAddress(), createdPatient.getHomeNumber(), createdPatient.getPhoneNumber(), createdPatient.getGender(), createdPatient.getDob().toString(), createdPatient.getGuardianFullName(), createdPatient.getGuardianContactNumber(), createdPatient.getGuardianEmail());
//                        Log.i("Testing:", createdPatient.getFirstName());
//                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//                        startActivity(intent);
//                    }
                }
            });
        }

        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mOpenCVCallBack)) {
            Log.e(TAG, "Cannot connect to OpenCV Manager");
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
            if (!UtilsString.isEmpty(selectedPatientDraftId)) {
                intent.putExtra(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID, selectedPatientDraftId);
            }
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
            if (!UtilsString.isEmpty(selectedPatientDraftId)) {
                intent.putExtra(Global.EXTRA_SELECTED_PATIENT_DRAFT_ID, selectedPatientDraftId);
            }
            intent.putIntegerArrayListExtra(Global.EXTRA_EMPTY_FIELD_ID_LIST, emptyFieldIdList);
            startActivity(intent);
        }
    }

    private void changeFragment(int index) {
        fragmentDisplayed = CreatePatientFormFragmentEncoder.getFragment(index);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.create_patient_info_form_fragment_container, fragmentDisplayed);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void changeFragment(int index, ArrayList<Integer> emptyFieldIdList) {
        fragmentDisplayed = CreatePatientFormFragmentEncoder.getFragment(index);
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
        checkWhetherInTheMiddleOfCreatingPatient(Global.NAVIGATION_PATIENT_LIST_ID);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        if (!UtilsString.isEmpty(selectedPatientDraftId)) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(Global.STATE_SELECTED_PATIENT_DRAFT_ID, selectedPatientDraftId);
            editor.commit();
        }
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        final int selectedIdentifier = drawerItem.getIdentifier();

        if (selectedIdentifier != Global.NAVIGATION_PATIENT_LIST_ID) {
            checkWhetherInTheMiddleOfCreatingPatient(selectedIdentifier);
        }

        return true;
    }

    private void checkWhetherInTheMiddleOfCreatingPatient(final int selectedIdentifier) {
        boolean isEditing = false;

        if (UtilsString.isEmpty(selectedPatientDraftId)) {
            isEditing = !DataHolder.getCreatedPatient().equals(new Patient());
        } else {
            isEditing = !DataHolder.getCreatedPatient().equals(DataHolder.getCreatedPatientEditInitial());
        }

        if (isEditing) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            if (UtilsString.isEmpty(selectedPatientDraftId)) {
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

        if (!UtilsString.isEmpty(json)) {
            Type type = new TypeToken<HashMap<String, Patient>>() {
            }.getType();
            draftMap = gson.fromJson(json, type);
        }

        Patient createdPatient = DataHolder.getCreatedPatient();
        String mapKey;
        if (!UtilsString.isEmpty(selectedPatientDraftId)) {
            mapKey = selectedPatientDraftId;
        } else {
            DateTime now = DateTime.now();
            mapKey = now.toString(Global.DATE_TIME_FORMAT);

            String firstName = createdPatient.getFirstName();
            if (!UtilsString.isEmpty(firstName)) {
                mapKey += " | " + firstName;
            }
        }
        draftMap.put(mapKey, createdPatient);

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

    /**
     * ======================================================
     * <p/>
     * OCR Features
     * <p/>
     * =======================================================
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_ocr:
                prepareOcrEnvirontment();
                startCaptureImageOcrActivity();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case ACTION_CAPTURE_PICTURE:

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap photoTaken = BitmapFactory.decodeFile(path, options);
                Mat origImg = new Mat();
                Utils.bitmapToMat(photoTaken, origImg);

                Rect cropArea = getCropArea(origImg);
                if(cropArea == null) {
                    // Manual cropping
                    Intent cropIntent = new Intent("com.android.camera.action.CROP");

                    cropIntent.setDataAndType(outputFileUri, "image/*");
                    cropIntent.putExtra("crop", "true");
                    cropIntent.putExtra("scale", true);
                    cropIntent.putExtra("return-data", true);
                    cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(cropIntent, ACTION_CROP_PICTURE);
                } else {
                    // Automatic cropping
                    Size size = origImg.size();
                    Mat croppedImg = new Mat(origImg, cropArea);
                    saveMatAsJpg(croppedImg, fileName);
                    onPhotoCropped();
                }
                break;
            case ACTION_CROP_PICTURE:
                onPhotoCropped();
                break;
        }
    }

    private void onPhotoCropped() {
        resizeImage();
//        if (checkIfNric()) {
        performOcr();
//        } else {
//            showNoNricDetectedToast();
//        }
    }

    private void resizeImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        saveMatAsJpg(mat, fileName);
    }


    private void saveMatAsJpg(Mat mat, String fileName) {
        Mat clone = mat.clone();
        Imgproc.resize(clone, clone, new Size(3000,1880));
        Bitmap bitmap = Bitmap.createBitmap(clone.cols(), clone.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(clone, bitmap);

        String fullPath = Global.DATA_PATH + fileName;

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fullPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Rect getCropArea(Mat origImg) {
        Mat processedImg = origImg.clone();

        Imgproc.cvtColor(processedImg, processedImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(processedImg, processedImg, 127, 255, Imgproc.THRESH_BINARY);

        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hier = new Mat();
        Imgproc.findContours(processedImg, contours, hier, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        Rect cropArea = null;
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);

            if (area > 7500 && area < 200000 && area > maxArea) {
                maxArea = area;

                //Convert contours(i) from MatOfPoint to MatOfPoint2f
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                //Processing on mMOP2f1 which is in type MatOfPoint2f
                double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
                Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

                //Convert back to MatOfPoint
                MatOfPoint points = new MatOfPoint(approxCurve.toArray());

                // Get bounding rect of contour
                cropArea = Imgproc.boundingRect(points);
            }
        }

        return cropArea;
    }

    private void showNoNricDetectedToast() {
        Toast.makeText(this, "The captured image is not NRIC", Toast.LENGTH_LONG).show();
    }

    // To be able to use openCV library
    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV Manager Connected");
                    break;
                case LoaderCallbackInterface.INIT_FAILED:
                    Log.i(TAG, "Init Failed");
                    break;
                case LoaderCallbackInterface.INSTALL_CANCELED:
                    Log.i(TAG, "Install Cancelled");
                    break;
                case LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION:
                    Log.i(TAG, "Incompatible Version");
                    break;
                case LoaderCallbackInterface.MARKET_ERROR:
                    Log.i(TAG, "Market Error");
                    break;
                default:
                    Log.i(TAG, "OpenCV Manager Install");
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    private boolean checkIfNric() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap photoTaken = BitmapFactory.decodeFile(path, options);
        Bitmap nricTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.nric_template);

        photoTaken = Bitmap.createScaledBitmap(photoTaken, 100, 100, true);
        nricTemplate = Bitmap.createScaledBitmap(nricTemplate, 100, 100, true);

        Mat img1 = new Mat();
        Utils.bitmapToMat(photoTaken, img1);
        Mat img2 = new Mat();
        Utils.bitmapToMat(nricTemplate, img2);

        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.cvtColor(img2, img2, Imgproc.COLOR_RGBA2GRAY);
        img1.convertTo(img1, CvType.CV_32F);
        img2.convertTo(img2, CvType.CV_32F);

        Mat hist1 = new Mat();
        Mat hist2 = new Mat();
        MatOfInt histSize = new MatOfInt(180);
        MatOfInt channels = new MatOfInt(0);
        ArrayList<Mat> bgr_planes1 = new ArrayList<Mat>();
        ArrayList<Mat> bgr_planes2 = new ArrayList<Mat>();
        Core.split(img1, bgr_planes1);
        Core.split(img2, bgr_planes2);
        MatOfFloat histRanges = new MatOfFloat(0f, 180f);
        boolean accumulate = false;
        Imgproc.calcHist(bgr_planes1, channels, new Mat(), hist1, histSize, histRanges, accumulate);
        Core.normalize(hist1, hist1, 0, hist1.rows(), Core.NORM_MINMAX, -1, new Mat());
        Imgproc.calcHist(bgr_planes2, channels, new Mat(), hist2, histSize, histRanges, accumulate);
        Core.normalize(hist2, hist2, 0, hist2.rows(), Core.NORM_MINMAX, -1, new Mat());
        img1.convertTo(img1, CvType.CV_32F);
        img2.convertTo(img2, CvType.CV_32F);
        hist1.convertTo(hist1, CvType.CV_32F);
        hist2.convertTo(hist2, CvType.CV_32F);

        double compare = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CHISQR);
        Log.i(TAG, "compare: " + compare);

        if (compare < 10000) {
            return true;
        }

        return false;
    }

    private void prepareOcrEnvirontment() {
        String lang = Global.LANG;
        String DATA_PATH = Global.DATA_PATH;

        String[] paths = new String[]{DATA_PATH, DATA_PATH + "tessdata/"};

        for (String path : paths) {
            File dir = new File(path);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
                    return;
                } else {
                    Log.v(TAG, "Created directory " + path + " on sdcard");
                }
            }

        }

        // lang.traineddata file with the app (in assets folder)
        // You can get them at:
        // http://code.google.com/p/tesseract-ocr/downloads/list
        // This area needs work and optimization
        if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
            try {

                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
                //GZIPInputStream gin = new GZIPInputStream(in);
                OutputStream out = new FileOutputStream(DATA_PATH
                        + "tessdata/" + lang + ".traineddata");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                //while ((lenf = gin.read(buff)) > 0) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                //gin.close();
                out.close();

                Log.v(TAG, "Copied " + lang + " traineddata");
            } catch (IOException e) {
                Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
            }
        }
    }

    private String fileName = "ocr.jpg";
    private String path = Global.DATA_PATH + fileName;
    private Uri outputFileUri;

    private void startCaptureImageOcrActivity() {
        File file = new File(path);
        outputFileUri = Uri.fromFile(file);

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, ACTION_CAPTURE_PICTURE);
    }

    private void performOcr() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        // Thresholding (Binarization)
        performAdaptiveThreshold(bitmap);

        // Set unimportant area to be white (Patching)
        bitmap = bitmap.copy(bitmap.getConfig(), true);
        bitmap.setHasAlpha(true);
        patchUnimportantArea(bitmap);

        // Rotate bitmap
        try {
            bitmap = autoRotateBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.setDebug(true);
        baseApi.init(Global.DATA_PATH, Global.LANG);
        baseApi.setImage(ReadFile.readBitmap(bitmap));


        int meanConfidence = baseApi.meanConfidence();
        ResultIterator resultIterator = baseApi.getResultIterator();
        ArrayList<String> result = new ArrayList<>();
        int level = TessBaseAPI.PageIteratorLevel.RIL_TEXTLINE;
        do {
            String line = resultIterator.getUTF8Text(level);
            if (!UtilsString.isEmpty(line)) {
                line = line.replaceAll("[^a-zA-Z0-9-]+", " ");
                result.add(line);
                Log.i(TAG, line);
            }
        } while (resultIterator.next(level));
        baseApi.end();

        // Delete captured photo
        File file = new File(path);
        file.delete();

        Log.i(TAG, "Mean Confidence: " + meanConfidence);

        if (result.size() > 0) {
            float similarityThreshold = (float) 0.7;
            if (UtilsString.similarity(result.get(0), "Republic of Singapore", false) < similarityThreshold) {
                showNoNricDetectedToast();
            } else {
                result.remove(0);
                Intent intent = new Intent(this, OcrReviewActivity.class);
                intent.putExtra(Global.EXTRA_RECOGNIZED_TEXT, result);
                startActivity(intent);
            }
        }
    }

    private Bitmap autoRotateBitmap(Bitmap bitmap) throws IOException {
        ExifInterface exif = new ExifInterface(path);
        int exifOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        Log.v(TAG, "Orient: " + exifOrientation);

        int rotate = 0;

        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
        }

        Log.v(TAG, "Rotation: " + rotate);

        if (rotate != 0) {

            // Getting width & height of the given image.
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            // Setting pre rotate
            Matrix mtx = new Matrix();
            mtx.preRotate(rotate);

            // Rotating Bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
        }

        // Convert to ARGB_8888, required by tess
        return bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }

    private void patchUnimportantArea(Bitmap bitmap) {
        float expandFactor = (float) 1.5;

        RectF finBox = new RectF(5.03f - expandFactor, 5.21f - expandFactor, 64.32f + expandFactor, 23.28f + expandFactor);
        RectF nameBox = new RectF(36.26f - expandFactor, 33.52f - expandFactor, 85.38f + expandFactor, 46.55f + expandFactor);
        RectF dobAndSexBox = new RectF(36.26f - expandFactor, 59.59f - expandFactor, 60.82f + expandFactor, 63.31f + expandFactor);
        ArrayList<RectF> boxList = new ArrayList<>();
        boxList.add(finBox);
        boxList.add(nameBox);
        boxList.add(dobAndSexBox);

        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float xPostPercent = (float) (i * 100) / width;
                float yPostPercent = (float) (j * 100) / height;

                boolean isPointInsideBox = false;

                for (RectF box : boxList) {
                    if (box.contains(xPostPercent, yPostPercent)) {
                        isPointInsideBox = true;
                        break;
                    }
                }

                if (!isPointInsideBox) {
                    bitmap.setPixel(i, j, Color.argb(255, 255, 255, 255));
                }
            }
        }
    }

    private void performAdaptiveThreshold(Bitmap bitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.adaptiveThreshold(mat, mat, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 2);
        Utils.matToBitmap(mat, bitmap);
    }
}
