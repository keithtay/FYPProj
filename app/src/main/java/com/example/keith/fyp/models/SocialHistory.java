package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class SocialHistory implements ObjectToAttributeValueTransformer {
    private String liveWith;
    private String diet;
    private String religion;
    private Boolean isSexuallyActive;
    private Boolean isSecondhandSmoker;
    private String alcoholUse;
    private String caffeineUse;
    private String tobaccoUse;
    private String drugUse;

    private String pet;
    private String occupation;
    private String like;
    private String dislike;
    private String hobby;
    private String habbit;
    private String holidayExperience;
    private String education;
    private String exercise;

    /**
     * Default constructor
     */
    public SocialHistory() {}

    /**
     * @return pet owned by the {@link Patient}
     */
    public SocialHistory(String liveWith, String diet, String religion, Boolean isSexuallyActive, Boolean isSecondhandSmoker, String alcoholUse, String caffeineUse, String tobaccoUse
    ,String drugUse, String pet, String occupation, String like, String dislike, String hobby, String habbit, String holidayExperience, String education, String exercise) {
        this.liveWith = liveWith;
        this.diet = diet;
        this.religion = religion;
        this.isSexuallyActive = isSexuallyActive;
        this.isSecondhandSmoker = isSecondhandSmoker;
        this.alcoholUse = alcoholUse;
        this.caffeineUse = caffeineUse;
        this.tobaccoUse = tobaccoUse;
        this.drugUse = drugUse;
        this.pet=pet;
        this.occupation = occupation;
        this.like = like;
        this.dislike = dislike;
        this.hobby = hobby;
        this.habbit = habbit;
        this.holidayExperience = holidayExperience;
        this.education = education;
        this.exercise = exercise;
    }
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();
        list.add(new AttributeValuePair("Live with", getLiveWith()));
        list.add(new AttributeValuePair("Diet", getDiet()));
        list.add(new AttributeValuePair("Religion", getReligion()));
        list.add(new AttributeValuePair("Is Sexually Active", getIsSexuallyActive().toString()));
        list.add(new AttributeValuePair("Is Second Hand Smoker", getIsSecondhandSmoker().toString()));
        list.add(new AttributeValuePair("Alcohol Use", getAlcoholUse()));
        list.add(new AttributeValuePair("Caffine Use", getCaffeineUse()));
        list.add(new AttributeValuePair("Tobacco Use", getTobaccoUse()));
        list.add(new AttributeValuePair("Drug Use", getDrugUse()));
        list.add(new AttributeValuePair("Pet", getPet()));
        list.add(new AttributeValuePair("Occupation", getOccupation()));
        list.add(new AttributeValuePair("Like", getLike()));
        list.add(new AttributeValuePair("Dislike", getDislike()));
        list.add(new AttributeValuePair("Hobby", getHobby()));
        list.add(new AttributeValuePair("Habbit", getHabbit()));
        list.add(new AttributeValuePair("Holiday Experience", getHolidayExperience()));
        list.add(new AttributeValuePair("Education", getEducation()));
        list.add(new AttributeValuePair("Exercise", getExercise()));
        return list;
    }
    public String getPet() {
        return pet;
    }

    /**
     * @param pet pet owned by the {@link Patient}
     */
    public void setPet(String pet) {
        this.pet = pet;
    }

    /**
     * @return occupation history by the {@link Patient}
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation occupation history by the {@link Patient}
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return things liked by the {@link Patient}
     */
    public String getLike() {
        return like;
    }

    /**
     * @param like things liked by the {@link Patient}
     */
    public void setLike(String like) {
        this.like = like;
    }

    /**
     * @return things disliked by the {@link Patient}
     */
    public String getDislike() {
        return dislike;
    }

    /**
     * @param dislike things disliked by the {@link Patient}
     */
    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    /**
     * @return hobby of the {@link Patient}
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby hobby of the {@link Patient}
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * @return habbit of the {@link Patient}
     */
    public String getHabbit() {
        return habbit;
    }

    /**
     * @param habbit habbit of the {@link Patient}
     */
    public void setHabbit(String habbit) {
        this.habbit = habbit;
    }

    /**
     * @return holiday experience of the {@link Patient}
     */
    public String getHolidayExperience() {
        return holidayExperience;
    }

    /**
     * @param holidayExperience holiday experience of the {@link Patient}
     */
    public void setHolidayExperience(String holidayExperience) {
        this.holidayExperience = holidayExperience;
    }

    /**
     * @return education history of the {@link Patient}
     */
    public String getEducation() {
        return education;
    }

    /**
     * @param education education history of the {@link Patient}
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * @return exercise usually done by the {@link Patient}
     */
    public String getExercise() {
        return exercise;
    }

    /**
     * @param exercise exercise usually done by the {@link Patient}
     */
    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    /**
     * @return people who lived with the {@link Patient}
     */
    public String getLiveWith() {
        return liveWith;
    }

    /**
     * @param liveWith people who lived with the {@link Patient}
     */
    public void setLiveWith(String liveWith) {
        this.liveWith = liveWith;
    }

    /**
     * @return diet restriction of the {@link Patient}
     */
    public String getDiet() {
        return diet;
    }

    /**
     * @param diet diet restriction of the {@link Patient}
     */
    public void setDiet(String diet) {
        this.diet = diet;
    }

    /**
     * @return religion of the {@link Patient}
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion religion of the {@link Patient}
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return {@code true} if the {@link Patient} is sexually active, otherwise return {@code false}
     */
    public Boolean getIsSexuallyActive() {
        return isSexuallyActive;
    }

    /**
     * @param isSexuallyActive {@code true} if the {@link Patient} is sexually active, otherwise return {@code false}
     */
    public void setIsSexuallyActive(Boolean isSexuallyActive) {
        this.isSexuallyActive = isSexuallyActive;
    }

    /**
     * @return {@code true} if the {@link Patient} is a secondhand smoker, otherwise return {@code false}
     */
    public Boolean getIsSecondhandSmoker() {
        return isSecondhandSmoker;
    }

    /**
     * @param isSecondhandSmoker {@code true} if the {@link Patient} is a secondhand smoker, otherwise return {@code false}
     */
    public void setIsSecondhandSmoker(Boolean isSecondhandSmoker) {
        this.isSecondhandSmoker = isSecondhandSmoker;
    }

    /**
     * @return description of the {@link Patient}'s alcohol use
     */
    public String getAlcoholUse() {
        return alcoholUse;
    }

    /**
     * @param alcoholUse description of the {@link Patient}'s alcohol use
     */
    public void setAlcoholUse(String alcoholUse) {
        this.alcoholUse = alcoholUse;
    }

    /**
     * @return description of the {@link Patient}'s caffeine use
     */
    public String getCaffeineUse() {
        return caffeineUse;
    }

    /**
     * @param caffeineUse description of the {@link Patient}'s caffeine use
     */
    public void setCaffeineUse(String caffeineUse) {
        this.caffeineUse = caffeineUse;
    }

    /**
     * @return description of the {@link Patient}'s tobacco use
     */
    public String getTobaccoUse() {
        return tobaccoUse;
    }

    /**
     * @param tobaccoUse description of the {@link Patient}'s tobacco use
     */
    public void setTobaccoUse(String tobaccoUse) {
        this.tobaccoUse = tobaccoUse;
    }

    /**
     * @return description of the {@link Patient}'s drug use
     */
    public String getDrugUse() {
        return drugUse;
    }

    /**
     * @param drugUse description of the {@link Patient}'s drug use
     */
    public void setDrugUse(String drugUse) {
        this.drugUse = drugUse;
    }
}
