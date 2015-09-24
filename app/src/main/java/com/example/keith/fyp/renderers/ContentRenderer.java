package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.models.Notification;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ContentRenderer extends Renderer {
    protected Notification notification;

    public ContentRenderer(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public View render() {
        return null;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
