package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Sutrisno on 19/9/2015.
 */
public class SpinnerField extends CustomField implements View.OnClickListener {

    private static String TAG = "SpinnerField";

    private String[] spinnerItems;
    private OnSpinnerFieldItemSelectedListener onSpinnerFieldItemSelectedListener;

    public SpinnerField(Context context) {
        super(context);
    }

    public SpinnerField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerField(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if(spinnerItems.length <= 0) {
            Log.d(TAG, "Please set spinner items beforehand");
        } else {
            AlertDialog.Builder spinnerDialog = new AlertDialog.Builder(getContext());
            spinnerDialog.setItems(spinnerItems, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int index) {
                    dialog.dismiss();
                    if(onSpinnerFieldItemSelectedListener == null) {
                        Log.d(TAG, "Please set OnSpinnerFieldItemSelectedListener beforehand");
                    } else {
                        onSpinnerFieldItemSelectedListener.onSpinnerFieldItemSelected(index);
                    }
                }

            });
            spinnerDialog.show();
        }
    }

    public void setSpinnerItems(String[] stringArray) {
        this.spinnerItems = stringArray;
    }

    public void setSpinnerFieldItemSelectedListener(OnSpinnerFieldItemSelectedListener onSpinnerFieldItemSelectedListener) {
        this.onSpinnerFieldItemSelectedListener = onSpinnerFieldItemSelectedListener;
    }

    public interface OnSpinnerFieldItemSelectedListener {
        public void onSpinnerFieldItemSelected(int index);
    }
}