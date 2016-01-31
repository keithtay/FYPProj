package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import java.util.ArrayList;

/**
 * Created by Keith on 29/1/2016.
 */
public class Games implements ObjectToAttributeValueTransformer {
    public String name;
    public String comments;
    public int duration;
    public int days;

    public Games(String name, String comments, int duration, int days){
        this.name = name;
        this.comments = comments;
        this.duration = duration;
        this.days= days;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();

        list.add(new AttributeValuePair("Games Category Name", getName()));
        list.add(new AttributeValuePair("Comments", getComments()));
        list.add(new AttributeValuePair("Duration", String.valueOf(getDuration())));
        list.add(new AttributeValuePair("Days", String.valueOf(getDays())));
        return list;
    }
}
