package com.example.keith.fyp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.UtilsString;

import org.joda.time.DateTime;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class TextField extends LinearLayout {
    private static String TAG = "TextField";

    private LinearLayout rootContainer;
    private TextView fieldTitleTextView;
    private EditText fieldValueEditText;
    private ImageView editButton;
//    private ExpandableLayout expandableControl;
    private View controlContainer;
    private Button saveButton;
    private Button cancelButton;
    private View topBorder;
    private View bottomBorder;
    private TransitionDrawable backgroundTransition;

    private boolean isExpanded;
    private int controlContainerHeight;
    private InputMethodManager imm;
    private OnTextFieldSaveListener onTextFieldSaveListener;

    private boolean alwaysEditable;
    private String fieldTitleStr;
    private boolean showTopBorder;
    private boolean showBottomBorder;
    private float containerPadding;
    private int inputType;
    private int transitionDuration;

    private String oldValue;

    public TextField(Context context) {
        super(context);
        initializeViews(context);
    }

    public TextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public TextField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_text_field, this);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        initializeViews(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextField);

        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.TextField_alwaysEditable:
                    alwaysEditable = typedArray.getBoolean(attr, false);
                    break;
                case R.styleable.TextField_fieldTitle:
                    fieldTitleStr = typedArray.getString(attr);
                    if(UtilsString.isEmpty(fieldTitleStr)) {
                        fieldTitleStr = "Field Name";
                    }
                    break;
                case R.styleable.TextField_showTopBorder:
                    showTopBorder = typedArray.getBoolean(attr, true);
                    break;
                case R.styleable.TextField_showBottomBorder:
                    showBottomBorder = typedArray.getBoolean(attr, true);
                    break;
                case R.styleable.TextField_containerPadding:
                    float defaultPadding = getResources().getDimension(R.dimen.text_field_padding);
                    containerPadding = typedArray.getDimension(attr, defaultPadding);
                    break;
                case R.styleable.TextField_android_inputType:
                    inputType = typedArray.getInt(attr, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
                    break;
                case R.styleable.TextField_transitionDuration:
                    transitionDuration = typedArray.getInt(attr, 350);
                    break;
                default:
                    Log.d(TAG, "Unknown attribute for " + getClass().toString() + ": " + attr);
                    break;
            }
        }

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        rootContainer = (LinearLayout) findViewById(R.id.root_container);
        fieldTitleTextView = (TextView) findViewById(R.id.field_title);
        fieldValueEditText = (EditText) findViewById(R.id.field_value);
        editButton = (ImageView) findViewById(R.id.edit_button);
        controlContainer = findViewById(R.id.control_container);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        topBorder = findViewById(R.id.top_border);
        bottomBorder = findViewById(R.id.bottom_border);

        // Setting the view values
        fieldTitleTextView.setText(fieldTitleStr);
        if(!showTopBorder) {
            ((ViewGroup) topBorder.getParent()).removeView(topBorder);
        }
        if(!showBottomBorder) {
            ((ViewGroup) bottomBorder.getParent()).removeView(bottomBorder);
        }
        int padding = (int) containerPadding;
        rootContainer.setPadding(padding, padding, padding, padding);
        fieldValueEditText.setInputType(inputType);

        backgroundTransition = (TransitionDrawable) rootContainer.getBackground();

        controlContainerHeight =  110;
        isExpanded = false;

        enableEditTextField(false);
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

        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMode();
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMode();
            }
        });

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
            }
        });
    }

    private void toggleMode() {
        if (isExpanded) {
            editButton.setImageResource(R.drawable.ic_mode_edit_green_24px);

            ExpandHeightAnimation a = new ExpandHeightAnimation(controlContainer, transitionDuration, ExpandHeightAnimation.COLLAPSE);
            controlContainer.startAnimation(a);

            backgroundTransition.reverseTransition(transitionDuration);
            enableEditTextField(false);

            fieldValueEditText.setBackgroundResource(R.color.transparent);

            fieldValueEditText.setText(oldValue);
            imm.hideSoftInputFromWindow(fieldValueEditText.getWindowToken(), 0);
        } else {
            editButton.setImageResource(R.drawable.ic_clear_green_24px);

            ExpandHeightAnimation a = new ExpandHeightAnimation(controlContainer, transitionDuration, ExpandHeightAnimation.EXPAND);
            a.setHeight(controlContainerHeight);
            controlContainer.startAnimation(a);

            backgroundTransition.startTransition(transitionDuration);
            enableEditTextField(true);

            fieldValueEditText.setBackgroundResource(R.drawable.bottom_border);

            fieldValueEditText.requestFocus();
            imm.showSoftInput(fieldValueEditText, InputMethodManager.SHOW_IMPLICIT);
        }
        isExpanded = !isExpanded;
    }

    private void enableEditTextField(boolean enable) {
        fieldValueEditText.setFocusable(enable);
        fieldValueEditText.setFocusableInTouchMode(enable);
    }

    private void saveValue() {
        String newValue = fieldValueEditText.getText().toString();
        oldValue = newValue;
        toggleMode();

        if(this.onTextFieldSaveListener != null) {
            this.onTextFieldSaveListener.textFieldSave(newValue);
        } else {
            Log.e(TAG, "OnTextFieldSaveListener should be set");
        }
    }

    public void setOnTextFieldSaveListener(OnTextFieldSaveListener onTextFieldSaveListener) {
        this.onTextFieldSaveListener = onTextFieldSaveListener;
    }

    public class ExpandHeightAnimation extends Animation {

        public final static int COLLAPSE = 1;
        public final static int EXPAND = 0;

        private View mView;
        private int mEndHeight;
        private int mType;
        private LinearLayout.LayoutParams mLayoutParams;

        public ExpandHeightAnimation(View view, int duration, int type) {

            setDuration(duration);
            mView = view;
            mEndHeight = mView.getHeight();
            mLayoutParams = ((LinearLayout.LayoutParams) view.getLayoutParams());
            mType = type;
            if(mType == EXPAND) {
                mLayoutParams.height = 0;
            } else {
                mLayoutParams.height = LayoutParams.WRAP_CONTENT;
            }
        }

        public int getHeight(){
            return mView.getHeight();
        }

        public void setHeight(int height){
            mEndHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                if(mType == EXPAND) {
                    mLayoutParams.height =  (int)(mEndHeight * interpolatedTime);
                } else {
                    mLayoutParams.height = (int) (mEndHeight * (1 - interpolatedTime));
                }
                mView.requestLayout();
            } else {
                if(mType == EXPAND) {
                    mLayoutParams.height = LayoutParams.WRAP_CONTENT;
                    mView.requestLayout();
                }
            }
        }
    }

    public void setText(String text) {
        this.fieldValueEditText.setText(text);
        oldValue = text;
    }

    public String getText() {
        return this.fieldValueEditText.getText().toString();
    }

    public void setFieldTitleWidth(int width) {
        this.fieldTitleTextView.getLayoutParams().width = width;
    }

    public int getFieldTitleWidth() {
        return this.fieldTitleTextView.getLayoutParams().width;
    }

    public interface OnTextFieldSaveListener {
        public void textFieldSave(String newValue);
    }
}