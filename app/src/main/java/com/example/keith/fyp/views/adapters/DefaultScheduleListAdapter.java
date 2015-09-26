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
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.DefaultSchedule;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.CareCenterConfigDefaultScheduleFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Sutrisno on 26/9/2015.
 */
public class DefaultScheduleListAdapter extends RecyclerView.Adapter<DefaultScheduleListAdapter.DefaultScheduleListViewHolder> {

    private LayoutInflater inflater;
    private List<DefaultSchedule> defaultScheduleList;
    private CareCenterConfigDefaultScheduleFragment fragment;

    public DefaultScheduleListAdapter(Context context, CareCenterConfigDefaultScheduleFragment fragment, List<DefaultSchedule> defaultScheduleList) {
        this.inflater = LayoutInflater.from(context);
        this.defaultScheduleList = defaultScheduleList;
        this.fragment = fragment;
    }

    @Override
    public DefaultScheduleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.default_schedule_card, parent, false);
        DefaultScheduleListViewHolder viewHolder = new DefaultScheduleListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultScheduleListViewHolder holder, int position) {
        Context context = holder.nameEditText.getContext();

        DefaultSchedule defaultSchedule = defaultScheduleList.get(position);

        holder.nameEditText.setText(defaultSchedule.getName());

        DateTime startTime = defaultSchedule.getStartTime();
        if (startTime != null) {
            String startTimeStr = startTime.toString();
            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                holder.startTimePicker.setText(startTime.toString(Global.TIME_FORMAT));
            }
        }

        DateTime endTime = defaultSchedule.getEndTime();
        if (endTime != null) {
            String endTimeStr = endTime.toString();
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                holder.endTimePicker.setText(endTime.toString(Global.TIME_FORMAT));
            }
        }

        if (defaultSchedule.getEveryNumber() != null) {
            holder.everyEditText.setText(Integer.toString(defaultSchedule.getEveryNumber()));
        }

        String everyLabelStr = defaultSchedule.getEveryLabel();
        int everyLabelIdx;
        String[] everyLabelStrArray = context.getResources().getStringArray(R.array.option_every_label);
        everyLabelIdx = Arrays.asList(everyLabelStrArray).indexOf(everyLabelStr);
        holder.everySpinner.setSelection(everyLabelIdx);

        String startDayStr = defaultSchedule.getStartDay();
        int startDayIdx;
        String[] startDayStrArray = context.getResources().getStringArray(R.array.option_day);
        startDayIdx = Arrays.asList(startDayStrArray).indexOf(startDayStr);
        holder.startDaySpinner.setSelection(startDayIdx + 1);
    }

    @Override
    public int getItemCount() {
        return defaultScheduleList.size();
    }

    class DefaultScheduleListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        EditText nameEditText;
        EditText startTimePicker;
        EditText endTimePicker;
        EditText everyEditText;
        Spinner everySpinner;
        MaterialSpinner startDaySpinner;

        ImageView menuButton;
        ExpandableLayout expandableLayout;
        Button cancelButton;
        Button saveButton;

        String oldName;
        String oldStartTime;
        String oldEndTime;
        String oldEveryNum;
        String oldEveryLabel;
        String oldStartDay;

        public DefaultScheduleListViewHolder(View itemView) {
            super(itemView);

            Context context = itemView.getContext();

            nameEditText = (EditText) itemView.findViewById(R.id.default_schedule_item_name_edit_text);
            startTimePicker = (EditText) itemView.findViewById(R.id.default_schedule_item_start_time_picker);
            endTimePicker = (EditText) itemView.findViewById(R.id.default_schedule_item_end_time_picker);
            everyEditText = (EditText) itemView.findViewById(R.id.default_schedule_item_every_edit_text);
            everySpinner = (Spinner) itemView.findViewById(R.id.default_schedule_item_every_spinner);
            startDaySpinner = (MaterialSpinner) itemView.findViewById(R.id.default_schedule_item_start_day_spinner);

            UtilsUi.setupEditTextToBeTimePicker(startTimePicker, context.getString(R.string.select_routine_start_time));
            UtilsUi.setupEditTextToBeTimePicker(endTimePicker, context.getString(R.string.select_routine_end_time));

            ArrayAdapter<CharSequence> everyLabelAdapter = ArrayAdapter.createFromResource(context,
                    R.array.option_every_label, android.R.layout.simple_spinner_item);
            everyLabelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            everySpinner.setAdapter(everyLabelAdapter);

            ArrayAdapter<CharSequence> startDayAdapter = ArrayAdapter.createFromResource(context,
                    R.array.option_day, android.R.layout.simple_spinner_item);
            startDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            startDaySpinner.setAdapter(startDayAdapter);

            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            menuButton.setOnClickListener(this);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_default_schedule_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_default_schedule_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_default_schedule_save_button);

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
                    ArrayList<DefaultSchedule> defaultScheduleList = DataHolder.getDefaultScheduleList();
                    DefaultSchedule defaultSchedule = defaultScheduleList.get(getAdapterPosition());

                    defaultSchedule.setName(nameEditText.getText().toString());

                    String startTimeStr = startTimePicker.getText().toString();
                    if (!UtilsString.isEmpty(startTimeStr)) {
                        DateTime  startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
                        defaultSchedule.setStartTime(startTime);
                    }

                    String endTimeStr = endTimePicker.getText().toString();
                    if (!UtilsString.isEmpty(endTimeStr)) {
                        DateTime endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
                        defaultSchedule.setEndTime(endTime);
                    }

                    String everyNumStr = everyEditText.getText().toString();
                    Integer everyNum = null;
                    if (!UtilsString.isEmpty(everyNumStr)) {
                        everyNum = Integer.parseInt(everyNumStr);
                    }
                    defaultSchedule.setEveryNumber(everyNum);

                    defaultSchedule.setEveryLabel(everySpinner.getSelectedItem().toString());

                    defaultSchedule.setStartDay(startDaySpinner.getSelectedItem().toString());

                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
                    }
                }
            });
        }

        private void restoreOldFieldsValue() {
            nameEditText.setText(oldName);
            startTimePicker.setText(oldStartTime);
            endTimePicker.setText(oldEndTime);
            everyEditText.setText(oldEveryNum);
            startDaySpinner.setTag(oldStartDay);

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
                    oldStartTime = startTimePicker.getText().toString();
                    oldEndTime = endTimePicker.getText().toString();
                    oldEveryNum = everyEditText.getText().toString();
                    oldEveryLabel = everySpinner.getSelectedItem().toString();
                    oldStartDay = startDaySpinner.getSelectedItem().toString();

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

            startTimePicker.setEnabled(isEditable);
            endTimePicker.setEnabled(isEditable);

            everyEditText.setFocusable(isEditable);
            everyEditText.setFocusableInTouchMode(isEditable);

            everySpinner.setEnabled(isEditable);
            startDaySpinner.setEnabled(isEditable);
        }
    }
}
