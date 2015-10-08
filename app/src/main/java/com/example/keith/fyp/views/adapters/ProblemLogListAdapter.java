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

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.PatientInfoFormListFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Sutrisno on 20/9/2015.
 */
public class ProblemLogListAdapter extends RecyclerView.Adapter<ProblemLogListAdapter.ProblemLogListViewHolder> {

    private LayoutInflater inflater;
    private List<ProblemLog> problemLogList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;
    private Context context;

    public ProblemLogListAdapter(Context context, PatientInfoFormListFragment fragment, List<ProblemLog> problemLogList, Patient patient) {
        this.inflater = LayoutInflater.from(context);
        this.problemLogList = problemLogList;
        this.fragment = fragment;
        this.patient = patient;
        this.context = context;
    }

    @Override
    public ProblemLogListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.problem_log_card, parent, false);
        ProblemLogListViewHolder viewHolder = new ProblemLogListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProblemLogListViewHolder holder, int position) {
        ProblemLog problemLog = problemLogList.get(position);

        holder.creationDateEditText.setText(problemLog.getCreationDate().toString(Global.DATE_FORMAT));

        String[] problemLogCategoryArray = context.getResources().getStringArray(R.array.option_problem_log_category);
        ArrayList<String> problemLogCategoryList = new ArrayList<>(Arrays.asList(problemLogCategoryArray));
        int index = problemLogCategoryList.indexOf(problemLog.getCategory()) + 1;
        holder.categorySpinner.setSelection(index);

        holder.notesEditText.setText(problemLog.getNotes());
    }

    @Override
    public int getItemCount() {
        return problemLogList.size();
    }

    class ProblemLogListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        EditText creationDateEditText;
        MaterialSpinner categorySpinner;
        EditText notesEditText;

        ImageView menuButton;
        ExpandableLayout expandableLayout;
        Button cancelButton;
        Button saveButton;

        String oldFromDate;
        String oldCategory;
        String oldNotes;

        Context context;

        public ProblemLogListViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();

            creationDateEditText = (EditText) itemView.findViewById(R.id.problem_log_item_from_date_edit_text);

            UtilsUi.setupEditTextToBeDatePicker(creationDateEditText, "Select problem log from date");

            categorySpinner = (MaterialSpinner) itemView.findViewById(R.id.problem_log_item_category_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.option_problem_log_category, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            notesEditText = (EditText) itemView.findViewById(R.id.problem_log_item_notes_edit_text);

            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_problem_log_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_problem_log_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_problem_log_save_button);
            menuButton.setOnClickListener(this);

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
                    ArrayList<ProblemLog> problemLogList = patient.getProblemLogList();
                    ProblemLog problemLog = problemLogList.get(getAdapterPosition());
                    problemLog.setCategory(categorySpinner.getSelectedItem().toString());
                    problemLog.setNotes(notesEditText.getText().toString());

                    DateTime fromDate = Global.DATE_FORMAT.parseDateTime(creationDateEditText.getText().toString());

                    problemLog.setCreationDate(fromDate);

                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
                    }
                }
            });
        }

        private void restoreOldFieldsValue() {
            creationDateEditText.setText(oldFromDate);

            String[] problemLogCategoryArray = context.getResources().getStringArray(R.array.option_problem_log_category);
            ArrayList<String> problemLogCategoryList = new ArrayList<>(Arrays.asList(problemLogCategoryArray));
            int index = problemLogCategoryList.indexOf(oldCategory) + 1;
            categorySpinner.setSelection(index);

            notesEditText.setText(oldNotes);
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
                    oldFromDate = creationDateEditText.getText().toString();
                    oldCategory = categorySpinner.getSelectedItem().toString();
                    oldNotes = notesEditText.getText().toString();

                    if(!expandableLayout.isOpened()) {
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
            creationDateEditText.setEnabled(isEditable);

            categorySpinner.setEnabled(isEditable);

            notesEditText.setFocusable(isEditable);
            notesEditText.setFocusableInTouchMode(isEditable);
        }
    }
}