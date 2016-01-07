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
import android.widget.ImageView;
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
    static final int REQUEST_TAKE_PHOTO = 1;

    //will need to edit these 3 parameters to to save the picture directly into database. current in static.
    private String fileName = "new_photo.jpg";
    private String path = Global.DATA_PATH + fileName;
    private Uri outputFileUri;
    /*
    private ArrayList<Bitmap> pulledUrlImageProfilePic = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageFamily = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageScenery = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageFriends = new ArrayList<Bitmap>();
    private ArrayList<Bitmap> pulledUrlImageOthers = new ArrayList<Bitmap>();
    */
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

}
