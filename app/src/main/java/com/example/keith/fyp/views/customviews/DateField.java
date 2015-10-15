package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.keith.fyp.utils.UtilsUi;

/**
 * A custom view to create and editable and expandable date field
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class DateField extends CustomField implements View.OnClickListener {

    private static String TAG = "DateField";

    /**
     * Create a date field with the specified value
     *
     * @param context context of the application
     */
    public DateField(Context context) {
        super(context);
    }

    /**
     * Create a date field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    public DateField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Create a date field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     * @param  defStyleAttr style attribute
     */
    public DateField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(alwaysEditable) {
            enableEditTextField(false);
            fieldValueEditText.setOnClickListener(this);
        }
    }

    /**
     * Collapse the view from edit mode and make the date field unclickable
     */
    public void collapse() {
        super.collapse();

        fieldValueEditText.setOnClickListener(null);
    }

    /**
     * Expand the view to be editable and make the date field
     * open a date picker when it is clicked
     */
    public void expand() {
        super.expand();

        enableEditTextField(false);
        fieldValueEditText.setOnClickListener(this);
        fieldValueEditText.performClick();
    }

    @Override
    public void onClick(View v) {
        UtilsUi.openDatePickerOnEditTextClick(fieldValueEditText, dialogTitle);
    }
}