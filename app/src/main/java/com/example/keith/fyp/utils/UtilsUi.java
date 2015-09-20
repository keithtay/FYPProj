package com.example.keith.fyp.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.keith.fyp.R;
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

import java.util.Calendar;

/**
 * Created by Sutrisno on 6/9/2015.
 */
public class UtilsUi {

    public static String convertDurationToString(Duration duration) {
        String durationStr = Long.toString(duration.getStandardMinutes()) + " min";
        return durationStr;
    }

    public static Drawer setNavigationDrawer(final Activity activity, Bundle savedInstanceState) {
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
                .withIcon(GoogleMaterial.Icon.gmd_home)
                .withIdentifier(1);
        PrimaryDrawerItem notificationDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_notification)
                .withIcon(GoogleMaterial.Icon.gmd_notifications)
                .withBadge("2")
                .withBadgeStyle(new BadgeStyle(resource.getColor(R.color.red_100),
                        resource.getColor(R.color.red_100)))
                .withIdentifier(2);
        PrimaryDrawerItem accountDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_account)
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withIdentifier(3);
        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_settings)
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(4);

        Drawer navigationDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(false)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        homeDrawerItem,
                        notificationDrawerItem,
                        accountDrawerItem,
                        settingsDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager fragmentManager = activity.getFragmentManager();
                        Fragment fragmentToBeDisplayed = null;

                        switch (drawerItem.getIdentifier()) {
                            case 1:
                                fragmentToBeDisplayed = new PatientListFragment();
                                break;
                            case 2:
                                fragmentToBeDisplayed = new NotificationFragment();
                                break;
                            case 3:
                                fragmentToBeDisplayed = new PatientListFragment();
                                break;
                            case 4:
                                fragmentToBeDisplayed = new PatientListFragment();
                                break;
                        }

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.dashboard_fragment_container, fragmentToBeDisplayed);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .buildView();

        MiniDrawer miniDrawer = new MiniDrawer()
                .withDrawer(navigationDrawer)
                .withInnerShadow(true)
                .withAccountHeader(accountHeader);

        int first = (int) UIUtils.convertDpToPixel(300, activity);
        int second = (int) UIUtils.convertDpToPixel(72, activity);

        View contentWrapper = activity.findViewById(R.id.dashboard_fragment_container);

        Crossfader crossFader = new Crossfader()
                .withContent(contentWrapper)
                .withFirst(navigationDrawer.getSlider(), first)
                .withSecond(miniDrawer.build(activity), second)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));

        return navigationDrawer;
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
}
