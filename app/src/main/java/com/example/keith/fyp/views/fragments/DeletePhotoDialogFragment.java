package com.example.keith.fyp.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;

import java.util.ArrayList;

/**
 * Created by ks on 27/2/2016.
 */
public class DeletePhotoDialogFragment extends DialogFragment {
    ArrayList<String> imageToDelete = new ArrayList<String>();
    dbfile db = new dbfile();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle mArgs = getArguments();
        imageToDelete = mArgs.getStringArrayList("key");
        builder.setTitle("Delete Photo");
        builder.setMessage("Are you sure you want to delete the selected photo?")
                .setPositiveButton(R.string.deletePhotoDialogBoxYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deletePicture(imageToDelete.get(0), getActivity());
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.deletePhotoDialogBoxNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //user cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
