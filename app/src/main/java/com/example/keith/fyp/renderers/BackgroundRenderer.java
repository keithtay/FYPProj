package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.R;

/**
 * BackgroundRenderer is {@link Renderer} to render the background of a {@link com.example.keith.fyp.models.Notification}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class BackgroundRenderer extends Renderer {

    /**
     * Create a background renderer with the specified values.
     */
    public BackgroundRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.notification_detail_background_layout, null);
        return rootView;
    }
}
