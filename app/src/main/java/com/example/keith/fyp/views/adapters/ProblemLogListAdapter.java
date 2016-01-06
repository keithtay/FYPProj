package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.PatientInfoFormListFragment;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * An {@link android.support.v7.widget.RecyclerView.Adapter} to display list of problem log
 */
public class ProblemLogListAdapter extends RecyclerView.Adapter<ProblemLogListAdapter.ProblemLogListViewHolder> implements Filterable {

    private LayoutInflater inflater;
    private List<ProblemLog> problemLogList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;
    private Context context;

    /**
     * Create problem log list adapter with the specified values
     *
     * @param context context of the application
     * @param fragment fragment of the UI
     * @param problemLogList list of problem log to be displayed
     * @param patient patient to be edited
     */
    public ProblemLogListAdapter(Context context, PatientInfoFormListFragment fragment, List<ProblemLog> problemLogList, Patient patient) {
        this.inflater = LayoutInflater.from(context);
        this.problemLogList = problemLogList;
        this.fragment = fragment;
        this.patient = patient;
        this.context = context;
        this.problemLogFilter = new ProblemLogFilter(this, problemLogList);
    }

    @Override
    public ProblemLogListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.problem_log_card, parent, false);
        ProblemLogListViewHolder viewHolder = new ProblemLogListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProblemLogListViewHolder holder, int position) {
        final ProblemLog problemLog = problemLogList.get(position);

        holder.creationDateEditText.setText(problemLog.getCreationDate().toString(Global.DATE_FORMAT));

        String[] problemLogCategoryArray = context.getResources().getStringArray(R.array.option_problem_log_category);
        ArrayList<String> problemLogCategoryList = new ArrayList<>(Arrays.asList(problemLogCategoryArray));
        int index = problemLogCategoryList.indexOf(problemLog.getCategory()) + 1;
        holder.categorySpinner.setSelection(index);

        holder.notesEditText.setText(problemLog.getNotes());

//        holder.saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayList<ProblemLog> problemLogList = patient.getProblemLogList();
//
//                for (ProblemLog log : problemLogList) {
//                    if (log.getId().equals(problemLog.getId())) {
//                        problemLog.setCategory(holder.categorySpinner.getSelectedItem().toString());
//                        problemLog.setNotes(holder.notesEditText.getText().toString());
//
//                        DateTime fromDate = Global.DATE_FORMAT.parseDateTime(holder.creationDateEditText.getText().toString());
//                        problemLog.setCreationDate(fromDate);
//
//                        holder.setFormEditable(false);
//                        if (holder.expandableLayout.isOpened()) {
//                            holder.expandableLayout.hide();
//                        }
//
//                        break;
//                    }
//                }
//
//                updateFilterOriginalList(problemLogList);
//            }
//        });
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
                    String fromDate = creationDateEditText.getText().toString();
                    String category =categorySpinner.getSelectedItem().toString();
                    String notes = notesEditText.getText().toString();
                    boolean isValidForm = true;
                    String errorMessage = v.getContext().getResources().getString(R.string.error_msg_field_required);

                    if(UtilsString.isEmpty(fromDate)) {
                        creationDateEditText.setError(errorMessage);
                        isValidForm = false;
                    }

                    if(UtilsString.isEmpty(notes)) {
                        notesEditText.setError(errorMessage);
                        isValidForm = false;
                    }
                    if(isValidForm) {
                        ArrayList<ProblemLog> problemloglist = patient.getProblemLogList();
                        ProblemLog pl = problemloglist.get(getAdapterPosition());
                        dbfile db = new dbfile();
                        int x = db.getPatientId(patient.getNric());
                        String date1 = fromDate.substring(0, 10);

                        SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
                        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = null;
                        try {
                            date = format1.parse(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String a = format2.format(date).toString();
                        int year = Integer.valueOf(a.substring(0, 4));
                        int month = Integer.valueOf(a.substring(5, 7));
                        int day = Integer.valueOf(a.substring(8,10));


                        DateTime d1 = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);


                        String oldValue = pl.getCreationDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0) + ";" + pl.getCategory() + ";" + pl.getNotes();
                        String newValue = d1.toString()+ ";" + category + ";" + notes;
                        int getRowID = db.getAllergyRowId(oldValue,x);
                        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
                        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
                        db.updatePatientSpec(oldValue, newValue, x, 12, getRowID, UserTypeID, UserID);
                        if(UserTypeID == 3){
                            pl.setCreationDate(d1);
                            pl.setCategory(category);
                            pl.setNotes(notes);
                            Toast.makeText(context, "Successfully Changed!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
                        }
                        setFormEditable(false);
                        if (expandableLayout.isOpened()) {
                            expandableLayout.hide();
                        }
                    }

//                    restoreOldFieldsValue();
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
            creationDateEditText.setEnabled(isEditable);

            categorySpinner.setEnabled(isEditable);

            notesEditText.setFocusable(isEditable);
            notesEditText.setFocusableInTouchMode(isEditable);
        }
    }

    private ProblemLogFilter problemLogFilter;

    @Override
    public Filter getFilter() {
        return problemLogFilter;
    }

    private static class ProblemLogFilter extends Filter {

        private final ProblemLogListAdapter adapter;
        private List<ProblemLog> originalList;
        private List<ProblemLog> filteredList;

        private ProblemLogFilter(ProblemLogListAdapter adapter, List<ProblemLog> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new ArrayList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final ProblemLog problemLog : originalList) {
                    if (problemLog.getCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(problemLog);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.problemLogList.clear();
            adapter.problemLogList.addAll((ArrayList<ProblemLog>) results.values);
            adapter.notifyDataSetChanged();
        }

        public void setOriginalList(ArrayList<ProblemLog> newOriginalList) {
            this.originalList = new ArrayList<>(newOriginalList);
        }
    }


    public void updateFilterOriginalList(ArrayList<ProblemLog> problemLogList) {
        problemLogFilter.setOriginalList(problemLogList);
    }
}