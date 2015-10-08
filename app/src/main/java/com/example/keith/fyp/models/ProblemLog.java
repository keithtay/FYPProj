package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 20/9/2015.
 */
public class ProblemLog implements ObjectToAttributeValueTransformer {
    private String id;
    private DateTime creationDate;
    private String category;
    private String notes;

    public ProblemLog(String id, DateTime creationDate, String category, String notes) {
        this.id = id;
        this.creationDate = creationDate;
        this.category = category;
        this.notes = notes;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
