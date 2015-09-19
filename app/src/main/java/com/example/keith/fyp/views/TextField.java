package com.example.keith.fyp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.keith.fyp.R;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class TextField extends CustomField {

    public TextField(Context context) {
        super(context);
    }

    public TextField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_text_field, this);
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
                    saveValue();
                    handled = true;
                }

                return handled;
            }
        });
    }

    public void collapse() {
        super.collapse();
        fieldValueEditText.setBackgroundResource(R.color.transparent);
        imm.hideSoftInputFromWindow(fieldValueEditText.getWindowToken(), 0);
    }

    public void expand() {
        super.expand();
        fieldValueEditText.setBackgroundResource(R.drawable.bottom_border);
        fieldValueEditText.requestFocus();
        imm.showSoftInput(fieldValueEditText, InputMethodManager.SHOW_IMPLICIT);
    }
}