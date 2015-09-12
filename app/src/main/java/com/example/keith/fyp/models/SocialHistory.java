package com.example.keith.fyp.models;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class SocialHistory {
    private String liveWith;
    private String diet;
    private String religion;
    private Boolean isSexuallyActive;
    private Boolean isSecondhandSmoker;
    private String alcoholUse;
    private String caffeineUse;
    private String tobaccoUse;
    private String drugUse;

    public SocialHistory() {

    }

    public String getLiveWith() {
        return liveWith;
    }

    public void setLiveWith(String liveWith) {
        this.liveWith = liveWith;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Boolean getIsSexuallyActive() {
        return isSexuallyActive;
    }

    public void setIsSexuallyActive(Boolean isSexuallyActive) {
        this.isSexuallyActive = isSexuallyActive;
    }

    public Boolean getIsSecondhandSmoker() {
        return isSecondhandSmoker;
    }

    public void setIsSecondhandSmoker(Boolean isSecondhandSmoker) {
        this.isSecondhandSmoker = isSecondhandSmoker;
    }

    public String getAlcoholUse() {
        return alcoholUse;
    }

    public void setAlcoholUse(String alcoholUse) {
        this.alcoholUse = alcoholUse;
    }

    public String getCaffeineUse() {
        return caffeineUse;
    }

    public void setCaffeineUse(String caffeineUse) {
        this.caffeineUse = caffeineUse;
    }

    public String getTobaccoUse() {
        return tobaccoUse;
    }

    public void setTobaccoUse(String tobaccoUse) {
        this.tobaccoUse = tobaccoUse;
    }

    public String getDrugUse() {
        return drugUse;
    }

    public void setDrugUse(String drugUse) {
        this.drugUse = drugUse;
    }
}
