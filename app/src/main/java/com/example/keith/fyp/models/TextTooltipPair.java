package com.example.keith.fyp.models;

/**
 * AttributeValuePair is a model to represent a pair of text and its description to be displayed in the tooltip.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class TextTooltipPair {
    private String text;
    private String tooltip;

    /**
     * Create a new pair of text and its description with the specified values
     */
    public TextTooltipPair(String text) {
        this.text = text;
    }

    /**
     * Create a new pair of text and its description with the specified values
     */
    public TextTooltipPair(String text, String tooltip) {
        this.text = text;
        this.tooltip = tooltip;
    }

    /**
     * @return text in the pair
     */
    public String getText() {
        return text;
    }

    /**
     * @param text text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return description of the text
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * @param tooltip description of the text text to set
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}
