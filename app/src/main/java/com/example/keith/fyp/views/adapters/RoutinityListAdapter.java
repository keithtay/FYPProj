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
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormRoutinityFragment;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Routinity;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sutrisno on 13/9/2015.
 */
public class RoutinityListAdapter extends RecyclerView.Adapter<RoutinityListAdapter.RoutinityListViewHolder> {

    private LayoutInflater inflater;
    private List<Routinity> routinityList;
    private CreatePatientInfoFormRoutinityFragment fragment;

    public RoutinityListAdapter(Context context, CreatePatientInfoFormRoutinityFragment createPatientInfoFormRoutinityFragment, List<Routinity> routinityList) {
        this.inflater = LayoutInflater.from(context);
        this.routinityList = routinityList;
        this.fragment = createPatientInfoFormRoutinityFragment;
    }

    @Override
    public RoutinityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.routinity_card, parent, false);
        RoutinityListViewHolder viewHolder = new RoutinityListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoutinityListViewHolder holder, int position) {
        Routinity routinity = routinityList.get(position);

        holder.nameEditText.setText(routinity.getName());
        holder.notesEditText.setText(routinity.getNotes());

        DateTime startDate = routinity.getStartDate();
        if (startDate != null) {
            String startDateStr = routinity.getStartDate().toString();
            if (startDateStr != null && !startDateStr.isEmpty()) {
                holder.startDatePicker.setText(startDate.toString(Global.DATE_FORMAT));
            }
        }

        DateTime endDate = routinity.getEndDate();
        if (endDate != null) {
            String endDateStr = routinity.getEndDate().toString();
            if (endDateStr != null && !endDateStr.isEmpty()) {
                holder.endDatePicker.setText(endDate.toString(Global.DATE_FORMAT));
            }
        }

        DateTime startTime = routinity.getStartTime();
        if (startTime != null) {
            String startTimeStr = startTime.toString();
            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                holder.startTimePicker.setText(startTime.toString(Global.TIME_FORMAT));
            }
        }

        DateTime endTime = routinity.getEndTime();
        if (endTime != null) {
            String endTimeStr = endTime.toString();
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                holder.endTimePicker.setText(endTime.toString(Global.TIME_FORMAT));
            }
        }

        if (routinity.getEveryNumber() != null) {
            holder.everyEditText.setText(Integer.toString(routinity.getEveryNumber()));
        }

        String everyLabelStr = routinity.getEveryLabel();
        int idx;
        String[] everyLabelStrArray = fragment.getResources().getStringArray(R.array.option_every_label);
        idx = Arrays.asList(everyLabelStrArray).indexOf(everyLabelStr);
        holder.everySpinner.setSelection(idx);
    }

    @Override
    public int getItemCount() {
        return routinityList.size();
    }

    class RoutinityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

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

        public RoutinityListViewHolder(View itemView) {
            super(itemView);

            nameEditText = (EditText) itemView.findViewById(R.id.routinity_item_name_edit_text);
            notesEditText = (EditText) itemView.findViewById(R.id.routinity_item_notes_edit_text);
            startDatePicker = (EditText) itemView.findViewById(R.id.routinity_item_start_date_picker);
            endDatePicker = (EditText) itemView.findViewById(R.id.routinity_item_end_date_picker);
            startTimePicker = (EditText) itemView.findViewById(R.id.routinity_item_start_time_picker);
            endTimePicker = (EditText) itemView.findViewById(R.id.routinity_item_end_time_picker);
            everyEditText = (EditText) itemView.findViewById(R.id.routinity_item_every_edit_text);
            everySpinner = (Spinner) itemView.findViewById(R.id.routinity_item_every_spinner);

            fragment.setupEditTextToBeDatePicker(startDatePicker);
            fragment.setupEditTextToBeDatePicker(endDatePicker);
            fragment.setupEditTextToBeTimePicker(startTimePicker);
            fragment.setupEditTextToBeTimePicker(endTimePicker);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(fragment.getActivity(),
                    R.array.option_every_label, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            everySpinner.setAdapter(adapter);

            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            menuButton.setOnClickListener(this);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_routinity_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_routinity_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_routinity_save_button);

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
                    ArrayList<Routinity> routinityList = DataHolder.getCreatedPatient().getRoutinityList();
                    Routinity routinity = routinityList.get(getAdapterPosition());

                    routinity.setName(nameEditText.getText().toString());
                    routinity.setNotes(notesEditText.getText().toString());

                    String startDateStr = startDatePicker.getText().toString();
                    DateTime startDate = null;
                    if (startDateStr != null && !startDateStr.isEmpty()) {
                        startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
                        routinity.setStartDate(startDate);
                    }

                    String endDateStr = endDatePicker.getText().toString();
                    DateTime endDate = null;
                    if (endDateStr != null && !endDateStr.isEmpty()) {
                        endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
                        routinity.setEndDate(endDate);
                    }

                    String startTimeStr = startTimePicker.getText().toString();
                    DateTime startTime = null;
                    if (startTimeStr != null && !startTimeStr.isEmpty()) {
                        startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
                        routinity.setStartTime(startTime);
                    }

                    String endTimeStr = endTimePicker.getText().toString();
                    DateTime endTime = null;
                    if (endTimeStr != null && !endTimeStr.isEmpty()) {
                        endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
                        routinity.setEndTime(endTime);
                    }

                    String everyNumStr = everyEditText.getText().toString();
                    Integer everyNum = null;
                    if (everyNumStr != null && !everyNumStr.isEmpty()) {
                        everyNum = Integer.parseInt(everyNumStr);
                    }
                    routinity.setEveryNumber(everyNum);

                    routinity.setEveryLabel(everySpinner.getSelectedItem().toString());

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
                popup.inflate(R.menu.menu_allergy_item);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_allergy_edit:
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
                case R.id.action_allergy_delete:
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