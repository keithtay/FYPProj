package com.example.keith.fyp.models;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 20/9/2015.
 */
public class ProblemLog {
    private DateTime creationDate;
    private String category;
    private String notes;

    public ProblemLog(DateTime creationDate, String category, String notes) {
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
}
