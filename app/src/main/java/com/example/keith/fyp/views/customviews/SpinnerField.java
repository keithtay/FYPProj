package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

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
            MaterialDialog.Builder spinnerDialog = new MaterialDialog.Builder(getContext());
            spinnerDialog.items(spinnerItems);
            spinnerDialog.itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                    materialDialog.dismiss();
                    if(onSpinnerFieldItemSelectedListener == null) {
                        Log.d(TAG, "Please set OnSpinnerFieldItemSelectedListener beforehand");
                    } else {
                        onSpinnerFieldItemSelectedListener.onSpinnerFieldItemSelected(i);
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