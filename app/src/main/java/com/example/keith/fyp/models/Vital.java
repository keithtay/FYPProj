package com.example.keith.fyp.models;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class Vital {
    private DateTime dateTimeTaken;
    private boolean isBeforeMeal;
    private float temperature;
    private float bloodPressureSystol;
    private float bloodPressureDiastol;
    private float height;
    private float weight;
    private String notes;

    public Vital(DateTime dateTimeTaken, boolean isBeforeMeal, float temperature, float bloodPressureSystol, float bloodPressureDiastol, float height, float weight, String notes) {
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

    public boolean isBeforeMeal() {
        return isBeforeMeal;
    }

    public void setIsBeforeMeal(boolean isBeforeMeal) {
        this.isBeforeMeal = isBeforeMeal;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getBloodPressureSystol() {
        return bloodPressureSystol;
    }

    public void setBloodPressureSystol(float bloodPressureSystol) {
        this.bloodPressureSystol = bloodPressureSystol;
    }

    public float getBloodPressureDiastol() {
        return bloodPressureDiastol;
    }

    public void setBloodPressureDiastol(float bloodPressureDiastol) {
        this.bloodPressureDiastol = bloodPressureDiastol;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
