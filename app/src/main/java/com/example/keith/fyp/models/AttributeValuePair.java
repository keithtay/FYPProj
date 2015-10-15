package com.example.keith.fyp.models;

/**
 * AttributeValuePair is a model to represent a pair of an object's attribute and its value.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class AttributeValuePair {

    private String attributeName;
    private String attributeValue;

    /**
     * Create a new pair of attribute name and value with the specified {@code attributeName} and {@code attributeValue} values
     */
    public AttributeValuePair(String attributeName, String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    /**
     * @return value of object's attribute
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * @param attributeValue attribute's value to set
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * @return name of object's attribute
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName attribute's name to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
