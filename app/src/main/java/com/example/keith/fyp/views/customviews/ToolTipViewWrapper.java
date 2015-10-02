package com.example.keith.fyp.views.customviews;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;

/**
 * Created by Sutrisno on 2/10/2015.
 */
public class ToolTipViewWrapper {
    private ToolTipView toolTipView;

    public ToolTipViewWrapper() {
        toolTipView = null;
    }

    public ToolTipViewWrapper(ToolTipView toolTipView) {
        this.toolTipView = toolTipView;
    }

    public ToolTipView getToolTipView() {
        return toolTipView;
    }

    public void setToolTipView(ToolTipView toolTipView) {
        this.toolTipView = toolTipView;
    }
}
