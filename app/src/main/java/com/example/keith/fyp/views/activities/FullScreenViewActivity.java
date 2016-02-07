package com.example.keith.fyp.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.keith.fyp.R;

import java.util.ArrayList;
import com.example.keith.fyp.views.adapters.FullScreenImageAdapter;

/**
 * Created by ks on 7/1/2016.
 */
public class FullScreenViewActivity extends Activity{

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    ArrayList<String> urlForFullScreen = new ArrayList<String>();
    ArrayList<String> confirmedUrlForFullScreen = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album_photo_fullscreen);
        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        urlForFullScreen= i.getStringArrayListExtra("urlPath");
        int pos = i.getIntExtra("position", 0);
        int numObjects = i.getIntExtra("numOfObjects", 0);
        Log.v("test", "url " + urlForFullScreen);
        //Log.v("item", "pos " + pos);
        Log.v("item", "count " + numObjects);
        //method to remove duplicate pictures for repeated clicking of full screen.
        if (urlForFullScreen.size() > numObjects){
            for (int j =0; j < numObjects; j++){
                confirmedUrlForFullScreen.add(urlForFullScreen.get(j));
                Log.v("edited", "url " + confirmedUrlForFullScreen);
            }
            adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, confirmedUrlForFullScreen);
        } else {
            adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, urlForFullScreen);
        }

        viewPager.setAdapter(adapter);
        // displaying selected image first
        viewPager.setCurrentItem(pos);

    }

    public void clearArrayListItems(){
        //clear all arraylist to empty when exiting fullscreen mode to prevent duplicates.
        confirmedUrlForFullScreen.clear();
        Log.v("clear","array1"+ confirmedUrlForFullScreen);
        urlForFullScreen.clear();
        Log.v("clear","array2"+ urlForFullScreen);
    }

}
