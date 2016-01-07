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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album_photo_fullscreen);
        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        ArrayList <String> urlForFullScreen= i.getStringArrayListExtra("urlPath");
        int pos = i.getIntExtra("position", 0);
        Log.v("test", "xyz " + urlForFullScreen);
        Log.v("test2", "xyz " + pos);

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, urlForFullScreen);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(pos);





    }

}
