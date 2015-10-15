package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * ProblemLog is a model to represent a {@link Patient}'s problem log
 *
 * @author Sutrisno Suryajaya Dwi Putra
 */
public class ProblemLog implements ObjectToAttributeValueTransformer {
    private String id;
    private DateTime creationDate;
    private String category;
    private String notes;

    /**
     * Create a new problem log with the specified values
     */
    public ProblemLog(String id, DateTime creationDate, String category, String notes) {
        this.id = id;
        this.creationDate = creationDate;
        this.category = category;
        this.notes = notes;
    }

    /**
     * @return creation date of the problem log
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
     * @return category of the problem log
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
     * @return other information of the problem log
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
        list.add(new AttributeValuePair("Category", getCategory()));
        list.add(new AttributeValuePair("Notes", getNotes()));
        return list;
    }

    /**
     * @return ID of the problem log
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
