package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * A custom view to create and editable and expandable spinner field
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class SpinnerField extends CustomField implements View.OnClickListener {

    private static String TAG = "SpinnerField";

    private String[] spinnerItems;
    private OnSpinnerFieldItemSelectedListener onSpinnerFieldItemSelectedListener;

    /**
     * Create a spinner field with the specified value
     *
     * @param context context of the application
     */
    public SpinnerField(Context context) {
        super(context);
    }

    /**
     * Create a spinner field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    public SpinnerField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Create a spinner field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     * @param  defStyleAttr style attribute
     */
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

    /**
     * Collapse the view from edit mode and make the spinner field unclickable
     */
    public void collapse() {
        super.collapse();

        fieldValueEditText.setOnClickListener(null);
    }

    /**
     * Expand the view to be editable and make the spinner field
     * open a list dialog when it is clicked
     */
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
                        fieldValueEditText.setError(null);
                    }
                }
            });
            spinnerDialog.show();
        }
    }

    /**
     * Set the list of item to be displayed in the spinner dialog
     *
     * @param stringArray list of item to be displayed
     */
    public void setSpinnerItems(String[] stringArray) {
        this.spinnerItems = stringArray;
    }

    /**
     * @param onSpinnerFieldItemSelectedListener listener when an item in the spinner dialog is selected
     */
    public void setSpinnerFieldItemSelectedListener(OnSpinnerFieldItemSelectedListener onSpinnerFieldItemSelectedListener) {
        this.onSpinnerFieldItemSelectedListener = onSpinnerFieldItemSelectedListener;
    }

    /**
     * OnSpinnerFieldItemSelectedListener is an interface
     * that provide a listener when an item in the spinner dialog is selected
     */
    public interface OnSpinnerFieldItemSelectedListener {
        public void onSpinnerFieldItemSelected(int index);
    }
}