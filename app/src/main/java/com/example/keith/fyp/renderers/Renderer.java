package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public abstract class Renderer {

    protected LayoutInflater inflater;

    public Renderer(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public View render() {
        return null;
    }
}
