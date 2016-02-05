package com.example.keith.fyp.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

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
    //arrayList of all different category.
    private ArrayList<String> urlToPullImageProfile = new ArrayList<String>();
    private ArrayList<String> urlToPullImageFamily = new ArrayList<String>();
    private ArrayList<String> urlToPullImageFriends = new ArrayList<String>();
    private ArrayList<String> urlToPullImageScenery = new ArrayList<String>();
    private ArrayList<String> urlToPullImageOthers = new ArrayList<String>();
    private ArrayList<String> combinedURLSOfAll = new ArrayList<String>();
    int selectedPatientID;
    //variables required for uploading picture to server remotely.
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String SERVER_ADDRESS = "http://dementiafypdb.com/";
    public String SERVER = "http://dementiafypdb.com/fileUpload.php";
    public String timestamp;
    private String fileName = "newPhoto.jpg";
    private String path;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbfile db = new dbfile();

        //code to select selected patient Nric.
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_photo_album, container, false);
        photoAlbumListView = (ListView) rootView.findViewById(R.id.photo_album_list_view);
        ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();

        Log.v("check patient nric", selectedPatientNric); //test nric.
        selectedPatientID = db.getPatientId(selectedPatientNric);
        combinedURLSOfAll = db.getPicturesURLS(selectedPatientID);

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

        photoAlbumList.add(new PhotoAlbum("Myself", urlToPullImageProfile));
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
        return rootView;
    }

    private void dispatchTakePictureIntent(String albumCategory) {
        //code to select selected patient Nric.
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        if (albumCategory.contains("ProfilePicture") ){
            path = "Images/profilePic/"+selectedPatientNric+".jpg"; //create filepath for profile pic database.
        }
        else {
            path = "Images/"+ albumCategory+"/"+selectedPatientNric +"_" + fileName; //create filepath for database.
        }
        Log.v("test",path);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + fileName);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String finalFilePath = "";
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Get our saved file into a bitmap object:
                File file = new File(Environment.getExternalStorageDirectory()+File.separator + fileName);
                Bitmap photo = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
                //get the current timeStamp and store that in the time Variable
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp = tsLong.toString();
                if (path.contains("profilePic")){
                    finalFilePath = path;
                    new UploadImage(photo,path,path).execute();
                }
                else {
                    finalFilePath = path.replace(".jpg","_"+ timestamp +".jpg");
                    nameNewPhoto(finalFilePath, photo);
                }
                Log.v("file url", finalFilePath);
                //new UploadImage(photo, holdFinalFilePath, holdFinalFilePath).execute();

            } else if (resultCode == Activity.RESULT_CANCELED){
                // failed to capture image
                Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UploadImage extends AsyncTask <Void,Void,Void>{
        Bitmap image;
        String name;
        String filePath;
        //ProgressDialog;
        private ProgressDialog dialog = new ProgressDialog(getActivity());
        dbfile db = new dbfile();

        public UploadImage(Bitmap image, String name, String filePath){
            this.image = image;
            this.name = name;
            this.filePath = filePath;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add( new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", name));

            HttpParams httpRequestParams = getHTTPRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "fileUpload.php");

            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);

            }catch (java.net.SocketException e){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Connection Reset By Server")
                        .setMessage("Please check internet connection and try uploading again later.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.v("patientID", "" + selectedPatientID);
            db.insertPicture(filePath, selectedPatientID, getActivity());

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Uploading...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Image Uploaded Successfully.",Toast.LENGTH_SHORT).show();
            //refresh current fragment.
            //refreshCurrentFragment();
            dialog.dismiss();
        }


    }

    private HttpParams getHTTPRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    }

    private void resetAddNewPhotosFields() {
        photoAlbumTitleSpinner.setSelection(0);
    }

    private void closeExpandableAddNewPhotos() {
        if (addNewPhotosExpandable.isOpened()) {
            addNewPhotosExpandable.hide();
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


    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH
        Log.v("entering","");
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    private static Fragment refreshCurrentFragment(){
        // Reload current fragment
        Log.v("reloading frag","");
        Fragment fragmentToBeDisplayed = null;
        fragmentToBeDisplayed = new ViewPatientInfoFormPersonalInfoFragment();
        Log.v("frag changed","");
        return fragmentToBeDisplayed;
    }

    protected void nameNewPhoto(final String defaultPath, final Bitmap photo) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialog_for_naming_new_photos, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText photoName = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String correctedPath = defaultPath;
                        correctedPath = correctedPath.replace(("newPhoto_"+ timestamp), photoName.getText());
                        correctedPath = correctedPath.replace(" ","_");
                        Log.v("see edit", correctedPath);
                        new UploadImage(photo, correctedPath, correctedPath).execute();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        new UploadImage(photo, defaultPath, defaultPath).execute();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
