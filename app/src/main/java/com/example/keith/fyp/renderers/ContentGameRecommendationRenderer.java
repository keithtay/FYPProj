package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.R;

/**
 * ContentGameRecommendationRenderer is {@link Renderer} to render the content of a {@link com.example.keith.fyp.models.Notification} with type {@link com.example.keith.fyp.models.Notification#TYPE_GAME_RECOMMENDATION}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ContentGameRecommendationRenderer extends ContentRenderer {

    /**
     * Create a content game recommendation renderer with the specified values.
     */
    public ContentGameRecommendationRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.empty_layout, null);

        return rootView;
    }
}
