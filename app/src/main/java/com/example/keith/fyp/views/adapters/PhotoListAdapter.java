package com.example.keith.fyp.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.keith.fyp.R;

import com.example.keith.fyp.views.activities.FullScreenViewActivity;
import com.example.keith.fyp.views.activities.ViewPatientActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ks on 15/12/2015.
 //if not using Picasso, change List<string> to list<bitmap>. change all.
 */
public class PhotoListAdapter extends BaseAdapter{

    private List<String> photoList;
    private Context context;
    ArrayList<String> listOfUrls = new ArrayList<String>();

    public PhotoListAdapter(Context context) {
        this.context = context;
    }

    public PhotoListAdapter(Context context, List<String> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View photoItemView = convertView;

        if (photoItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            photoItemView = vi.inflate(R.layout.photo_album_individual_photo_layout, null);
        }
        /* Required if not using picasso library
        Bitmap photo = (Bitmap) getItem(position);
        */
        if (photoList.get(position) != null) {
            ImageView photoImageView = (ImageView) photoItemView.findViewById(R.id.photo_image_view);
            if(photoImageView != null) {
                if (photoList.get(position).contains("profilePic")){
                    Picasso.with(context.getApplicationContext()).load(photoList.get(position))
                            .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                            .into(photoImageView);
                    //Log.v("no cache","profile");
                } else{
                    Picasso.with(context.getApplicationContext()).load(photoList.get(position)).into(photoImageView);
                    //Log.v("cache", "xcept profile");
                }

                /* Required if not using picasso library
                photoImageView.setImageBitmap(photo);
                */
                photoItemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.v("item position ", ""+ position);
                        int numOfPhotosInAlbum = getCount();
                        Log.v("num_photos ", ""+ numOfPhotosInAlbum);
                        for (int i = 0; i< numOfPhotosInAlbum; i++){
                            listOfUrls.add(photoList.get(i)); //store url string in arraylist string.
                        }

                        Intent intent = new Intent(context.getApplicationContext(),FullScreenViewActivity.class);
                        //Log.v("list_photos", ""+ listOfUrls);
                        intent.putStringArrayListExtra("urlPath", listOfUrls);
                        intent.putExtra("position", position);
                        intent.putExtra("numOfObjects",numOfPhotosInAlbum);
                        context.startActivity(intent);
                        return false;
                    }
                });
            }
        }
        /* Required if not using picasso library
        else {
            photo.recycle();
        }
        */
        return photoItemView;

    }
    public void clearArrayListUrls (){
        //clear arraylist to empty when exiting fullscreen mode to prevent duplicates.
        listOfUrls.clear();
        Log.v("clear", "array3" + listOfUrls);
    }
}
