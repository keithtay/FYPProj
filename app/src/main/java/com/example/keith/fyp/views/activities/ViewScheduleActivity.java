package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.comparators.ProblemLogComparator;
import com.example.keith.fyp.models.DrawerAndMiniDrawerPair;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.SimilarProblemListAdapter;
import com.example.keith.fyp.views.customviews.DateField;
import com.example.keith.fyp.views.customviews.SpinnerField;
import com.example.keith.fyp.views.customviews.TextField;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.quentindommerc.superlistview.SuperListview;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import mehdi.sakout.fancybuttons.FancyButton;

public class ViewScheduleActivity extends ScheduleActivity implements Drawer.OnDrawerItemClickListener {

    private FancyButton viewMoreButton;
    private FancyButton logProblemButton;

    private Drawer navDrawer;
    private MiniDrawer miniDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        View contentWrapper = findViewById(R.id.activity_content_container);
        DrawerAndMiniDrawerPair drawerAndMiniDrawerPair = UtilsUi.setNavigationDrawer(this, contentWrapper, this, savedInstanceState);
        this.navDrawer = drawerAndMiniDrawerPair.getDrawer();
        this.miniDrawer = drawerAndMiniDrawerPair.getMiniDrawer();

        viewMoreButton = (FancyButton) findViewById(R.id.view_more_button);
        viewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewPatientInfoActivity(null);
            }
        });

        logProblemButton = (FancyButton) findViewById(R.id.log_problem_button);
        logProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProblemLogFormDialog();
            }
        });

        eventListContainer = (LinearLayout) findViewById(R.id.event_list_container);
        displaySchedule();
    }

    private void openViewPatientInfoActivity(Bundle bundle) {
        Intent intent = new Intent(ViewScheduleActivity.this, ViewPatientActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(Global.STATE_SELECTED_PATIENT_NRIC, selectedPatientNric);
        startActivity(intent);
    }

    private void openProblemLogFormDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(ViewScheduleActivity.this);

        builder.title(R.string.dialog_title_add_new_problem_log);

        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_log_problem, null);

        final DateField fromDateDateField = (DateField) rootView.findViewById(R.id.problem_log_from_date);
        fromDateDateField.setText(DateTime.now().toString(Global.DATE_FORMAT));

        final SpinnerField categorySpinnerField = (SpinnerField) rootView.findViewById(R.id.problem_log_category);
        final TextField notesTextField = (TextField) rootView.findViewById(R.id.problem_log_notes);

        String[] problemLogCategoryArray = getResources().getStringArray(R.array.option_problem_log_category);
        final ArrayList<String> problemLogCategoryList = new ArrayList<String>(Arrays.asList(problemLogCategoryArray));

        SuperListview similarProblemListView = (SuperListview) rootView.findViewById(R.id.similar_problem_list_view);
        final ArrayList<ProblemLog> similarProblemList = new ArrayList<>();
        final SimilarProblemListAdapter similarProblemAdapter = new SimilarProblemListAdapter(this, similarProblemList);
        similarProblemListView.setAdapter(similarProblemAdapter);

        categorySpinnerField.setSpinnerItems(problemLogCategoryArray);
        categorySpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
            @Override
            public void onSpinnerFieldItemSelected(int index) {
                String selectedCategoryStr = problemLogCategoryList.get(index);
                categorySpinnerField.changeDisplayedText(selectedCategoryStr);

                similarProblemList.clear();
                ArrayList<ProblemLog> problemList = viewedPatient.getProblemLogList();
                for (ProblemLog problemLog : problemList) {
                    boolean isSameCategory = problemLog.getCategory().equals(selectedCategoryStr);

                    String selectedDateStr = fromDateDateField.getText();
                    DateTime selectedDate = Global.DATE_FORMAT.parseDateTime(selectedDateStr);
                    int numOfDaysDiff = Days.daysBetween(selectedDate.withTimeAtStartOfDay(), problemLog.getCreationDate().withTimeAtStartOfDay()).getDays();
                    boolean isWithinSevenDays = numOfDaysDiff <= 7;

                    if (isSameCategory && isWithinSevenDays) {
                        similarProblemList.add(problemLog);
                    }

                    ProblemLogComparator comparator = new ProblemLogComparator();
                    Collections.sort(similarProblemList, comparator);

                    similarProblemAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.customView(rootView, false);

        builder.positiveText(R.string.dialog_button_add);
        builder.negativeText(R.string.dialog_button_cancel);

        final MaterialDialog dialog = builder.show();
        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryStr = categorySpinnerField.getText();

                boolean isValidForm = true;
                if (UtilsString.isEmpty(categoryStr)) {
                    categorySpinnerField.setError(ViewScheduleActivity.this.getResources().getString(R.string.error_msg_field_required));
                    isValidForm = false;
                }

                if (isValidForm) {
                    DateTime creationDate = Global.DATE_FORMAT.parseDateTime(fromDateDateField.getText().toString());
                    String category = categoryStr;
                    String notes = notesTextField.getText();
                    ProblemLog newProblemLog = new ProblemLog(UtilsUi.generateUniqueId(), creationDate, category, notes);

                    DataHolder.getViewedPatient().getProblemLogList().add(0, newProblemLog);
                    dialog.dismiss();
                }
            }
        });
    }

    public void openEditScheduleActivity(View view) {
        Intent intent = new Intent(ViewScheduleActivity.this, EditScheduleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void displaySchedule() {
        boolean isCurrentTimeMarkHasBeenDisplayed = false;

        // Adding event views to the layout
        int lastIndex = eventList.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            Event event = eventList.get(i);

            String eventTitle = event.getTitle();
            String eventDescription = event.getDescription();
            DateTime startTime = event.getStartTime();
            DateTime endTime = event.getEndTime();
            String startTimeStr = startTime.toString(Global.TIME_FORMAT);
            String durationStr = UtilsUi.convertDurationToString(event.getDuration());

            if (!isCurrentTimeMarkHasBeenDisplayed && currentTime.isBefore(startTime)) {
                View currentTimeMarker = getLayoutInflater().inflate(R.layout.current_time_marker, eventListContainer, false);
                if (i == 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, spacingBetweenEventView);
                    currentTimeMarker.setLayoutParams(lp);
                }
                eventListContainer.addView(currentTimeMarker);
                isCurrentTimeMarkHasBeenDisplayed = true;
            }

            int layoutId;
            int marginLeft = (int) getResources().getDimension(R.dimen.event_margin_left);

            boolean isEventCurrentlyOccurring = (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) || currentTime.isEqual(startTime);
            if (!isCurrentTimeMarkHasBeenDisplayed && isEventCurrentlyOccurring) {
                layoutId = R.layout.event_layout_current;
                isCurrentTimeMarkHasBeenDisplayed = true;
                marginLeft = 0;
            } else {
                layoutId = R.layout.notification_detail_content_game_recommendation_layout;
            }

            View eventView = createEventViewWithLayout(layoutId, eventTitle, eventDescription, startTimeStr, durationStr, i, lastIndex, marginLeft);
            eventListContainer.addView(eventView);
        }

        if (!isCurrentTimeMarkHasBeenDisplayed) {
            View currentTimeMarker = getLayoutInflater().inflate(R.layout.current_time_marker, eventListContainer, false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, spacingBetweenEventView, 0, 0);
            currentTimeMarker.setLayoutParams(lp);
            eventListContainer.addView(currentTimeMarker);
        }
    }

    private View createEventViewWithLayout(int layoutId, String eventTitle, String eventDescription, String startTimeStr, String durationStr, int currentIndex, int lastIndex, int marginLeft) {
        View eventView = getLayoutInflater().inflate(layoutId, eventListContainer, false);

        TextView titleTextView = (TextView) eventView.findViewById(R.id.event_title_text_view);
        TextView descriptionTextView = (TextView) eventView.findViewById(R.id.event_description_text_view);
        TextView startTimeTextView = (TextView) eventView.findViewById(R.id.event_start_time_text_view);
        TextView durationTextView = (TextView) eventView.findViewById(R.id.event_duration_text_view);

        titleTextView.setText(eventTitle);
        descriptionTextView.setText(eventDescription);
        startTimeTextView.setText(startTimeStr);
        durationTextView.setText(durationStr);

        if (currentIndex > 0 && currentIndex < lastIndex) {
            LinearLayout.LayoutParams defaultEventLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            defaultEventLayoutParams.setMargins(marginLeft, spacingBetweenEventView, 0, spacingBetweenEventView);
            eventView.setLayoutParams(defaultEventLayoutParams);
        } else if (currentIndex == 0) {
            LinearLayout.LayoutParams firstEventLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            firstEventLayoutParams.setMargins(marginLeft, 0, 0, spacingBetweenEventView);
            eventView.setLayoutParams(firstEventLayoutParams);
        } else if (currentIndex == lastIndex) {
            LinearLayout.LayoutParams lastEventLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lastEventLayoutParams.setMargins(marginLeft, spacingBetweenEventView, 0, 0);
            eventView.setLayoutParams(lastEventLayoutParams);
        }

        return eventView;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataHolder.resetViewedPatient();
    }

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        int selectedIdentifier = drawerItem.getIdentifier();

        if (selectedIdentifier != Global.NAVIGATION_PATIENT_LIST_ID) {
            miniDrawer.updateItem(Global.NAVIGATION_PATIENT_LIST_ID);

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(Global.EXTRA_SELECTED_NAVIGATION_ID, selectedIdentifier);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}


