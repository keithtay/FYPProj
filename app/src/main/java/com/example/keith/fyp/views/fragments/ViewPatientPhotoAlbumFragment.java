package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;



import com.andexert.expandablelayout.library.ExpandableLayout;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.PhotoAlbum;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.adapters.PhotoAlbumTitleAdapter;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's photo album fragment
 *
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
    static final int REQUEST_TAKE_PHOTO = 1;

    //will need to edit these 3 parameters to to save the picture directly into database. current in static.
    private String fileName = "new_photo.jpg";
    private String path = Global.DATA_PATH + fileName;
    private Uri outputFileUri;

    private ArrayList<Bitmap> pulledUrlImageProfilePic = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageFamily = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageScenery = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageFriends = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageOthers = new ArrayList<Bitmap>();

    private ArrayList<String> urlToPullImageProfile = new ArrayList<String>();
    private ArrayList<String> urlToPullImageFamily = new ArrayList<String>();
    private ArrayList<String> urlToPullImageFriends = new ArrayList<String>();
    private ArrayList<String> urlToPullImageScenery = new ArrayList<String>();
    private ArrayList<String> urlToPullImageOthers = new ArrayList<String>();
    private ArrayList<String> combinedURLSOfAll = new ArrayList<String>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbfile db = new dbfile();
        boolean familyURLExistCheck = true;
        boolean friendsURLExistCheck = true;
        boolean sceneryURLExistCheck = true;
        boolean othersURLExistCheck = true;

        //code to select selected patient Nric.
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");
        //end of code to select selected patient Nric.


        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_photo_album, container, false);
        photoAlbumListView = (ListView) rootView.findViewById(R.id.photo_album_list_view);
        ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();

        Log.v("check patient nric", selectedPatientNric); //test nric.
        urlToPullImageProfile = db.getPicturesURLS(db.getPatientId(selectedPatientNric), 1);
        urlToPullImageFamily = db.getPicturesURLS(db.getPatientId(selectedPatientNric), 2);
        urlToPullImageFriends = db.getPicturesURLS(db.getPatientId(selectedPatientNric), 3);
        urlToPullImageScenery = db.getPicturesURLS(db.getPatientId(selectedPatientNric), 4);
        urlToPullImageOthers = db.getPicturesURLS(db.getPatientId(selectedPatientNric), 5);
        combinedURLSOfAll.addAll(urlToPullImageProfile); //only 1 profile pic and is mandatory.

        if (urlToPullImageFamily.size() == 0) {
            familyURLExistCheck = turnBoolFalse();
        }
        if (urlToPullImageFriends.size() == 0) {
            friendsURLExistCheck = turnBoolFalse();
        }
        if (urlToPullImageScenery.size() == 0) {
            sceneryURLExistCheck = turnBoolFalse();
        }
        if (urlToPullImageOthers.size() == 0) {
            othersURLExistCheck = turnBoolFalse();
        }

        if (familyURLExistCheck) {
            combinedURLSOfAll.addAll(urlToPullImageFamily);
            Log.v("adding family pics", " ");
        }
        if (friendsURLExistCheck) {
            combinedURLSOfAll.addAll(urlToPullImageFriends);
            Log.v("adding friends pics", " ");
        }
        if (sceneryURLExistCheck) {
            combinedURLSOfAll.addAll(urlToPullImageScenery);
            Log.v("adding scenery pics", " ");
        }
        if (othersURLExistCheck) {
            combinedURLSOfAll.addAll(urlToPullImageOthers);
            Log.v("adding others pics", " ");
        }

        // Create an object for subclass of AsyncTask
        GetXMLTask task = new GetXMLTask();
        // Execute the task
        task.execute(combinedURLSOfAll);

        photoAlbumList.add(new PhotoAlbum("Profile Picture", pulledUrlImageProfilePic));
        photoAlbumList.add(new PhotoAlbum("Family", pulledUrlImageFamily));
        photoAlbumList.add(new PhotoAlbum("Friends", pulledUrlImageFriends));
        photoAlbumList.add(new PhotoAlbum("Scenery", pulledUrlImageScenery));
        photoAlbumList.add(new PhotoAlbum("Others", pulledUrlImageOthers));


        photoAlbumListView.setAdapter(new PhotoAlbumTitleAdapter(getActivity(), photoAlbumList));

        photoAlbumTitleSpinner = (MaterialSpinner) rootView.findViewById(R.id.photo_album_title_spinner);
        ArrayAdapter<CharSequence> photoAlbumTitleAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.option_album_title, android.R.layout.simple_spinner_item);
        photoAlbumTitleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        photoAlbumTitleSpinner.setAdapter(photoAlbumTitleAdapter);


        /////////////////////////////////////listeners////////////////////////////

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
            dispatchTakePictureIntent();
        }

    }

    private void dispatchTakePictureIntent() {
        File file = new File(path);
        outputFileUri = Uri.fromFile(file);

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
    private static boolean turnBoolFalse(){
        return false;
    }

    private class GetXMLTask extends AsyncTask< ArrayList<String>, Void, ArrayList<Bitmap> > {

        @Override
        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... params) {
            ArrayList <Bitmap>  map = new ArrayList<Bitmap>();
            for (int i = 0; i < combinedURLSOfAll.size(); i++) {
                map.add(downloadImage(combinedURLSOfAll.get(i)));
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(ArrayList<Bitmap> result) {
            int numberOfItemsCleared = 0;
            pulledUrlImageProfilePic.add(result.get(0)); //only 1 profile pic and is mandatory.
            numberOfItemsCleared += pulledUrlImageProfilePic.size();

            if (urlToPullImageFamily.size() > 0) {
                for (int i = 0; i < urlToPullImageFamily.size(); i++) {
                    pulledUrlImageFamily.add(result.get(numberOfItemsCleared + i));
                    //Log.v("loading family pics", " ");
                }
            }
            numberOfItemsCleared += urlToPullImageFamily.size();

            if (urlToPullImageFriends.size() > 0) {
                for (int i = 0; i < urlToPullImageFriends.size(); i++) {
                    pulledUrlImageFriends.add(result.get(numberOfItemsCleared + i));
                    //Log.v("loading friends pics", " ");
                }
            }
            numberOfItemsCleared += urlToPullImageFriends.size();

            if (urlToPullImageScenery.size() > 0) {
                for (int i = 0; i < urlToPullImageScenery.size(); i++) {
                    pulledUrlImageScenery.add(result.get(numberOfItemsCleared + i));
                    //Log.v("loading scenery pics", " ");
                }
            }
            numberOfItemsCleared += urlToPullImageScenery.size();

            if (urlToPullImageOthers.size() > 0) {
                for (int i = 0; i < urlToPullImageOthers.size(); i++) {
                    pulledUrlImageOthers.add(result.get(numberOfItemsCleared + i));
                    //Log.v("loading others pics", " ");
                }
            }
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


}
