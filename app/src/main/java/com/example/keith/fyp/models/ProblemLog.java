package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 20/9/2015.
 */
public class ProblemLog implements ObjectToAttributeValueTransformer {
    private DateTime creationDate;
    private DateTime toDate;
    private String category;
    private String notes;

    public ProblemLog(DateTime creationDate, String category, String notes) {
        this.creationDate = creationDate;
        this.category = category;
        this.notes = notes;
        this.toDate = null;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();
        list.add(new AttributeValuePair("Category", getCategory()));
        list.add(new AttributeValuePair("Notes", getNotes()));
        return list;
    }

    public DateTime getToDate() {
        return toDate;
    }

    public void setToDate(DateTime toDate) {
        this.toDate = toDate;
    }
}
