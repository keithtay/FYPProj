package com.example.keith.fyp.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.keith.fyp.R;

/**
 * Created by ks on 7/1/2016.
 */
public class FullScreenViewActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album_photo_fullscreen);

        //viewPager = (ViewPager) findViewById(R.id.pager);

        //utils = new Utils(getApplicationContext());

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        //adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
          //      url);

        //viewPager.setAdapter(adapter);


        // displaying selected image first
        //viewPager.setCurrentItem(position);


    }

}
