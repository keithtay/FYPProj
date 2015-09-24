package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class NotificationRenderer extends Renderer {

    private BackgroundRenderer backgroundRenderer;
    private HeaderRenderer headerRenderer;
    private ContentRenderer contentRenderer;
    private ActionRenderer actionRenderer;
    private Notification notification;

    public NotificationRenderer(LayoutInflater inflater, BackgroundRenderer backgroundRenderer, HeaderRenderer headerRenderer, ContentRenderer contentRenderer, ActionRenderer actionRenderer, Notification notification) {
        super(inflater);
        this.backgroundRenderer = backgroundRenderer;
        this.headerRenderer = headerRenderer;
        contentRenderer.setNotification(notification);
        this.contentRenderer = contentRenderer;
        actionRenderer.setNotification(notification);
        this.actionRenderer = actionRenderer;
        this.notification = notification;
    }

    @Override
    public View render() {
        View background = backgroundRenderer.render();
        ViewGroup contentContainer = (ViewGroup) background.findViewById(R.id.notification_detail_container);

        View header = headerRenderer.render();
        contentContainer.addView(header);

        View content = contentRenderer.render();
        contentContainer.addView(content);

        View actionFooter = actionRenderer.render();
        contentContainer.addView(actionFooter);

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
