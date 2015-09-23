package com.example.keith.fyp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.R;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class NotificationRenderer extends Renderer {

    private BackgroundRenderer backgroundRenderer;
    private HeaderRenderer headerRenderer;
    private ContentRenderer contentRenderer;
    private ActionRenderer actionRenderer;

    public NotificationRenderer(LayoutInflater inflater, BackgroundRenderer backgroundRenderer, HeaderRenderer headerRenderer, ContentRenderer contentRenderer, ActionRenderer actionRenderer) {
        super(inflater);
        this.backgroundRenderer = backgroundRenderer;
        this.headerRenderer = headerRenderer;
        this.contentRenderer = contentRenderer;
        this.actionRenderer = actionRenderer;
    }

    @Override
    public View render() {
        View background = backgroundRenderer.render();
        ViewGroup contentContainer = (ViewGroup) background.findViewById(R.id.notification_detail_container);

        View header = headerRenderer.render();
        contentContainer.addView(header);

        View content = contentRenderer.render();
        contentContainer.addView(content);

        return background;
    }

    public BackgroundRenderer getBackgroundRenderer() {
        return backgroundRenderer;
    }

    public void setBackgroundRenderer(BackgroundRenderer backgroundRenderer) {
        this.backgroundRenderer = backgroundRenderer;
    }

    public HeaderRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    public void setHeaderRenderer(HeaderRenderer headerRenderer) {
        this.headerRenderer = headerRenderer;
    }

    public ContentRenderer getContentRenderer() {
        return contentRenderer;
    }

    public void setContentRenderer(ContentRenderer contentRenderer) {
        this.contentRenderer = contentRenderer;
    }

    public ActionRenderer getActionRenderer() {
        return actionRenderer;
    }

    public void setActionRenderer(ActionRenderer actionRenderer) {
        this.actionRenderer = actionRenderer;
    }
}
