package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.models.Notification;

/**
 * ContentRenderer is {@link Renderer} to render the content of a {@link com.example.keith.fyp.models.Notification}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ContentRenderer extends Renderer {
    /**
     * Notification object to be rendered
     */
    protected Notification notification;

    /**
     * Create a content renderer with the specified values.
     */
    public ContentRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        return null;
    }

    /**
     * @param notification notification to be rendered
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
