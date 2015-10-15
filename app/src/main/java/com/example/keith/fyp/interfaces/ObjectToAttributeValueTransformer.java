package com.example.keith.fyp.interfaces;

import com.example.keith.fyp.models.AttributeValuePair;

import java.util.ArrayList;

/**
 * ObjectToAttributeValueTransformer is an interface
 * that going to transform an object into a list of {@link AttributeValuePair}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public interface ObjectToAttributeValueTransformer {

    /**
     * A method to transform an object into a list of {@link AttributeValuePair}
     *
     * @return object transformation result, i.e. a list of {@link AttributeValuePair}
     */
    ArrayList<AttributeValuePair> transform();
}
