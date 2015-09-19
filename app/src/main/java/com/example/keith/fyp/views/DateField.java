package com.example.keith.fyp.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;

import org.joda.time.DateTime;

import java.util.Calendar;

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