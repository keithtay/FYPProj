package com.example.keith.fyp.renderers;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.keith.fyp.R;

/**
 * ContentUpdateInfoFieldRenderer is {@link Renderer} to render the content of a {@link com.example.keith.fyp.models.Notification} with type {@link com.example.keith.fyp.models.Notification#TYPE_UPDATE_INFO_FIELD}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ContentUpdateInfoFieldRenderer extends ContentRenderer {

    private String fieldCategory;
    private String fieldName;
    private String oldValue;
    private String newValue;

    /**
     * Create a content update info field renderer with the specified values.
     */
    public ContentUpdateInfoFieldRenderer(LayoutInflater inflater, String fieldCategory, String fieldName, String oldValue, String newValue) {
        super(inflater);
        this.fieldCategory = fieldCategory;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.notification_detail_content_update_info_field_layout, null);

        TextView fieldCategoryTextView = (TextView) rootView.findViewById(R.id.field_category);
        TextView fieldNameTextView = (TextView) rootView.findViewById(R.id.field_name);
        TextView oldValueTextView = (TextView) rootView.findViewById(R.id.old_value);
        TextView newValueTextView = (TextView) rootView.findViewById(R.id.new_value);

        fieldCategoryTextView.setText(fieldCategory);
        fieldNameTextView.setText(fieldName);
        oldValueTextView.setText(oldValue);
        oldValueTextView.setPaintFlags(oldValueTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        newValueTextView.setText(newValue);

        return rootView;
    }
}
