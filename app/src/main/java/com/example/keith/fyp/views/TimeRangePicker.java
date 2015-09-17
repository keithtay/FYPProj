package com.example.keith.fyp.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.keith.fyp.views.fragments.TimeRangePickerFragment;

import org.joda.time.DateTime;

import java.util.Date;

public class TimeRangePicker {

    private CharSequence mDialogTitle;
    private DateTime mInitStartTime;
    private DateTime mInitEndTime;
    private TimeRangePickerFragment.OnTimeRangeSetListener mOnTimeRangeSetListener;
    private FragmentManager mFragmentManager;

    /**
     * Private constructor that can only be access using the make method
     * @param dialogTitle Title of the Date Time Picker Dialog
     * @param initStartTime Date object to use to set the initial Date and Time
     * @param onTimeRangeSetListener OnTimeRangeSetListener interface
     * @param fragmentManager Fragment Manager from the activity
     */
    private TimeRangePicker(CharSequence dialogTitle, DateTime initStartTime, DateTime initEndTime,
                            TimeRangePickerFragment.OnTimeRangeSetListener onTimeRangeSetListener,
                            FragmentManager fragmentManager) {

        // Find if there are any DialogFragments from the FragmentManager
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        Fragment mTimeRangeDialogFrag = fragmentManager.findFragmentByTag(
                TimeRangePickerFragment.TAG_FRAG_TIME_RANGE
        );

        // Remove it if found
        if(mTimeRangeDialogFrag != null) {
            mFragmentTransaction.remove(mTimeRangeDialogFrag);
        }
        mFragmentTransaction.addToBackStack(null);

        mDialogTitle = dialogTitle;
        mInitStartTime = initStartTime;
        mInitEndTime = initEndTime;
        mOnTimeRangeSetListener = onTimeRangeSetListener;
        mFragmentManager = fragmentManager;
    }

    /**
     * Creates a new instance of the TimeRangePicker
     * @param dialogTitle Title of the Date Time Picker Dialog
     * @param initStartTime Date object to use to set the initial Date and Time
     * @param onTimeRangeSetListener OnTimeRangeSetListener interface
     * @param fragmentManager Fragment Manager from the activity
     * @return Returns a TimeRangePicker object
     */
    public static TimeRangePicker make(CharSequence dialogTitle, DateTime initStartTime, DateTime initEndTime,
                                            TimeRangePickerFragment.OnTimeRangeSetListener onTimeRangeSetListener,
                                            FragmentManager fragmentManager) {
        return new TimeRangePicker(dialogTitle, initStartTime, initEndTime,
                onTimeRangeSetListener, fragmentManager);
    }

    /**
     * Shows the TimeRangePickerFragment Dialog
     */
    public void show() {
        // Create new TimeRangePickerFragment
        TimeRangePickerFragment mTimeRangePickerFragment = TimeRangePickerFragment.newInstance(mDialogTitle, mInitStartTime, mInitEndTime);
        mTimeRangePickerFragment.setOnTimeRangeSetListener(mOnTimeRangeSetListener);
        mTimeRangePickerFragment.show(mFragmentManager, TimeRangePickerFragment.TAG_FRAG_TIME_RANGE);
    }
}
