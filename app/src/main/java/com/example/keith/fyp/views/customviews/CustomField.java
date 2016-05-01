package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.TransitionDrawable;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
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
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;

/**
 * A custom view to create and editable and expandable input field
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class CustomField extends LinearLayout {
    private static String TAG = "CustomField";

    private LinearLayout rootContainer;
    private TextView fieldTitleTextView;
    protected EditText fieldValueEditText;
    private ImageView editButton;
    private View controlContainer;
    private Button saveButton;
    private Button cancelButton;
    private View topBorder;
    private View bottomBorder;
    private TransitionDrawable backgroundTransition;

    private boolean isExpanded;
    private int controlContainerHeight;
    protected InputMethodManager imm;
    private OnCustomFieldSaveListener onCustomFieldSaveListener;
    protected TypedArray typedArray;

    protected boolean alwaysEditable;
    private String fieldTitleStr;
    private float fieldTitleWidth;
    protected int inputType;
    private boolean showTopBorder;
    private boolean showBottomBorder;
    private float containerPadding;
    private float containerPaddingTop;
    private float containerPaddingRight;
    private float containerPaddingBottom;
    private float containerPaddingLeft;
    private int transitionDuration;
    protected String dialogTitle;
    protected boolean isRequired;

    private String oldValue;

    /**
     * Create a custom field with the specified value
     *
     * @param context context of the application
     */
    public CustomField(Context context) {
        super(context);
        initializeViews(context);
    }

    /**
     * Create a custom field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    public CustomField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    /**
     * Create a custom field with the specified value
     *
     * @param context context of the application
     * @param attrs attributes of the view
     * @param  defStyleAttr style attribute
     */
    public CustomField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    /**
     * Initialize the view by inflating the layout
     *
     * @param context context of the application
     */
    protected void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_custom_field, this);
    }

    /**
     * Initialize the view by inflating the layout and read the attributes
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    protected void initializeViews(Context context, AttributeSet attrs) {
        initializeViews(context);

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomField);

        alwaysEditable = typedArray.getBoolean(R.styleable.CustomField_alwaysEditable, false);

        fieldTitleStr = typedArray.getString(R.styleable.CustomField_fieldTitle);
        if (UtilsString.isEmpty(fieldTitleStr)) {
            fieldTitleStr = "Field Name";
        }

        fieldTitleWidth = typedArray.getDimension(R.styleable.CustomField_fieldTitleWidth, getResources().getDimension(R.dimen.text_field_title_default_width));

        showTopBorder = typedArray.getBoolean(R.styleable.CustomField_showTopBorder, true);
        showBottomBorder = typedArray.getBoolean(R.styleable.CustomField_showBottomBorder, true);

        float defaultPadding = getResources().getDimension(R.dimen.text_field_padding);
        containerPadding = typedArray.getDimension(R.styleable.CustomField_containerPadding, defaultPadding);
        containerPaddingTop = typedArray.getDimension(R.styleable.CustomField_containerPaddingTop, -1);
        containerPaddingRight = typedArray.getDimension(R.styleable.CustomField_containerPaddingRight, -1);
        containerPaddingBottom = typedArray.getDimension(R.styleable.CustomField_containerPaddingBottom, -1);
        containerPaddingLeft = typedArray.getDimension(R.styleable.CustomField_containerPaddingLeft, -1);

        transitionDuration = typedArray.getInt(R.styleable.CustomField_transitionDuration, 350);

        inputType = typedArray.getInt(R.styleable.CustomField_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);

        dialogTitle = typedArray.getString(R.styleable.CustomField_dialogTitle);

        isRequired = typedArray.getBoolean(R.styleable.CustomField_isRequired, false);

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
        if (!showTopBorder) {
            ((ViewGroup) topBorder.getParent()).removeView(topBorder);
        }
        if (!showBottomBorder) {
            ((ViewGroup) bottomBorder.getParent()).removeView(bottomBorder);
        }
        fieldTitleTextView.setWidth((int) fieldTitleWidth);
        int allPadding = (int) containerPadding;
        int leftPadding = containerPaddingLeft >= 0 ? (int) containerPaddingLeft : allPadding;
        int topPadding = containerPaddingTop >= 0 ? (int) containerPaddingTop : allPadding;
        int rightPadding = containerPaddingRight >= 0 ? (int) containerPaddingRight : allPadding;
        int bottomPadding = containerPaddingBottom >= 0 ? (int) containerPaddingBottom : allPadding;
        rootContainer.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        backgroundTransition = (TransitionDrawable) rootContainer.getBackground();

        controlContainerHeight = 110;
        isExpanded = false;

        if (alwaysEditable) {
            UtilsUi.removeView(editButton);
            fieldValueEditText.setBackgroundResource(R.drawable.bottom_border);
        } else {
            enableEditTextField(false);
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
                    checkAndSaveValue();
                }
            });
        }
    }

    /**
     * Check whether the value is required.
     * If yes, it must not be empty to be able to save.
     * If no, save the value
     */
    protected void checkAndSaveValue() {
        if (isRequired) {
            if (UtilsString.isEmpty(getText())) {
                String errorMessage = getResources().getString(R.string.error_msg_field_required);
                fieldValueEditText.setError(errorMessage);
            } else {
                saveValue();
            }
        } else {
            saveValue();
        }
    }

    private void toggleMode() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    /**
     * Enable to input field to be editable
     *
     * @param enable enable or disable
     */
    protected void enableEditTextField(boolean enable) {
        fieldValueEditText.setFocusable(enable);
        fieldValueEditText.setFocusableInTouchMode(enable);
    }

    /**
     * Collapse the view from edit mode
     */
    public void collapse() {
        fieldValueEditText.setBackgroundResource(R.color.transparent);

        editButton.setImageResource(R.drawable.ic_mode_edit_green_24px);

        ExpandHeightAnimation a = new ExpandHeightAnimation(controlContainer, transitionDuration, ExpandHeightAnimation.COLLAPSE);
        controlContainer.startAnimation(a);

        backgroundTransition.reverseTransition(transitionDuration);
        enableEditTextField(false);

        fieldValueEditText.setText(oldValue);

        isExpanded = false;

        fieldValueEditText.setError(null);
    }

    /**
     * Expand the view to be editable
     */
    public void expand() {
        fieldValueEditText.setBackgroundResource(R.drawable.bottom_border);

        editButton.setImageResource(R.drawable.ic_clear_green_24px);

        ExpandHeightAnimation a = new ExpandHeightAnimation(controlContainer, transitionDuration, ExpandHeightAnimation.EXPAND);
        a.setHeight(controlContainerHeight);
        controlContainer.startAnimation(a);

        backgroundTransition.startTransition(transitionDuration);
        enableEditTextField(true);

        isExpanded = true;
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
            if (mType == EXPAND) {
                mLayoutParams.height = 0;
            } else {
                mLayoutParams.height = LayoutParams.WRAP_CONTENT;
            }
        }

        public int getHeight() {
            return mView.getHeight();
        }

        public void setHeight(int height) {
            mEndHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                if (mType == EXPAND) {
                    mLayoutParams.height = (int) (mEndHeight * interpolatedTime);
                } else {
                    mLayoutParams.height = (int) (mEndHeight * (1 - interpolatedTime));
                }
                mView.requestLayout();
            } else {
                if (mType == EXPAND) {
                    mLayoutParams.height = LayoutParams.WRAP_CONTENT;
                    mView.requestLayout();
                }
            }
        }
    }

    public void setFieldTitleWidth(int width) {
        this.fieldTitleTextView.getLayoutParams().width = width;
    }

    /**
     * Save the edited value
     */
    protected void saveValue() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String selectedPatientNric = mPrefs.getString(Global.STATE_SELECTED_PATIENT_NRIC, "");

        Log.v("Testing Testing", selectedPatientNric);
        Log.v("whats this", fieldTitleStr);

        SharedPreferences pref = getContext().getSharedPreferences("Login", 0);
        final int UserID = Integer.parseInt(pref.getString("userid", ""));
        final int UserTypeID = Integer.parseInt(pref.getString("userTypeId", ""));
        dbfile db = new dbfile();
        String newValue="";

        int x = db.getPatientId(selectedPatientNric);
        if (UserTypeID ==3){
            newValue = fieldValueEditText.getText().toString();
            db.SupinsertNewPatientInfo(oldValue, newValue, fieldTitleStr, x, UserTypeID, UserID);
            oldValue = newValue;
        }else{
            newValue = fieldValueEditText.getText().toString();
            db.insertNewPatientInfo(oldValue, newValue, fieldTitleStr, x, UserTypeID, UserID);
            Toast.makeText(getContext(), "Pending Supervisor Approval", Toast.LENGTH_SHORT).show();
        }

        toggleMode();

        if (this.onCustomFieldSaveListener != null) {
            this.onCustomFieldSaveListener.onCustomFieldSave(newValue);
        } else {
            Log.d(TAG, "OnCustomFieldSaveListener should be set");
        }
    }

    /**
     * Set text displayed in the input field
     *
     * @param text text to be displayed
     */
    public void setText(String text) {
        this.fieldValueEditText.setText(text);
        oldValue = text;
    }

    /**
     * @return current text in the input field
     */
    public String getText() {
        return this.fieldValueEditText.getText().toString();
    }

    /**
     * Set text displayed in the input field
     *
     * @param text text to be displayed
     */
    public void changeDisplayedText(String text) {
        this.fieldValueEditText.setText(text);
    }

    /**
     * OnCustomFieldSaveListener is an interface
     * that provide a listener when the custom field is saved
     */
    public interface OnCustomFieldSaveListener {
        void onCustomFieldSave(String newValue);
    }

    /**
     * @param onCustomFieldSaveListener listener when the custom field is saved
     */
    public void setOnCustomFieldSaveListener(OnCustomFieldSaveListener onCustomFieldSaveListener) {
        this.onCustomFieldSaveListener = onCustomFieldSaveListener;
    }

    /**
     * Set an error message to the input field
     *
     * @param errorMsg error message to be displayed
     */
    public void setError(String errorMsg) {
        fieldValueEditText.setError(errorMsg);
    }
}
