package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * PatientFormSpec is a model to represent a medication prescribed to a {@link Patient}
 *
 * @author Sutrisno Suryajaya Dwi Putra
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

    /**
     * Create a new prescription with the specified values
     */
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

    /**
     * @return medicine's name of the prescription
     */
    public String getName() {
        return name;
    }

    /**
     * @param name medicine's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return dosage per one take of the prescription
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * @param dosage dosage per one take to set
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * @return how many time the medicine need to be consumed in one day
     */
    public Integer getFreqPerDay() {
        return freqPerDay;
    }

    /**
     * @param freqPerDay how many time the medicine need to be consumed in one day
     */
    public void setFreqPerDay(Integer freqPerDay) {
        this.freqPerDay = freqPerDay;
    }

    /**
     * @return instruction to consume the medicine
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * @param instruction instruction to set
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * @return start date to consume the medicine
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate start date to set
     */
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end date to consume the medicine
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate end date to set
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return indicator whether the medicine should be consumed before or after the meal
     */
    public String getBeforeAfterMeal() {
        return beforeAfterMeal;
    }

    /**
     * @param beforeAfterMeal indicator whether the medicine should be consumed before or after the meal
     */
    public void setBeforeAfterMeal(String beforeAfterMeal) {
        this.beforeAfterMeal = beforeAfterMeal;
    }

    /**
     * @return other information to take notes in consuming the medicine
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes other information to take notes in consuming the medicine
     */
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
