package com.example.keith.fyp.views.fragments;

import android.content.DialogInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.keith.fyp.R;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;

public class TimeRangePickerFragment extends DialogFragment {
    public static final String TAG_FRAG_TIME_RANGE = "fragTimeRange";
    private static final String KEY_DIALOG_TITLE = "dialogTitle";
    private static final String KEY_INIT_START_TIME = "initStartTime";
    private static final String KEY_INIT_END_TIME = "initEndTime";
    private static final String TAG_START_TIME = "startTime";
    private static final String TAG_END_TIME = "endTime";
    private Context mContext;
    private ButtonClickListener mButtonClickListener;
    private OnTimeRangeSetListener mOnTimeRangeSetListener;
    private Bundle mArgument;
    private TimePicker mStartTimePicker;
    private TimePicker mEndTimePicker;

    // DialogFragment constructor must be empty
    public TimeRangePickerFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mButtonClickListener = new ButtonClickListener();
    }

    /**
     * @param dialogTitle Title of the TimeRangePickerFragment DialogFragment
     * @param startTime    Initial Date and Time set to the Date and Time Picker
     * @return Instance of the TimeRangePickerFragment DialogFragment
     */
    public static TimeRangePickerFragment newInstance(CharSequence dialogTitle, DateTime startTime, DateTime endTime) {
        // Create a new instance of TimeRangePickerFragment
        TimeRangePickerFragment mTimeRangePickerFragment = new TimeRangePickerFragment();
        // Setup the constructor parameters as arguments
        Bundle mBundle = new Bundle();
        mBundle.putCharSequence(KEY_DIALOG_TITLE, dialogTitle);
        mBundle.putSerializable(KEY_INIT_START_TIME, startTime);
        mBundle.putSerializable(KEY_INIT_END_TIME, endTime);
        mTimeRangePickerFragment.setArguments(mBundle);
        // Return instance with arguments
        return mTimeRangePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Retrieve Argument passed to the constructor
        mArgument = getArguments();
        // Use an AlertDialog Builder to initially create the Dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        // Setup the Dialog
        mBuilder.setTitle(mArgument.getCharSequence(KEY_DIALOG_TITLE));
        mBuilder.setNegativeButton(android.R.string.no, mButtonClickListener);
        mBuilder.setPositiveButton(android.R.string.yes, mButtonClickListener);
        // Create the Alert Dialog
        AlertDialog mDialog = mBuilder.create();
        // Set the View to the Dialog
        mDialog.setView(
                createDateTimeView(mDialog.getLayoutInflater())
        );
        // Return the Dialog created
        return mDialog;
    }

    /**
     * Inflates the XML Layout and setups the tabs
     *
     * @param layoutInflater Layout inflater from the Dialog
     * @return Returns a view that will be set to the Dialog
     */
    private View createDateTimeView(LayoutInflater layoutInflater) {
        // Inflate the XML Layout using the inflater from the created Dialog
        View mView = layoutInflater.inflate(R.layout.time_range_picker, null);
        // Extract the TabHost
        TabHost mTabHost = (TabHost) mView.findViewById(R.id.tab_host);
        mTabHost.setup();
        // Create Start Time Tab and add to TabHost
        TabHost.TabSpec mStartTimeTab = mTabHost.newTabSpec(TAG_START_TIME);
        mStartTimeTab.setIndicator(mContext.getString(R.string.tab_start_time));
        mStartTimeTab.setContent(R.id.start_time_content);
        mTabHost.addTab(mStartTimeTab);
        // Create End Time Tab and add to TabHost
        TabHost.TabSpec mEndTimeTab = mTabHost.newTabSpec(TAG_END_TIME);
        mEndTimeTab.setIndicator(mContext.getString(R.string.tab_end_time));
        mEndTimeTab.setContent(R.id.end_time_content);
        mTabHost.addTab(mEndTimeTab);
        // Retrieve Date from Arguments sent to the Dialog

        DateTime mStartTime = (DateTime) mArgument.getSerializable(KEY_INIT_START_TIME);
        DateTime mEndTime = (DateTime) mArgument.getSerializable(KEY_INIT_END_TIME);
        final Duration duration = new Duration(mStartTime, mEndTime);

        // Initialize Date and Time Pickers
        mStartTimePicker = (TimePicker) mView.findViewById(R.id.start_time_picker);
        mStartTimePicker.setCurrentHour(mStartTime.getHourOfDay());
        mStartTimePicker.setCurrentMinute(mStartTime.getMinuteOfHour());
        mStartTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                DateTime now = DateTime.now();

                int endHour = mEndTimePicker.getCurrentHour();
                int endMinute = mEndTimePicker.getCurrentMinute();

                DateTime endTime = now.withHourOfDay(endHour).withMinuteOfHour(endMinute);
                DateTime startTime = now.withHourOfDay(hourOfDay).withMinuteOfHour(minute);

                if (startTime.isAfter(endTime)) {
                    endTime = startTime.withDurationAdded(duration, 1);
                    mEndTimePicker.setCurrentHour(endTime.getHourOfDay());
                    mEndTimePicker.setCurrentMinute(endTime.getMinuteOfHour());
                }
            }
        });

        mEndTimePicker = (TimePicker) mView.findViewById(R.id.end_time_picker);
        mEndTimePicker.setCurrentHour(mEndTime.getHourOfDay());
        mEndTimePicker.setCurrentMinute(mEndTime.getMinuteOfHour());
        mEndTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                DateTime now = DateTime.now();

                int startHour = mStartTimePicker.getCurrentHour();
                int startMinute = mStartTimePicker.getCurrentMinute();

                DateTime startTime = now.withHourOfDay(startHour).withMinuteOfHour(startMinute);
                DateTime endTime = now.withHourOfDay(hourOfDay).withMinuteOfHour(minute);

                if (endTime.isBefore(startTime)) {
                    startTime = endTime.withDurationAdded(duration, -1);
                    mStartTimePicker.setCurrentHour(startTime.getHourOfDay());
                    mStartTimePicker.setCurrentMinute(startTime.getMinuteOfHour());
                }
            }
        });

        // Return created view
        return mView;
    }

    /**
     * Sets the OnTimeRangeSetListener interface
     *
     * @param onTimeRangeSetListener Interface that is used to send the Date and Time
     *                              to the calling object
     */
    public void setOnTimeRangeSetListener(OnTimeRangeSetListener onTimeRangeSetListener) {
        mOnTimeRangeSetListener = onTimeRangeSetListener;
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int result) {
            // Determine if the user selected Ok
            if (DialogInterface.BUTTON_POSITIVE == result) {
                DateTime mStartTime = DateTime.now().withSecondOfMinute(0).withMillisOfSecond(0);
                mStartTime = mStartTime.withHourOfDay(mStartTimePicker.getCurrentHour());
                mStartTime = mStartTime.withMinuteOfHour(mStartTimePicker.getCurrentMinute());

                DateTime mEndTime = DateTime.now().withSecondOfMinute(0).withMillisOfSecond(0);
                mEndTime = mEndTime.withHourOfDay(mEndTimePicker.getCurrentHour());
                mEndTime = mEndTime.withMinuteOfHour(mEndTimePicker.getCurrentMinute());

                if (mStartTime.isAfter(mEndTime) || mStartTime.isEqual(mEndTime)) {
                    Toast.makeText(getActivity(), "Start time should be before the end time", Toast.LENGTH_SHORT).show();
                } else {
                    mOnTimeRangeSetListener.timeRangeSet(mStartTime, mEndTime);
                }
            }
        }
    }

    /**
     * Interface for sending the Date and Time to the calling object
     */
    public interface OnTimeRangeSetListener {
        public void timeRangeSet(DateTime startTime, DateTime endTime);
    }
}