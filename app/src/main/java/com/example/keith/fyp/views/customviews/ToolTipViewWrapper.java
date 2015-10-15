package com.example.keith.fyp.views.customviews;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;

/**
 * A wrapper for {@link ToolTipView}
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class ToolTipViewWrapper {
    private ToolTipView toolTipView;

    /**
     * Create a tooltip view wrapper
     */
    public ToolTipViewWrapper() {
        toolTipView = null;
    }

    /*
     * @return tooltip view
     */
    public ToolTipView getToolTipView() {
        return toolTipView;
    }

    /**
     * @param toolTipView tooltip view to set
     */
    public void setToolTipView(ToolTipView toolTipView) {
        this.toolTipView = toolTipView;
    }
}
