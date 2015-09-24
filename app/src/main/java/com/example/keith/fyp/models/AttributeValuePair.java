package com.example.keith.fyp.models;

/**
 * Created by Sutrisno on 24/9/2015.
 */
public class AttributeValuePair {

    private String attributeName;
    private String attributeValue;

    public AttributeValuePair(String attributeName, String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
