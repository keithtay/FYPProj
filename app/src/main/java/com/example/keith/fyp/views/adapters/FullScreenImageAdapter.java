package com.example.keith.fyp.views.adapters;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.example.keith.fyp.R;
import com.example.keith.fyp.views.activities.FullScreenViewActivity;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.keith.fyp.views.customviews.TouchImageView;
import com.example.keith.fyp.views.fragments.DeletePhotoDialogFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * Created by ks on 7/1/2016.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> stringArray = new ArrayList<String>();
    private String imageToLoad;


    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    // constructor

    public FullScreenImageAdapter(Activity activity, ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button btnClose;
        Button btnDelete;

        final FullScreenViewActivity fullScreenViewActivity = new FullScreenViewActivity();
        final PhotoListAdapter photoListAdapter = new PhotoListAdapter(context);

        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.view_fullscreen_photo_layout, container, false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        btnDelete = (Button) viewLayout.findViewById(R.id.btnDelete);

        Log.v("check:",""+_imagePaths);

        if (this._imagePaths.get(position).contains("profilePic")) {
            Picasso.with(this._activity).load(this._imagePaths.get(position))
                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imgDisplay); //no cache for profile pic.
            //Log.v("no cache", "profileF");
        } else{
            Picasso.with(this._activity).load(this._imagePaths.get(position)).into(imgDisplay);
            //Log.v("cache", "xcept profileF");
        }

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
                fullScreenViewActivity.clearArrayListItems();
                photoListAdapter.clearArrayListUrls();
            }
        });

        // delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putStringArrayList("key", _imagePaths);
                DeletePhotoDialogFragment startDialog = new DeletePhotoDialogFragment();
                startDialog.setArguments(args);
                startDialog.show(_activity.getFragmentManager(),"Delete Photo");
            }
        });




        ((ViewPager) container).addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
