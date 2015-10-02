package com.example.keith.fyp.models;

/**
 * Created by Sutrisno on 2/10/2015.
 */
public class TextTooltipPair {
    private String text;
    private String tooltip;

    public TextTooltipPair(String text) {
        this.text = text;
    }

    public TextTooltipPair(String text, String tooltip) {
        this.text = text;
        this.tooltip = tooltip;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}
