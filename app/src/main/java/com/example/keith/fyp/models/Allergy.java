package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 11/9/2015.
 */
public class Allergy implements ObjectToAttributeValueTransformer {
    private String name;
    private String reaction;
    private String notes;

    public Allergy(String name, String reaction, String notes) {
        this.name = name;
        this.reaction = reaction;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
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
        list.add(new AttributeValuePair("Name", getName()));
        list.add(new AttributeValuePair("Reaction", getReaction()));
        list.add(new AttributeValuePair("Notes", getNotes()));
        return list;
    }
}
