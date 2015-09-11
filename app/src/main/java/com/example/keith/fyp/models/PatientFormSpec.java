package com.example.keith.fyp.models;


import android.widget.ArrayAdapter;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class PatientFormSpec {
    private int viewId;
    private String attributeName;
    private String viewClassName;

    public PatientFormSpec(int viewId, String attributeName, String viewClassName) {
        this.viewId = viewId;
        this.attributeName = attributeName;
        this.viewClassName = viewClassName;
    }

    public int getViewId() {
        return viewId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getViewClassName() {
        return viewClassName;
    }
}
