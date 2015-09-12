package com.example.keith.fyp.models;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class Prescription {
    private String name;
    private String dosage;
    private Integer freqPerDay;
    private String instruction;
    private DateTime startDate;
    private DateTime endDate;
    private String beforeAfterMeal;
    private String notes;

    public Prescription(String name, String dosage, Integer freqPerDay, String instruction, DateTime startDate, DateTime endDate, String beforeAfterMeal, String notes) {
        this.name = name;
        this.dosage = dosage;
        this.freqPerDay = freqPerDay;
        this.instruction = instruction;
        this.startDate = startDate;
        this.endDate = endDate;
        this.beforeAfterMeal = beforeAfterMeal;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Integer getFreqPerDay() {
        return freqPerDay;
    }

    public void setFreqPerDay(Integer freqPerDay) {
        this.freqPerDay = freqPerDay;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getBeforeAfterMeal() {
        return beforeAfterMeal;
    }

    public void setBeforeAfterMeal(String beforeAfterMeal) {
        this.beforeAfterMeal = beforeAfterMeal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
