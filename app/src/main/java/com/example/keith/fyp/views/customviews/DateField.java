package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.keith.fyp.utils.UtilsUi;

/**
 * Created by Sutrisno on 19/9/2015.
 */
public class DateField extends CustomField implements View.OnClickListener {

    private static String TAG = "DateField";

    public DateField(Context context) {
        super(context);
    }

    public DateField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

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

    public void collapse() {
        super.collapse();

        fieldValueEditText.setOnClickListener(null);
    }

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