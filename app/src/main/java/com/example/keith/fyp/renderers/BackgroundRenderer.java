package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.R;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class BackgroundRenderer extends Renderer {
    public BackgroundRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.notification_detail_background_layout, null);
        return rootView;
    }
}
