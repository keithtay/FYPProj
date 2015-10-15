package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.renderers.NotificationRenderer;
import com.example.keith.fyp.utils.NotificationToRendererConverter;

import java.util.Collections;
import java.util.List;

/**
 * An {@link BaseAdapter} to display notification detail list with the same affected patient
 */
public class NotificationDetailListAdapter extends BaseAdapter {

    private List<Notification> notificationList = Collections.emptyList();
    private Context context;

    /**
     * Create an notification detail list adapter with the specified values
     *
     * @param context context of the application
     */
    public NotificationDetailListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Create an notification detail list adapter with the specified values
     *
     * @param context context of the application
     * @param notificationList list of notification to be displayed
     */
    public NotificationDetailListAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Notification getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Notification notification = getItem(position);
        NotificationRenderer renderer = NotificationToRendererConverter.convert(context, notification);
        View notificationItemView = renderer.render();

        return notificationItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
