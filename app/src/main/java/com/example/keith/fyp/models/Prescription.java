package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class Prescription implements ObjectToAttributeValueTransformer {
    private String name;
    private String dosage; // Dosage per take
    private Integer freqPerDay; // Time taken per day
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

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();
        list.add(new AttributeValuePair("Name", getName()));
        list.add(new AttributeValuePair("Dosage per take", getDosage()));
        list.add(new AttributeValuePair("Time taken per day", Integer.toString(getFreqPerDay())));
        list.add(new AttributeValuePair("Instruction", getInstruction()));
        list.add(new AttributeValuePair("Start Date", getStartDate().toString(Global.DATE_FORMAT)));
        list.add(new AttributeValuePair("End Date", getEndDate().toString(Global.DATE_FORMAT)));
        list.add(new AttributeValuePair("Before/after meal", getBeforeAfterMeal()));
        list.add(new AttributeValuePair("Notes", getNotes()));
        return list;
    }
}
