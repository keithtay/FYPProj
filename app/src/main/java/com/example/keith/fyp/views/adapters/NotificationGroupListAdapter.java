package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.NotificationGroup;

import java.util.Collections;
import java.util.List;

/**
 * An {@link BaseAdapter} to display notification group list with the same affected patient
 */
public class NotificationGroupListAdapter extends BaseAdapter {

    private List<NotificationGroup> notificationGroupList = Collections.emptyList();
    private Context context;

    /**
     * Create an notification group list adapter with the specified values
     *
     * @param context context of the application
     */
    public NotificationGroupListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Create an notification group list adapter with the specified values
     *
     * @param context context of the application
     * @param notificationGroupList list of notification group to be displayed
     */
    public NotificationGroupListAdapter(Context context, List<NotificationGroup> notificationGroupList) {
        this.context = context;
        this.notificationGroupList = notificationGroupList;
    }

    @Override
    public int getCount() {
        return notificationGroupList.size();
    }

    @Override
    public NotificationGroup getItem(int position) {
        return notificationGroupList.get(position);
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

        final NotificationGroup notificationGroup = getItem(position);

        if (notificationGroup != null) {
            ImageView patientPhotoImageView = (ImageView) notificationItemView.findViewById(R.id.notification_patient_photo_image_view);
            TextView patientNameTextView = (TextView) notificationItemView.findViewById(R.id.notification_patient_name_text_view);
            TextView summaryTextView = (TextView) notificationItemView.findViewById(R.id.notification_summary_text_view);

            if(notificationGroup.getStatus() == NotificationGroup.STATUS_ALL_PROCESSED) {
                patientNameTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                patientNameTextView.setTextColor(context.getResources().getColor(R.color.text_color_default));
            } else {
                patientNameTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                patientNameTextView.setTextColor(context.getResources().getColor(R.color.light_green_800));
            }

            if (patientPhotoImageView != null) {
                patientPhotoImageView.setImageBitmap(notificationGroup.getAffectedPatient().getPhoto());
            }

            if (patientNameTextView != null) {
                patientNameTextView.setText(notificationGroup.getAffectedPatient().getFullName());
            }

            if (summaryTextView != null) {
                summaryTextView.setText(notificationGroup.getSummary());
            }
        }
        return notificationItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}