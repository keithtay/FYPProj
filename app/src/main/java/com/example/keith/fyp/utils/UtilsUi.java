package com.example.keith.fyp.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.models.SocialHistory;
import com.example.keith.fyp.models.Vital;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * UtilsUi is a singleton class to
 * handle various operations to Android user interface
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class UtilsUi {

    // Style of notification count badge (navigation drawer)
    private static BadgeStyle visibleStyle;
    private static BadgeStyle invisibleStyle;

    // Indicate which page of the navigation option is currently displayed
    private static int currentDisplayedFragmentId;

    /**
     * Method the get the styling when navigation's badge is visible
     * @param context context of the application
     * @return navigation's badge style
     */
    public static BadgeStyle getVisibleBadgeStyle(Context context) {
        if(visibleStyle == null) {
            int backgroundColor = context.getResources().getColor(R.color.red_100);
            int textColor = context.getResources().getColor(R.color.text_color_default);
            visibleStyle = new BadgeStyle(backgroundColor, backgroundColor);
            visibleStyle.withTextColor(textColor);
        }
        return visibleStyle;
    }

    /**
     * Method the get the styling when navigation's badge is invisible
     * @param context context of the application
     * @return navigation's badge style
     */
    public static BadgeStyle getInvisibleBadgeStyle(Context context) {
        if(invisibleStyle == null) {
            int transparentColor = context.getResources().getColor(R.color.transparent);
            invisibleStyle = new BadgeStyle(transparentColor, transparentColor);
            invisibleStyle.withTextColor(transparentColor);
        }
        return invisibleStyle;
    }

    /**
     * @return identifier of currently displayed fragment
     */
    public static int getCurrentDisplayedFragmentId() {
        return currentDisplayedFragmentId;
    }

    /**
     * @param currentDisplayedFragmentId identifier of currently displayed fragment
     */
    public static void setCurrentDisplayedFragmentId(int currentDisplayedFragmentId) {
        UtilsUi.currentDisplayedFragmentId = currentDisplayedFragmentId;
    }

    /**
     * Convert a {@link Duration} to string
     *
     * @param duration duration to be converted
     * @return comvertion result
     */
    public static String convertDurationToString(Duration duration) {
        String durationStr = Long.toString(duration.getStandardMinutes()) + " min";
        return durationStr;
    }

    /**
     * Method to set {@link ListView}'s height based on the children's heights.
     *
     * @param listView list view to be set the height
     */
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

    /**
     * Make an {@link EditText} to open a date picker when it is clicked.
     *
     * @param editText the {@link EditText} to be edited
     * @param title title of date picker dialog
     */
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
                date = date.withMonthOfYear(selectedMonth + 1);
                date = date.withYear(selectedYear);

                String selectedDateStr = date.toString(Global.DATE_FORMAT);
                editText.setError(null);
                editText.setText(selectedDateStr);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setTitle(title);
        datePickerDialog.show();
    }

    /**
     * Make an {@link EditText} to be a date picker
     *
     * @param editText the {@link EditText} to be edited
     * @param title title of date picker dialog
     */
    public static void setupEditTextToBeDatePicker(final EditText editText, final String title) {
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerOnEditTextClick(editText, title);
            }
        });
    }

    /**
     * Make an {@link EditText} to be a time picker
     *
     * @param editText the {@link EditText} to be edited
     * @param title title of time picker dialog
     */
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
                        editText.setError(null);
                        editText.setText(selectedTimeStr);
                    }
                }, mHour, mMinutes, true);

                timePickerDialog.setTitle(title);
                timePickerDialog.show();
            }
        });
    }

    /**
     * Remove view from the layout
     *
     * @param view view to be removed
     */
    public static void removeView(View view) {
        ((ViewGroup) view.getParent()).removeView(view);
    }

    /**
     * Method to count the number of {@link NotificationGroup} with status {@link NotificationGroup#STATUS_UNPROCESSED}
     * @return
     */
    public static int countUnprocessedNotificationGroup() {
        ArrayList<NotificationGroup> notificationGroupList = DataHolder.getNotificationGroupList();

        int count = 0;

        for(NotificationGroup notificationGroup : notificationGroupList) {
            if(notificationGroup.getStatus() == NotificationGroup.STATUS_UNPROCESSED) {
                count++;
            }
        }

        return count;
    }

    /**
     * Set the navigation bar to the user interface
     *
     * @param activity activity to be add the navigation bar
     * @param contentWrapper view that wrap the content of the activity
     * @param drawerItemClickListener listener when an item in the navigation drawer is clicked
     * @param savedInstanceState save instance state
     * @return pair of navigation drawer and mini navigation drawer
     */
    public static DrawerAndMiniDrawerPair setNavigationDrawer(Activity activity, View contentWrapper, Drawer.OnDrawerItemClickListener drawerItemClickListener, Bundle savedInstanceState) {
        Resources resource = activity.getResources();
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(resource.getDrawable(R.drawable.avatar1));

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.account_header)
                .withTranslucentStatusBar(false)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_patient_list)
                .withIcon(GoogleMaterial.Icon.gmd_supervisor_account)
                .withIdentifier(Global.NAVIGATION_PATIENT_LIST_ID);
        String notificationCount = Integer.toString(UtilsUi.countUnprocessedNotificationGroup());
        PrimaryDrawerItem notificationDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_notification)
                .withIcon(GoogleMaterial.Icon.gmd_notifications)
                .withBadge(notificationCount)
                .withBadgeStyle(getVisibleBadgeStyle(activity))
                .withIdentifier(Global.NAVIGATION_NOTIFICATION_ID);
        PrimaryDrawerItem accountDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_account)
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withIdentifier(3);
        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_care_center_config)
                .withIcon(FontAwesome.Icon.faw_building)
                .withIdentifier(Global.NAVIGATION_CARE_CENTER_CONFIG_ID);
        PrimaryDrawerItem logoutDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_care_center_config)
                .withIcon(GoogleMaterial.Icon.gmd_lock_outline)
                .withIdentifier(5);
