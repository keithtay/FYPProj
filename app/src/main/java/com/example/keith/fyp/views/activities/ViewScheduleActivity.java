package com.example.keith.fyp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.customviews.DateField;
import com.example.keith.fyp.views.customviews.SpinnerField;
import com.example.keith.fyp.views.customviews.TextField;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;

import mehdi.sakout.fancybuttons.FancyButton;

public class ViewScheduleActivity extends ScheduleActivity {

    private FancyButton viewMoreButton;
    private FancyButton logProblemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        init();

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
        if(bundle != null) {
            intent.putExtras(bundle);
        }
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

        categorySpinnerField.setSpinnerItems(problemLogCategoryArray);
        categorySpinnerField.setSpinnerFieldItemSelectedListener(new SpinnerField.OnSpinnerFieldItemSelectedListener() {
            @Override
            public void onSpinnerFieldItemSelected(int index) {
                String selectedCategoryStr = problemLogCategoryList.get(index);
                categorySpinnerField.changeDisplayedText(selectedCategoryStr);
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
                    ProblemLog newProblemLog = new ProblemLog(creationDate, category, notes);

                    ProblemLog similarLog = UtilsUi.isSimilarProblemLogExist(newProblemLog);

                    if (similarLog != null) {
                        openSimilarProblemLogDialog(newProblemLog, similarLog);
                    } else {
                        DataHolder.getViewedPatient().getProblemLogList().add(0, newProblemLog);
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    private void openSimilarProblemLogDialog(final ProblemLog newProblemLog, ProblemLog similarLog) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);

        DateTime shownDate = similarLog.getCreationDate();
        if(similarLog.getToDate() != null) {
            shownDate = similarLog.getToDate();
        }
        String message = "On " + shownDate.toString(Global.DATE_FORMAT) + " the patient have a similar problem with the " + similarLog.getCategory() + " category. Are you sure you want to add this log?";
        builder.content(message);

        builder.positiveText(R.string.button_add_problem_log_anyway);
        builder.neutralText(R.string.button_view_similar_log);
        builder.negativeText(R.string.button_cancel);

        builder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                DataHolder.getViewedPatient().getProblemLogList().add(0, newProblemLog);
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNegative(dialog);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Global.EXTRA_OPEN_PROBLEM_LOG_TAB, true);
                openViewPatientInfoActivity(bundle);
            }
        });

        builder.show();
    }

    public void openEditScheduleActivity(View view) {
        Intent intent = new Intent(ViewScheduleActivity.this, EditScheduleActivity.class);
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
}


