package com.example.keith.fyp.views;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;

/**
 * Created by Sutrisno on 18/9/2015.
 */
public class TextField extends LinearLayout {

    private LinearLayout rootContainer;
    private TextView fieldName;
    private EditText fieldValue;
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
    private int animationDuration = 350;

    public TextField(Context context) {
        super(context);
        initializeViews(context);
    }

    public TextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public TextField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_text_field, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        rootContainer = (LinearLayout) findViewById(R.id.root_container);
        fieldName = (TextView) findViewById(R.id.field_name);
        fieldValue = (EditText) findViewById(R.id.field_value);
        editButton = (ImageView) findViewById(R.id.edit_button);
//        expandableControl = (ExpandableLayout) findViewById(R.id.expandable_control);
        controlContainer = findViewById(R.id.control_container);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        topBorder = findViewById(R.id.top_border);
        bottomBorder = findViewById(R.id.bottom_border);

        backgroundTransition = (TransitionDrawable) rootContainer.getBackground();

        controlContainerHeight =  96;
        isExpanded = false;

        fieldValue.setFocusable(false);
        fieldValue.setFocusableInTouchMode(false);

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
    }

    private void toggleMode() {
        if (isExpanded) {
            editButton.setImageResource(R.drawable.ic_mode_edit_green_24px);

            ExpandHeightAnimation a = new ExpandHeightAnimation(controlContainer, animationDuration, ExpandHeightAnimation.COLLAPSE);
            controlContainer.startAnimation(a);

            backgroundTransition.reverseTransition(animationDuration);
            fieldValue.setFocusable(false);
            fieldValue.setFocusableInTouchMode(false);

            fieldValue.setBackgroundResource(R.color.transparent);
            // TODO: revert back to old value

            // TODO: hide keyboard
        } else {
            editButton.setImageResource(R.drawable.ic_clear_green_24px);

            ExpandHeightAnimation a = new ExpandHeightAnimation(controlContainer, animationDuration, ExpandHeightAnimation.EXPAND);
            a.setHeight(controlContainerHeight);
            controlContainer.startAnimation(a);

            backgroundTransition.startTransition(animationDuration);
            fieldValue.setFocusable(true);
            fieldValue.setFocusableInTouchMode(true);

            fieldValue.setBackgroundResource(R.drawable.bottom_border);
            // TODO: focus on text field

            // TODO: make sure text field can be seen
        }
        isExpanded = !isExpanded;
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
}