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
import com.example.keith.fyp.utils.Global;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class NotificationListAdapter extends BaseAdapter {

    private List<Notification> notificationList = Collections.emptyList();
    private Context context;

    public NotificationListAdapter(Context context) {
        this.context = context;
    }

    public NotificationListAdapter(Context context, List<Notification> notificationList) {
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
        View notificationItemView = convertView;

        if (notificationItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            notificationItemView = vi.inflate(R.layout.notification_item_layout, null);
        }

        final Notification notification = getItem(position);

        if (notification != null) {
            ImageView senderPhotoImageView = (ImageView) notificationItemView.findViewById(R.id.notification_sender_photo_image_view);
            TextView senderNameTextView = (TextView) notificationItemView.findViewById(R.id.notification_sender_name_text_view);
            TextView summaryTextView = (TextView) notificationItemView.findViewById(R.id.notification_summary_text_view);
            TextView dateTextView = (TextView) notificationItemView.findViewById(R.id.notification_date_text_view);

            if(notification.getStatus() != Notification.STATUS_NONE) {
                senderNameTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                dateTextView.setTextColor(context.getResources().getColor(R.color.text_color_default));
            }

            if (senderPhotoImageView != null) {
                senderPhotoImageView.setImageBitmap(notification.getSenderPhoto());
            }

            if (senderNameTextView != null) {
                senderNameTextView.setText(notification.getSenderName());
            }

            if (summaryTextView != null) {
                summaryTextView.setText(notification.getSummary());
            }

            if (dateTextView != null) {
                dateTextView.setText(notification.getCreationDate().toString(Global.DATE_FORMAT));
            }
        }
        return notificationItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}