//        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem()
//                .withName(R.string.nav_care_center_config)
//                .withIcon(FontAwesome.Icon.faw_sign_out)
//                .withIdentifier(5);

        Drawer navigationDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(false)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        homeDrawerItem,
                        notificationDrawerItem,
                        accountDrawerItem,
                        settingsDrawerItem,
                        logoutDrawerItem
//                        logoutItem
                )
                .withOnDrawerItemClickListener(drawerItemClickListener)
                .withSavedInstance(savedInstanceState)
                .buildView();

        MiniDrawer miniDrawer = new MiniDrawer()
                .withDrawer(navigationDrawer)
                .withInnerShadow(true)
                .withAccountHeader(accountHeader);

        int first = (int) UIUtils.convertDpToPixel(300, activity);
        int second = (int) UIUtils.convertDpToPixel(72, activity);

        Crossfader crossFader = new Crossfader()
                .withContent(contentWrapper)
                .withFirst(navigationDrawer.getSlider(), first)
                .withSecond(miniDrawer.build(activity), second)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));

        Drawer navDrawer = navigationDrawer;

        return new DrawerAndMiniDrawerPair(navDrawer, miniDrawer);
    }

    /**
     * Set the summary of {@code notificationGroup}
     *
     * @param context context of the application
     * @param notificationGroup notification group to be edited
     */
    public static void setNotificationGroupSummary(Context context, NotificationGroup notificationGroup) {
        int unprocessedCount = notificationGroup.getUnprocessedNotif().size();
        String summary;
        if(unprocessedCount == 0) {
            summary = context.getResources().getString(R.string.notification_group_summary_all_processed);
        } else {
            summary = context.getResources().getString(R.string.notification_group_summary_unprocessed, unprocessedCount);
        }
        notificationGroup.setSummary(summary);
    }

    /**
     * Set the status of {@code notificationGroup}
     *
     * @param context context of the application
     * @param notificationGroup notification group to be edited
     */
    public static void setNotificationGroupStatus(Context context, NotificationGroup notificationGroup) {
        if(notificationGroup.getUnprocessedNotif().size() > 0) {
            notificationGroup.setStatus(NotificationGroup.STATUS_UNPROCESSED);
        } else {
            notificationGroup.setStatus(NotificationGroup.STATUS_ALL_PROCESSED);
        }
    }

    /**
     * Method to generate unique ID
     *
     * @return unique ID
     */
    public static String generateUniqueId() {
        UUID uniqueId = UUID.randomUUID();
        String id = uniqueId.toString().replaceAll("[\\- ]", "");
        return id;
    }
}
