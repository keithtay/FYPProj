package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbfile db = new dbfile();
        Patient createdPatient = DataHolder.getCreatedPatient();

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_photo_album, container, false);

        photoAlbumListView = (ListView) rootView.findViewById(R.id.photo_album_list_view);

        ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();

        //ArrayList<Bitmap> profilePicBitmapList = new ArrayList<>();

        ;
        photoAlbumList.add(new PhotoAlbum("Profile Picture", db.getProfilePic(db.getPatientId(createdPatient.getNric()), 1)));

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

}