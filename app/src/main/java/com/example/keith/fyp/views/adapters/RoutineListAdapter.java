package com.example.keith.fyp.views.adapters;

import android.content.Context;
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
 * Created by Sutrisno on 13/9/2015.
 */
public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.RoutineListViewHolder> {

    private LayoutInflater inflater;
    private List<Routine> routineList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;

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

            Context context = itemView.getContext();

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
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Routine> routineList = patient.getRoutineList();
                    Routine routine = routineList.get(getAdapterPosition());

                    routine.setName(nameEditText.getText().toString());
                    routine.setNotes(notesEditText.getText().toString());

                    String startDateStr = startDatePicker.getText().toString();
                    DateTime startDate = null;
                    if (startDateStr != null && !startDateStr.isEmpty()) {
                        startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
                        routine.setStartDate(startDate);
                    }

                    String endDateStr = endDatePicker.getText().toString();
                    DateTime endDate = null;
                    if (endDateStr != null && !endDateStr.isEmpty()) {
                        endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
                        routine.setEndDate(endDate);
                    }

                    String startTimeStr = startTimePicker.getText().toString();
                    DateTime startTime = null;
                    if (startTimeStr != null && !startTimeStr.isEmpty()) {
                        startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
                        routine.setStartTime(startTime);
                    }

                    String endTimeStr = endTimePicker.getText().toString();
                    DateTime endTime = null;
                    if (endTimeStr != null && !endTimeStr.isEmpty()) {
                        endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
                        routine.setEndTime(endTime);
                    }

                    String everyNumStr = everyEditText.getText().toString();
                    Integer everyNum = null;
                    if (everyNumStr != null && !everyNumStr.isEmpty()) {
                        everyNum = Integer.parseInt(everyNumStr);
                    }
                    routine.setEveryNumber(everyNum);

                    routine.setEveryLabel(everySpinner.getSelectedItem().toString());

                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
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
                    everySpinner.setSelection(1);
                    break;
                case "Week":
                    everySpinner.setSelection(2);
                    break;
                case "Month":
                    everySpinner.setSelection(3);
                    break;
                case "Year":
                    everySpinner.setSelection(4);
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