package com.example.keith.fyp.renderers;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.keith.fyp.R;

/**
 * Created by Keith on 25/1/2016.
 */
public class ContentRejectionInfoRenderer extends ContentRenderer {
    private String fieldCategory;
    private String fieldName;


    public ContentRejectionInfoRenderer(LayoutInflater inflater, String fieldCategory, String fieldName) {
        super(inflater);
        this.fieldCategory = fieldCategory;
        this.fieldName = fieldName;

    }
    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.notification_detail_content_rejection_layout, null);

        TextView fieldCategoryTextView = (TextView) rootView.findViewById(R.id.field_category1);
        TextView fieldNameTextView = (TextView) rootView.findViewById(R.id.field_name1);

        fieldCategoryTextView.setText(fieldCategory);
        fieldNameTextView.setText(fieldName);

        return rootView;
    }
}
