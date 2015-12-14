package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Photo Album is a model to represent a {@link Patient}'s Photo Album
 *
 * @author ks
 */
public class PhotoAlbum implements ObjectToAttributeValueTransformer {
    private String id;
    private DateTime creationDate;
    private String category;
    private String notes;

    /**
     * Create a new problem log with the specified values
     */
    public PhotoAlbum(String id, DateTime creationDate, String category, String notes) {
        this.id = id;
        //this.creationDate = creationDate;
        //this.category = category;
        this.notes = notes;
    }

    /**
     * @return creation date of the Photo Album
     */
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate creation date to set
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return category of the Photo Album
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return other information of the Photo Album
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();
        list.add(new AttributeValuePair("Album Category", getCategory()));
        list.add(new AttributeValuePair("Caption", getNotes()));
        return list;
    }

    /**
     * @return ID of the Photo Album
     */
    public String getId() {
        return id;
    }

    /**
     * @param id ID to set
     */
    public void setId(String id) {
        this.id = id;
    }
}