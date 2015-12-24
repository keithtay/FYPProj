package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.example.keith.fyp.R;
import com.example.keith.fyp.comparators.ProblemLogComparator;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PhotoAlbum;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.PhotoAlbumTitleAdapter;
import com.example.keith.fyp.views.adapters.ProblemLogListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;
import com.mikepenz.iconics.utils.Utils;

import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's photo album fragment
 *
 * @author  ks
 */
public class ViewPatientPhotoAlbumFragment extends Fragment {

    private LinearLayout rootView;
    private LinearLayout addNewPhotosHeaderContainer;
    private ExpandableLayout addNewPhotosExpandable;
    private Button cancelAddNewPhotosButton;
    private Button addnewPhotosButton;
    private ListView photoAlbumListView;
    private MaterialSpinner photoAlbumTitleSpinner;
    private String addPhotosSpinnerText;
    static final int REQUEST_TAKE_PHOTO = 1;

    //will need to edit these 3 parameters to to save the picture directly into database. current in static.
    private String fileName = "new_photo.jpg";
    private String path = Global.DATA_PATH + fileName;
    private Uri outputFileUri;

    ArrayList <Bitmap> pulledUrlImageProfilePic = new ArrayList<Bitmap>();
    ArrayList <Bitmap> pulledUrlImageFamily = new ArrayList<Bitmap>();
    ArrayList <Bitmap> pulledUrlImageScenery = new ArrayList<Bitmap>();
    ArrayList <Bitmap> pulledUrlImageFriends = new ArrayList<Bitmap>();
    ArrayList <Bitmap> pulledUrlImageOthers = new ArrayList<Bitmap>();

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


        ArrayList <String> urlToPullImageProfile = new ArrayList<String>();
        Log.v("test patient nric", selectedPatientNric); //test nric.
        urlToPullImageProfile = db.getProfilePic(db.getPatientId(selectedPatientNric), 1);
        // Create an object for subclass of AsyncTask
        GetXMLTask task_ProfilePic = new GetXMLTask();
        // Execute the task
        for (int i = 0; i < urlToPullImageProfile.size(); i++){
            task_ProfilePic.execute(new String[]{urlToPullImageProfile.get(i)});
        }

        photoAlbumList.add(new PhotoAlbum("Profile Picture", pulledUrlImageProfilePic));

        ArrayList <String> urlToPullImageFamily = new ArrayList<String>();

        Log.v("test patient nric", selectedPatientNric);
        urlToPullImageFamily = db.getProfilePic(db.getPatientId(selectedPatientNric), 1);
        // Create an object for subclass of AsyncTask
        GetXMLTask task_Family = new GetXMLTask();
        // Execute the task
        for (int i = 0; i < urlToPullImageFamily.size(); i++){
            task_Family.execute(new String[]{urlToPullImageFamily.get(i)});
        }

        photoAlbumList.add(new PhotoAlbum("Family", pulledUrlImageFamily));

        ArrayList <String> urlToPullImageScenery = new ArrayList<String>();

        Log.v("test patient nric", selectedPatientNric);
        urlToPullImageScenery = db.getProfilePic(db.getPatientId(selectedPatientNric), 1);
        // Create an object for subclass of AsyncTask
        GetXMLTask task_Scenery = new GetXMLTask();
        // Execute the task
        for (int i = 0; i < urlToPullImageScenery.size(); i++){
            task_Scenery.execute(new String[]{urlToPullImageScenery.get(i)});
        }

        photoAlbumList.add(new PhotoAlbum("Scenery", pulledUrlImageScenery));

        ArrayList <String> urlToPullImageFriends = new ArrayList<String>();

        Log.v("test patient nric", selectedPatientNric);
        urlToPullImageFriends = db.getProfilePic(db.getPatientId(selectedPatientNric), 1);
        // Create an object for subclass of AsyncTask
        GetXMLTask task_Friends = new GetXMLTask();
        // Execute the task
        for (int i = 0; i < urlToPullImageFriends.size(); i++){
            task_Friends.execute(new String[]{urlToPullImageFriends.get(i)});
        }

        photoAlbumList.add(new PhotoAlbum("Friends", pulledUrlImageFriends));

        ArrayList <String> urlToPullImageOthers = new ArrayList<String>();

        Log.v("test patient nric", selectedPatientNric);
        urlToPullImageOthers = db.getProfilePic(db.getPatientId(selectedPatientNric), 1);
        // Create an object for subclass of AsyncTask
        GetXMLTask task_Others = new GetXMLTask();
        // Execute the task
        for (int i = 0; i < urlToPullImageOthers.size(); i++){
            task_Others.execute(new String[]{urlToPullImageOthers.get(i)});
        }

        photoAlbumList.add(new PhotoAlbum("Others", pulledUrlImageOthers));

        /* STATIC IMAGES
        ArrayList<Bitmap> familyBitmapList = new ArrayList<>();

        familyBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01));
        familyBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02));
        familyBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03));
        familyBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_04));

        photoAlbumList.add(new PhotoAlbum("Family", familyBitmapList));

        ArrayList<Bitmap> sceneryBitmapList = new ArrayList<>();
        sceneryBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01));
        sceneryBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02));
        sceneryBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03));
        sceneryBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_04));

        photoAlbumList.add(new PhotoAlbum("Scenery", sceneryBitmapList));


        ArrayList<Bitmap> friendsBitmapList = new ArrayList<>();
        friendsBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01));
        friendsBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02));
        friendsBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03));
        friendsBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_04));

        photoAlbumList.add(new PhotoAlbum("Friends", friendsBitmapList));


        ArrayList<Bitmap> others = new ArrayList<>();


        photoAlbumList.add(new PhotoAlbum("Others", others));
        END OF STATIC IMAGES */

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

    private void errorCheckingAndTakePhoto(){
        String addPhotosSpinnerText = null;
        if(photoAlbumTitleSpinner.getSelectedItemPosition() != 0) {
            addPhotosSpinnerText = photoAlbumTitleSpinner.getSelectedItem().toString();
        }
        // Input checking to see if album title has been selected a not.
        boolean isValidForm = true;
        String errorMessage = getResources().getString(R.string.error_msg_field_required);

        if(UtilsString.isEmpty(addPhotosSpinnerText)) {
            photoAlbumTitleSpinner.setError(errorMessage);
            isValidForm = false;
        }

        if (isValidForm){
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


    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            pulledUrlImageProfilePic.add(result);
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