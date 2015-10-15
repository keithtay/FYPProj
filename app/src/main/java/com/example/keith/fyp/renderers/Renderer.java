package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.models.Notification;

/**
 * Renderer is an abstract class to render an object
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public abstract class Renderer {

    protected LayoutInflater inflater;

    /**
     * Create a renderer the specified values.
     */
    public Renderer(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    /**
     * Render the object.
     *
     * @return rendering {@link View} result
     */
    public View render() {
        return null;
    }
}
