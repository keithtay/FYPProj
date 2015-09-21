package com.example.keith.fyp.renderers;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class HeaderRenderer extends Renderer {

    private Bitmap photo;
    private String name;
    private DateTime date;
    private String summary;

    public HeaderRenderer(LayoutInflater inflater, Bitmap photo, String name, DateTime date, String summary) {
        super(inflater);
        this.photo = photo;
        this.name = name;
        this.date = date;
        this.summary = summary;
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.notification_detail_header_layout, null);

        ImageView photoImageView = (ImageView) rootView.findViewById(R.id.notification_sender_photo_image_view);
        TextView dateTextView = (TextView) rootView.findViewById(R.id.notification_date_text_view);
        TextView senderNameTextView = (TextView) rootView.findViewById(R.id.notification_sender_name_text_view);
        TextView summaryTextView = (TextView) rootView.findViewById(R.id.notification_summary_text_view);

        photoImageView.setImageBitmap(photo);
        dateTextView.setText(date.toString(Global.DATE_TIME_FORMAT));
        senderNameTextView.setText(name);
        summaryTextView.setText(summary);

        return rootView;
    }
}
