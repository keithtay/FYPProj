package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormRoutineFragment;
import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.fragments.PatientInfoFormListFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An {@link android.support.v7.widget.RecyclerView.Adapter} to display list of routine
 */
public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.RoutineListViewHolder> {

    private LayoutInflater inflater;
    private List<Routine> routineList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;

    /**
     * Create routine list adapter with the specified values
     *
     * @param context context of the application
     * @param fragment fragment of the UI
     * @param routineList list of routine to be displayed
     * @param patient patient to be edited
     */
    public RoutineListAdapter(Context context, PatientInfoFormListFragment fragment, List<Routine> routineList, Patient patient) {
        this.inflater = LayoutInflater.from(context);
        this.routineList = routineList;
        this.fragment = fragment;
        this.patient = patient;
    }

    @Override
    public RoutineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.routine_card, parent, false);
        RoutineListViewHolder viewHolder = new RoutineListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoutineListViewHolder holder, int position) {
        Context context = holder.nameEditText.getContext();

        Routine routine = routineList.get(position);

        holder.nameEditText.setText(routine.getName());
        holder.notesEditText.setText(routine.getNotes());

        DateTime startDate = routine.getStartDate();
        if (startDate != null) {
            String startDateStr = routine.getStartDate().toString();
            if (startDateStr != null && !startDateStr.isEmpty()) {
                holder.startDatePicker.setText(startDate.toString(Global.DATE_FORMAT));
            }
        }

        DateTime endDate = routine.getEndDate();
        if (endDate != null) {
            String endDateStr = routine.getEndDate().toString();
            if (endDateStr != null && !endDateStr.isEmpty()) {
                holder.endDatePicker.setText(endDate.toString(Global.DATE_FORMAT));
            }
        }

        DateTime startTime = routine.getStartTime();
        if (startTime != null) {
            String startTimeStr = startTime.toString();
            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                holder.startTimePicker.setText(startTime.toString(Global.TIME_FORMAT));
            }
        }

        DateTime endTime = routine.getEndTime();
        if (endTime != null) {
            String endTimeStr = endTime.toString();
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                holder.endTimePicker.setText(endTime.toString(Global.TIME_FORMAT));
            }
        }

        if (routine.getEveryNumber() != null) {
            holder.everyEditText.setText(Integer.toString(routine.getEveryNumber()));
        }

        String everyLabelStr = routine.getEveryLabel();
        int idx;
        String[] everyLabelStrArray = context.getResources().getStringArray(R.array.option_every_label);
        idx = Arrays.asList(everyLabelStrArray).indexOf(everyLabelStr);
        holder.everySpinner.setSelection(idx);
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    class RoutineListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        EditText nameEditText;
        EditText notesEditText;
        EditText startDatePicker;
        EditText endDatePicker;
        EditText startTimePicker;
        EditText endTimePicker;
        EditText everyEditText;
        Spinner everySpinner;

        ImageView menuButton;
        ExpandableLayout expandableLayout;
        Button cancelButton;
        Button saveButton;

        String oldName;
        String oldNotes;
        String oldStartDate;
        String oldEndDate;
        String oldStartTime;
        String oldEndTime;
        String oldEveryNum;
        String oldEveryLabel;

        public RoutineListViewHolder(View itemView) {
            super(itemView);

            final Context context = itemView.getContext();

            nameEditText = (EditText) itemView.findViewById(R.id.routine_item_name_edit_text);
            notesEditText = (EditText) itemView.findViewById(R.id.routine_item_notes_edit_text);
            startDatePicker = (EditText) itemView.findViewById(R.id.routine_item_start_date_picker);
            endDatePicker = (EditText) itemView.findViewById(R.id.routine_item_end_date_picker);
            startTimePicker = (EditText) itemView.findViewById(R.id.routine_item_start_time_picker);
            endTimePicker = (EditText) itemView.findViewById(R.id.routine_item_end_time_picker);
            everyEditText = (EditText) itemView.findViewById(R.id.routine_item_every_edit_text);
            everySpinner = (Spinner) itemView.findViewById(R.id.routine_item_every_spinner);


            UtilsUi.setupEditTextToBeDatePicker(startDatePicker, context.getString(R.string.select_routine_start_date));
            UtilsUi.setupEditTextToBeDatePicker(endDatePicker, context.getString(R.string.select_routine_end_date));
            UtilsUi.setupEditTextToBeTimePicker(startTimePicker, context.getString(R.string.select_routine_start_time));
            UtilsUi.setupEditTextToBeTimePicker(endTimePicker, context.getString(R.string.select_routine_end_time));

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.option_every_label, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            everySpinner.setAdapter(adapter);

            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            menuButton.setOnClickListener(this);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_routine_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_routine_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_routine_save_button);

            setFormEditable(false);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
                    }
                    restoreOldFieldsValue();

                    nameEditText.setError(null);
                    startDatePicker.setError(null);
                    endDatePicker.setError(null);
                    startTimePicker.setError(null);
                    endTimePicker.setError(null);
                    everyEditText.setError(null);
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameStr = nameEditText.getText().toString();
                    String notesStr = notesEditText.getText().toString();
                    String startDateStr = startDatePicker.getText().toString();
                    String endDateStr = endDatePicker.getText().toString();
                    String startTimeStr = startTimePicker.getText().toString();
                    String endTimeStr = endTimePicker.getText().toString();
                    String everyNumStr = everyEditText.getText().toString();
                    String everyLabelStr = everySpinner.getSelectedItem().toString();


                    // Input checking
                    Resources resources = context.getResources();
                    boolean isValidForm = true;
                    String errorMessage = resources.getString(R.string.error_msg_field_required);

                    if (UtilsString.isEmpty(nameStr)) {
                        nameEditText.setError(errorMessage);
                        isValidForm = false;
                    }

                    DateTime startDate = null;
                    if (UtilsString.isEmpty(startDateStr)) {
                        startDatePicker.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
                    }

                    DateTime endDate = null;
                    if (UtilsString.isEmpty(endDateStr)) {
                        endDatePicker.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
                    }

                    DateTime startTime = null;
                    if (UtilsString.isEmpty(startTimeStr)) {
                        startTimePicker.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
                    }

                    DateTime endTime = null;
                    if (UtilsString.isEmpty(endTimeStr)) {
                        endTimePicker.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
                    }

                    Integer everyNum = null;
                    if (UtilsString.isEmpty(everyNumStr)) {
                        everyEditText.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        everyNum = Integer.parseInt(everyNumStr);
                    }

                    if (startDate != null && endDate != null) {
                        if (startDate.isAfter(endDate)) {
                            endDatePicker.setError(resources.getString(R.string.error_msg_end_date_must_be_after_start_date));
                            isValidForm = false;
                        }
                    }

                    if (startTime != null && endTime != null) {
                        if (startTime.isAfter(endTime)) {
                            endTimePicker.setError(resources.getString(R.string.error_msg_end_time_must_be_after_start_time));
                            isValidForm = false;
                        }
                    }

                    if (isValidForm) {
                        ArrayList<Routine> routineList = patient.getRoutineList();
                        Routine routine = routineList.get(getAdapterPosition());

                        routine.setName(nameStr);
                        routine.setNotes(notesStr);
                        routine.setStartDate(startDate);
                        routine.setEndDate(endDate);
                        routine.setStartTime(startTime);
                        routine.setEndTime(endTime);
                        routine.setEveryNumber(everyNum);
                        routine.setEveryLabel(everyLabelStr);

                        setFormEditable(false);
                        if (expandableLayout.isOpened()) {
                            expandableLayout.hide();
                        }
                    }
                }
            });
        }

        private void restoreOldFieldsValue() {
            nameEditText.setText(oldName);
            notesEditText.setText(oldNotes);

            startDatePicker.setText(oldStartDate);
            endDatePicker.setText(oldEndDate);
            startTimePicker.setText(oldStartTime);
            endTimePicker.setText(oldEndTime);

            everyEditText.setText(oldEveryNum);

            switch (oldEveryLabel) {
                case "Day":
                    everySpinner.setSelection(0);
                    break;
                case "Week":
                    everySpinner.setSelection(1);
                    break;
                case "Month":
                    everySpinner.setSelection(2);
                    break;
                case "Year":
                    everySpinner.setSelection(3);
                    break;
                default:
                    everySpinner.setSelection(0);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (v == menuButton) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_edit_item);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_item_edit:
                    oldName = nameEditText.getText().toString();
                    oldNotes = notesEditText.getText().toString();
                    oldStartDate = startDatePicker.getText().toString();
                    oldEndDate = endDatePicker.getText().toString();
                    oldStartTime = startTimePicker.getText().toString();
                    oldEndTime = endTimePicker.getText().toString();
                    oldEveryNum = everyEditText.getText().toString();
                    oldEveryLabel = everySpinner.getSelectedItem().toString();

                    if (!expandableLayout.isOpened()) {
                        expandableLayout.show();
                    }
                    setFormEditable(true);
                    return true;
                case R.id.action_item_delete:
                    int selectedItemIdx = getAdapterPosition();
                    fragment.deleteItem(selectedItemIdx);
                    return true;
                default:
                    return false;
            }
        }

        private void setFormEditable(boolean isEditable) {
            nameEditText.setFocusable(isEditable);
            nameEditText.setFocusableInTouchMode(isEditable);

            notesEditText.setFocusable(isEditable);
            notesEditText.setFocusableInTouchMode(isEditable);

            startDatePicker.setEnabled(isEditable);
            endDatePicker.setEnabled(isEditable);
            startTimePicker.setEnabled(isEditable);
            endTimePicker.setEnabled(isEditable);

            everyEditText.setFocusable(isEditable);
            everyEditText.setFocusableInTouchMode(isEditable);

            everySpinner.setEnabled(isEditable);
        }
    }
}