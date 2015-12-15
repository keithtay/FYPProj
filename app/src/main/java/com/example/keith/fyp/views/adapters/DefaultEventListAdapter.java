package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.CareCenterConfigDefaultEventFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * An {@link android.support.v7.widget.RecyclerView.Adapter} to display list of default event
 */
public class DefaultEventListAdapter extends RecyclerView.Adapter<DefaultEventListAdapter.DefaultEventListViewHolder> {

    private LayoutInflater inflater;
    private List<DefaultEvent> defaultEventList;
    private CareCenterConfigDefaultEventFragment fragment;

    /**
     * Create allergy list adapter with the specified values
     *
     * @param context context of the application
     * @param fragment fragment of the UI
     * @param defaultEventList list of default event to be displayed
     */
    public DefaultEventListAdapter(Context context, CareCenterConfigDefaultEventFragment fragment, List<DefaultEvent> defaultEventList) {
        this.inflater = LayoutInflater.from(context);
        this.defaultEventList = defaultEventList;
        this.fragment = fragment;
    }

    @Override
    public DefaultEventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.default_event_card, parent, false);
        DefaultEventListViewHolder viewHolder = new DefaultEventListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultEventListViewHolder holder, int position) {
        Context context = holder.nameEditText.getContext();

        DefaultEvent defaultEvent = defaultEventList.get(position);

        holder.nameEditText.setText(defaultEvent.getName());

        DateTime startTime = defaultEvent.getStartTime();
        if (startTime != null) {
            String startTimeStr = startTime.toString();
            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                holder.startTimePicker.setText(startTime.toString(Global.TIME_FORMAT));
            }
        }

        DateTime endTime = defaultEvent.getEndTime();
        if (endTime != null) {
            String endTimeStr = endTime.toString();
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                holder.endTimePicker.setText(endTime.toString(Global.TIME_FORMAT));
            }
        }

        if (defaultEvent.getEveryNumber() != null) {
            holder.everyEditText.setText(Integer.toString(defaultEvent.getEveryNumber()));
        }

        String everyLabelStr = defaultEvent.getEveryLabel();
        int everyLabelIdx;
        String[] everyLabelStrArray = context.getResources().getStringArray(R.array.option_every_label);
        everyLabelIdx = Arrays.asList(everyLabelStrArray).indexOf(everyLabelStr);
        holder.everySpinner.setSelection(everyLabelIdx);

        String startDayStr = defaultEvent.getStartDay();
        int startDayIdx;
        String[] startDayStrArray = context.getResources().getStringArray(R.array.option_day);
        startDayIdx = Arrays.asList(startDayStrArray).indexOf(startDayStr);
        holder.startDaySpinner.setSelection(startDayIdx + 1);
    }

    @Override
    public int getItemCount() {
        return defaultEventList.size();
    }

    class DefaultEventListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

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

        public DefaultEventListViewHolder(View itemView) {
            super(itemView);

            Context context = itemView.getContext();

            nameEditText = (EditText) itemView.findViewById(R.id.default_event_item_name_edit_text);
            startTimePicker = (EditText) itemView.findViewById(R.id.default_event_item_start_time_picker);
            endTimePicker = (EditText) itemView.findViewById(R.id.default_event_item_end_time_picker);
            everyEditText = (EditText) itemView.findViewById(R.id.default_event_item_every_edit_text);
            everySpinner = (Spinner) itemView.findViewById(R.id.default_event_item_every_spinner);
            startDaySpinner = (MaterialSpinner) itemView.findViewById(R.id.default_event_item_start_day_spinner);

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

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_default_event_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_default_event_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_default_event_save_button);

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
                    startDaySpinner.setError(null);
                    startTimePicker.setError(null);
                    endTimePicker.setError(null);
                    everyEditText.setError(null);
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameStr = nameEditText.getText().toString();
                    String startTimeStr = startTimePicker.getText().toString();
                    String endTimeStr = endTimePicker.getText().toString();
                    String everyNumStr = everyEditText.getText().toString();
                    String everyLabelStr = everySpinner.getSelectedItem().toString();
                    int startDayPosition = startDaySpinner.getSelectedItemPosition();
                    String startDayStr = startDaySpinner.getSelectedItem().toString();

                    // Input checking
                    boolean isValidForm = true;
                    Resources resources = v.getResources();
                    String errorMsg = resources.getString(R.string.error_msg_field_required);

                    if (UtilsString.isEmpty(nameStr)) {
                        nameEditText.setError(errorMsg);
                        isValidForm = false;
                    }

                    if (startDayPosition == 0) {
                        startDaySpinner.setError(errorMsg);
                        isValidForm = false;
                    }

                    DateTime startTime = null;
                    if (UtilsString.isEmpty(startTimeStr)) {
                        startTimePicker.setError(errorMsg);
                        isValidForm = false;
                    } else {
                        startTime = Global.TIME_FORMAT.parseDateTime(startTimeStr);
                    }

                    DateTime endTime = null;
                    if (UtilsString.isEmpty(endTimeStr)) {
                        endTimePicker.setError(errorMsg);
                        isValidForm = false;
                    } else {
                        endTime = Global.TIME_FORMAT.parseDateTime(endTimeStr);
                    }

                    if (UtilsString.isEmpty(everyNumStr)) {
                        everyEditText.setError(errorMsg);
                        isValidForm = false;
                    }

                    if (startTime != null && endTime != null) {
                        if (startTime.isAfter(endTime)) {
                            endTimePicker.setError(resources.getString(R.string.error_msg_end_time_must_be_after_start_time));
                        }
                    }

                    if (isValidForm) {
                        ArrayList<DefaultEvent> defaultEventList = DataHolder.getDefaultEventList();
                        DefaultEvent defaultEvent = defaultEventList.get(getAdapterPosition());
                        Integer everyNum = Integer.parseInt(everyNumStr);

                        defaultEvent.setName(nameStr);
                        defaultEvent.setStartTime(startTime);
                        defaultEvent.setEndTime(endTime);
                        defaultEvent.setEveryNumber(everyNum);
                        defaultEvent.setEveryLabel(everyLabelStr);
                        defaultEvent.setStartDay(startDayStr);

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
                    oldName = nameEditText.getText().toString();
                    oldStartTime = startTimePicker.getText().toString() +"%";
                    oldEndTime = endTimePicker.getText().toString() +"%";
                    oldEveryNum = everyEditText.getText().toString();
                    oldEveryLabel = everySpinner.getSelectedItem().toString();
                    dbfile db=new dbfile();
                    db.updateDefaultEvent(oldName,oldStartTime,oldEndTime,Integer.parseInt(oldEveryNum),oldEveryLabel);
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
