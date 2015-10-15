package com.example.keith.fyp.models;


/**
 * PatientFormSpec is a model to represent a mapping between {@link Patient}'s attribute and <a href="http://developer.android.com/reference/android/view/View.html">View</a>.
 * PatientFormSpec will be use to set {@link Patient}'s attribute whenever the <a href="http://developer.android.com/reference/android/view/View.html">View</a>'s value changed.
 *
 * @author Sutrisno Suryajaya Dwi Putra
 */
public class PatientFormSpec {
    private int viewId;
    private String attributeName;
    private String viewClassName;

    /**
     * Create a new mapping between {@link Patient}'s attribute and <a href="http://developer.android.com/reference/android/view/View.html">View</a> with the specified values
     */
    public PatientFormSpec(int viewId, String attributeName, String viewClassName) {
        this.viewId = viewId;
        this.attributeName = attributeName;
        this.viewClassName = viewClassName;
    }

    /**
     * @return <a href="http://developer.android.com/reference/android/view/View.html">View</a> Id
     */
    public int getViewId() {
        return viewId;
    }

    /**
     * @return {@link Patient} attribute name
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @return <a href="http://developer.android.com/reference/android/view/View.html">View</a> class name
     */
    public String getViewClassName() {
        return viewClassName;
    }
}
