package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * A custom view to create and editable and expandable text field
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class TextField extends CustomField {

    /**
     * Create a text field with the specified value
     *
     * @param context context of the application
     */
    public TextField(Context context) {
        super(context);
    }

    /**
     * Create a text field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    public TextField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Create a text field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     * @param  defStyleAttr style attribute
     */
    public TextField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        fieldValueEditText.setInputType(inputType);

        fieldValueEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        fieldValueEditText.setImeActionLabel("Save", EditorInfo.IME_ACTION_DONE);
        fieldValueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkAndSaveValue();
                    handled = true;
                }

                return handled;
            }
        });
    }

    /**
     * Collapse the view from edit mode and make the text field uneditable
     */
    public void collapse() {
        super.collapse();
        imm.hideSoftInputFromWindow(fieldValueEditText.getWindowToken(), 0);
    }

    /**
     * Expand the view to be editable and focus the cursor to
     * the text field and open soft-keyboard
     */
    public void expand() {
        super.expand();
        fieldValueEditText.requestFocus();
        imm.showSoftInput(fieldValueEditText, InputMethodManager.SHOW_IMPLICIT);
    }
}