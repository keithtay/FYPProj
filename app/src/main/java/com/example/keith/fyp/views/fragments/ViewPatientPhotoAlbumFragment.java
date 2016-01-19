package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.PhotoAlbum;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.adapters.PhotoAlbumTitleAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's photo album fragment
 * //if not using Picasso, change arraylist of <string> to arraylist of <bitmap>.
 * @author  kok sang
 */
public class ViewPatientPhotoAlbumFragment extends Fragment {

    private LinearLayout rootView;
    private LinearLayout addNewPhotosHeaderContainer;
    private ExpandableLayout addNewPhotosExpandable;
    private Button cancelAddNewPhotosButton;
    private Button addnewPhotosButton;
    private ListView photoAlbumListView;
    private MaterialSpinner photoAlbumTitleSpinner;
    private Button uploadAddNewPhotosButton;
    private ImageView uploadImage;

    //will need to edit these 3 parameters to to save the picture directly into database. current in static.
    private String fileName = "new_photo.jpg";
    private String path = fileName;
    private Uri outputFileUri;

    private ArrayList<String> urlToPullImageProfile = new ArrayList<String>();
    private ArrayList<String> urlToPullImageFamily = new ArrayList<String>();
    private ArrayList<String> urlToPullImageFriends = new ArrayList<String>();
    private ArrayList<String> urlToPullImageScenery = new ArrayList<String>();
    private ArrayList<String> urlToPullImageOthers = new ArrayList<String>();
    private ArrayList<String> combinedURLSOfAll = new ArrayList<String>();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbfile db = new dbfile();
        //code to select selected patient Nric.
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        //end of code to select selected patient Nric.

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_photo_album, container, false);
        photoAlbumListView = (ListView) rootView.findViewById(R.id.photo_album_list_view);
        ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();

        uploadImage = (ImageView) rootView.findViewById(R.id.uploading_image);


        Log.v("check patient nric", selectedPatientNric); //test nric.
        combinedURLSOfAll = db.getPicturesURLS(db.getPatientId(selectedPatientNric));

        for (int i = 0; i< combinedURLSOfAll.size(); i++) {
            if (combinedURLSOfAll.get(i).contains("profilePic")) {
                urlToPullImageProfile.add(combinedURLSOfAll.get(i));
            }
            else if (combinedURLSOfAll.get(i).contains("Family")) {
                urlToPullImageFamily.add(combinedURLSOfAll.get(i));
            }
            else if (combinedURLSOfAll.get(i).contains("Friends")) {
                urlToPullImageFriends.add(combinedURLSOfAll.get(i));
            }
            else if (combinedURLSOfAll.get(i).contains("Scenery")) {
                urlToPullImageScenery.add(combinedURLSOfAll.get(i));
            }
            else if (combinedURLSOfAll.get(i).contains("Others")) {
                urlToPullImageOthers.add(combinedURLSOfAll.get(i));
            }
        }

        photoAlbumList.add(new PhotoAlbum("Profile Picture", urlToPullImageProfile));
        photoAlbumList.add(new PhotoAlbum("Family", urlToPullImageFamily));
        photoAlbumList.add(new PhotoAlbum("Friends", urlToPullImageFriends));
        photoAlbumList.add(new PhotoAlbum("Scenery", urlToPullImageScenery));
        photoAlbumList.add(new PhotoAlbum("Others", urlToPullImageOthers));


        photoAlbumListView.setAdapter(new PhotoAlbumTitleAdapter(getActivity(), photoAlbumList));

        photoAlbumTitleSpinner = (MaterialSpinner) rootView.findViewById(R.id.photo_album_title_spinner);
        ArrayAdapter<CharSequence> photoAlbumTitleAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.option_album_title, android.R.layout.simple_spinner_item);
        photoAlbumTitleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        photoAlbumTitleSpinner.setAdapter(photoAlbumTitleAdapter);


        //***********************LISTENERS*****************************
        //ontouch listener for "add New Photos" card view. once touched while open, it will clear fields and close it.
        addNewPhotosExpandable = (ExpandableLayout) rootView.findViewById(R.id.add_new_photo_expandable_layout);
        addNewPhotosHeaderContainer = (LinearLayout) rootView.findViewById(R.id.add_new_photos_header);
        addNewPhotosHeaderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (addNewPhotosExpandable.isOpened()) {
                    resetAddNewPhotosFields();
                    addNewPhotosExpandable.hide();
                }
                return false;
            }
        });

        //'cancel' button listener
        cancelAddNewPhotosButton = (Button) rootView.findViewById(R.id.cancel_add_new_photos_event_button);
        cancelAddNewPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeExpandableAddNewPhotos();
                resetAddNewPhotosFields();
                photoAlbumTitleSpinner.setError(null);
            }
        });

        //'take photo' button listener
        addnewPhotosButton = (Button) rootView.findViewById(R.id.add_new_photos_event_button);
        addnewPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorCheckingAndTakePhoto();
            }

        });

        //'upload photo' button listener
        uploadAddNewPhotosButton = (Button) rootView.findViewById(R.id.upload_new_photo_button);
        uploadAddNewPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "Please take photo first before uploading.", Toast.LENGTH_SHORT);
                if (uploadImage.getDrawable() == null){
                    toast.show();
                }else{
                    //uploadPhoto();
                }
            }

        });
        return rootView;
    }


    private void resetAddNewPhotosFields() {
        photoAlbumTitleSpinner.setSelection(0);
        uploadImage.setImageDrawable(null);
    }

    private void closeExpandableAddNewPhotos() {
        if (addNewPhotosExpandable.isOpened()) {
            addNewPhotosExpandable.hide();
            uploadImage.setImageDrawable(null);
        }
    }

    private void errorCheckingAndTakePhoto() {
        String addPhotosSpinnerText = null;
        if (photoAlbumTitleSpinner.getSelectedItemPosition() != 0) {
            addPhotosSpinnerText = photoAlbumTitleSpinner.getSelectedItem().toString();
        }
        // Input checking to see if album title has been selected a not.
        boolean isValidForm = true;
        String errorMessage = getResources().getString(R.string.error_msg_field_required);

        if (UtilsString.isEmpty(addPhotosSpinnerText)) {
            photoAlbumTitleSpinner.setError(errorMessage);
            isValidForm = false;
        }

        if (isValidForm) {
            dispatchTakePictureIntent(addPhotosSpinnerText);
        }

    }

    private void dispatchTakePictureIntent(String albumCategory) {

        //code to select selected patient Nric.
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        //end of code to select selected patient Nric.

        path = "images/"+ albumCategory+"/"+selectedPatientNric +"_" + fileName;
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
        // start the image capture Intent
        Log.v("launch camera", "yes!");
        Log.v("file url", ":"+path);
        startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data !=null) {

                // successfully captured the image
                // launching upload activity
                Log.v("photo", "taken!");
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                uploadImage.setImageBitmap(photo);
                launchUploadActivity(true);
            } else if (resultCode == Activity.RESULT_CANCELED){
                // failed to capture image
                Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void launchUploadActivity(boolean isImage){
        Log.v("testing", "test:" + path);
        //new UploadFileToServer().execute();
        // outputfileurl path: /storage/emulated/0/PearPCC/new_photo.jpg
        /*
        Intent i = new Intent(MainActivity.this, UploadActivity.class);
        i.putExtra("filePath", outputFileUri.getPath());
        i.putExtra("isImage", isImage);
        startActivity(i);
        */
    }
    /*
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {


        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://dementiafypdb.com/fileUpload.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new RecoverySystem.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
                entity.addPart("website",
                        new StringBody("www.androidhive.info"));
                entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("test:", "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }
    */
    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
