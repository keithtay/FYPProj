package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.example.keith.fyp.R;
import com.example.keith.fyp.comparators.ProblemLogComparator;
import com.example.keith.fyp.models.PhotoAlbum;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.PhotoAlbumTitleAdapter;
import com.example.keith.fyp.views.adapters.ProblemLogListAdapter;
import com.example.keith.fyp.views.decorators.SpacesCardItemDecoration;
import com.mikepenz.iconics.utils.Utils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Fragment to display the patient's problem log information
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ViewPatientPhotoAlbumFragment extends Fragment {

    private LinearLayout rootView;
    private LinearLayout addNewProblemLogHeaderContainer;
    private RecyclerView problemLogRecyclerView;
    private LinearLayoutManager layoutManager;
    private ProblemLogListAdapter problemLogListAdapter;
    private Button cancelNewProblemLogButton;
    private Button addNewProblemLogButton;
    private MaterialSpinner newProblemLogCategorySpinner;
    private EditText addProblemLogFromDateEditText;
    private EditText newProblemLogNotesEditText;
    private ExpandableLayout addProblemLogExpandableLayout;
    private Spinner problemLogFilterSpinner;
    private ListView photoAlbumListView;
    private MaterialSpinner photoAlbumTitleSpinner;

    private ArrayList<ProblemLog> problemLogList;
    private ProblemLogComparator problemLogComparator = new ProblemLogComparator();

    private String selectedCategoryFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_view_patient_photo_album, container, false);

        photoAlbumListView = (ListView) rootView.findViewById(R.id.photo_album_list_view);

        ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();

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
        others.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_01));
        others.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_02));
        others.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_03));
        others.add(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_04));

        photoAlbumList.add(new PhotoAlbum("Others", others));

        photoAlbumListView.setAdapter(new PhotoAlbumTitleAdapter(getActivity(), photoAlbumList));

        photoAlbumTitleSpinner = (MaterialSpinner) rootView.findViewById(R.id.photo_album_title_spinner);
        ArrayAdapter<CharSequence> photoAlbumTitleAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.option_album_title, android.R.layout.simple_spinner_item);
        photoAlbumTitleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        photoAlbumTitleSpinner.setAdapter(photoAlbumTitleAdapter);

        return rootView;
    }
}