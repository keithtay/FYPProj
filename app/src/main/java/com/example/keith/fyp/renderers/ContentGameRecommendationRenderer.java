package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.R;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ContentGameRecommendationRenderer extends ContentRenderer {
    public ContentGameRecommendationRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.empty_layout, null);

        return rootView;
    }
}
