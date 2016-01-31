package com.example.keith.fyp.models;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Keith on 29/1/2016.
 */
public class User implements ObjectToAttributeValueTransformer {
    public String firstName;
    public String lastName;
    public String address;
    public String email;
    public String officeNumber;
    public String handphoneNumber;
    public char gender;
    public DateTime dob;
    public String rank;

    public User(String firstName, String lastName, String address, String email, String officeNumber, String handphoneNumber, char gender, DateTime dob, String rank){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.officeNumber = officeNumber;
        this.handphoneNumber = handphoneNumber;
        this.gender = gender;
        this.dob = dob;
        this.rank = rank;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getHandphoneNumber() {
        return handphoneNumber;
    }

    public void setHandphoneNumber(String handphoneNumber) {
        this.handphoneNumber = handphoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public DateTime getDob() {
        return dob;
    }

    public void setDob(DateTime dob) {
        this.dob = dob;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public ArrayList<AttributeValuePair> transform() {
        ArrayList<AttributeValuePair> list = new ArrayList<>();

        list.add(new AttributeValuePair("First Name", getFirstName()));
        list.add(new AttributeValuePair("Last Name", getLastName()));
        list.add(new AttributeValuePair("Address", getAddress()));
        list.add(new AttributeValuePair("Email", getEmail()));
        list.add(new AttributeValuePair("Office Number", getOfficeNumber()));
        list.add(new AttributeValuePair("Handphone Number", getHandphoneNumber()));
        list.add(new AttributeValuePair("Gender", String.valueOf(getGender())));
        list.add(new AttributeValuePair("DOB", getDob().toString()));
        list.add(new AttributeValuePair("Status Type", getRank()));

        return list;
    }
}
