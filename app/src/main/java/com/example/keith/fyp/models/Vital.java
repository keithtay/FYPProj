package com.example.keith.fyp.models;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class Vital {
    private DateTime dateTimeTaken;
    private Boolean isBeforeMeal;
    private Float temperature;
    private Float bloodPressureSystol;
    private Float bloodPressureDiastol;
    private Float height;
    private Float weight;
    private String notes;

    public Vital(DateTime dateTimeTaken, Boolean isBeforeMeal, Float temperature, Float bloodPressureSystol, Float bloodPressureDiastol, Float height, Float weight, String notes) {
        this.dateTimeTaken = dateTimeTaken;
        this.isBeforeMeal = isBeforeMeal;
        this.temperature = temperature;
        this.bloodPressureSystol = bloodPressureSystol;
        this.bloodPressureDiastol = bloodPressureDiastol;
        this.height = height;
        this.weight = weight;
        this.notes = notes;
    }

    public DateTime getDateTimeTaken() {
        return dateTimeTaken;
    }

    public void setDateTimeTaken(DateTime dateTimeTaken) {
        this.dateTimeTaken = dateTimeTaken;
    }

    public Boolean isBeforeMeal() {
        return isBeforeMeal;
    }

    public void setIsBeforeMeal(Boolean isBeforeMeal) {
        this.isBeforeMeal = isBeforeMeal;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getBloodPressureSystol() {
        return bloodPressureSystol;
    }

    public void setBloodPressureSystol(Float bloodPressureSystol) {
        this.bloodPressureSystol = bloodPressureSystol;
    }

    public Float getBloodPressureDiastol() {
        return bloodPressureDiastol;
    }

    public void setBloodPressureDiastol(Float bloodPressureDiastol) {
        this.bloodPressureDiastol = bloodPressureDiastol;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
