package com.example.keith.fyp.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.views.fragments.HomeScheduleFragment;
import com.example.keith.fyp.views.fragments.NotificationFragment;
import com.example.keith.fyp.views.fragments.PatientListFragment;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sutrisno on 6/9/2015.
 */
public class UtilsUi {

    public static String convertDurationToString(Duration duration) {
        String durationStr = Long.toString(duration.getStandardMinutes()) + " min";
        return durationStr;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void openDatePickerOnEditTextClick(final EditText editText, String title) {
        int mYear;
        int mMonth;
        int mDay;

        String prevSelectedDateStr = editText.getText().toString();

        if (UtilsString.isEmpty(prevSelectedDateStr)) {
            Calendar mcurrentDate = Calendar.getInstance();
            mYear = mcurrentDate.get(Calendar.YEAR);
            mMonth = mcurrentDate.get(Calendar.MONTH);
            mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        } else {
            DateTime date = Global.DATE_FORMAT.parseDateTime(prevSelectedDateStr);
            mYear = date.getYear();
            mMonth = date.getMonthOfYear() - 1;
            mDay = date.getDayOfMonth();
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(editText.getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                DateTime date = DateTime.now();

                date = date.withDayOfMonth(selectedDay);
                date = date.withMonthOfYear(selectedMonth+1);
                date = date.withYear(selectedYear);

                String selectedDateStr = date.toString(Global.DATE_FORMAT);
                editText.setText(selectedDateStr);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setTitle(title);
        datePickerDialog.show();
    }

    public static void setupEditTextToBeDatePicker(final EditText editText, final String title) {
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerOnEditTextClick(editText, title);
            }
        });
    }


    public static void setupEditTextToBeTimePicker(final EditText editText, final String title) {
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHour;
                int mMinutes;

                String prevSelectedTimeStr = editText.getText().toString();

                if (prevSelectedTimeStr == null || prevSelectedTimeStr.isEmpty()) {
                    Calendar mCurrentTime = Calendar.getInstance();
                    mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                    mMinutes = mCurrentTime.get(Calendar.MINUTE);
                } else {
                    DateTime time = Global.TIME_FORMAT.parseDateTime(prevSelectedTimeStr);
                    mHour = time.getHourOfDay();
                    mMinutes = time.getMinuteOfHour();
                }

                TimePickerDialog timePickerDialog = new TimePickerDialog(editText.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DateTime time = DateTime.now();

                        time = time.withHourOfDay(hourOfDay);
                        time = time.withMinuteOfHour(minute);

                        String selectedTimeStr = time.toString(Global.TIME_FORMAT);
                        editText.setText(selectedTimeStr);
                    }
                }, mHour, mMinutes, true);

                timePickerDialog.setTitle(title);
                timePickerDialog.show();
            }
        });
    }

    public static void removeView(View view) {
        ((ViewGroup) view.getParent()).removeView(view);
    }

    public static int countUnacceptedAndUnrejectedNotification() {
        ArrayList<Notification> notificationList = DataHolder.getNotificationList();

        int count = 0;

        for(Notification notification : notificationList) {
            if(notification.getStatus() == Notification.STATUS_NONE) {
                count++;
            }
        }

        return count;
    }

    public static ProblemLog isSimilarProblemLogExist(ProblemLog newProblemLog) {
        ProblemLog similarLog = null;

        String newCategory = newProblemLog.getCategory();
        DateTime newCreationDate = newProblemLog.getCreationDate();

        Patient viewedPatient = DataHolder.getViewedPatient();
        ArrayList<ProblemLog> problemLogList = viewedPatient.getProblemLogList();
        for (ProblemLog problemLog : problemLogList) {
            String currentCategory = problemLog.getCategory();
            if (!currentCategory.equals(newCategory)) {
                continue;
            }

            DateTime compareDate = null;
            DateTime existingToDate = problemLog.getCreationDate();
            if(existingToDate != null) {
                compareDate = existingToDate;
            } else {
                DateTime existingFromDate = problemLog.getCreationDate();
                compareDate = existingFromDate;
            }

            Duration duration = new Duration(compareDate, newCreationDate);
            boolean isBeforeOrEqual = compareDate.isBefore(newCreationDate) || compareDate.isEqual(newCreationDate);
            boolean isBetweenOneDay = duration.getStandardDays() <= 1;
            if (isBetweenOneDay && isBeforeOrEqual) {
                similarLog = problemLog;
                break;
            }
        }

        return similarLog;
    }
}
