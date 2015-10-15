package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Allergy is a model to represent an allergy a {@link Patient} have
*
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class Allergy implements ObjectToAttributeValueTransformer {
    private String name;
    private String reaction;
    private String notes;

    /**
     * Create a new allergy with the specified {@code name}, {@code reaction} and {@code notes} values
     */
    public Allergy(String name, String reaction, String notes) {
        this.name = name;
        this.reaction = reaction;
        this.notes = notes;
    }

    /**
     * @return name of allergy
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return reaction of allergy
     */
    public String getReaction() {
        return reaction;
    }

    /**
     * @param reaction reaction to set
     */
    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    /**
     * @return other allergy's description
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes other allergy's description to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();
        list.add(new AttributeValuePair("Name", getName()));
        list.add(new AttributeValuePair("Reaction", getReaction()));
        list.add(new AttributeValuePair("Notes", getNotes()));
        return list;
    }
}
