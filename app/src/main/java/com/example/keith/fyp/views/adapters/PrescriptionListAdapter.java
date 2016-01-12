package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPrescriptionFragment;
import com.example.keith.fyp.views.fragments.PatientInfoFormListFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * An {@link android.support.v7.widget.RecyclerView.Adapter} to display list of prescription
 */
public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.PrescriptionListViewHolder> {

    private LayoutInflater inflater;
    private List<Prescription> prescriptionList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;
    private Context context;
    /**
     * Create prescription list adapter with the specified values
     *
     * @param context context of the application
     * @param createPatientInfoFormPrescriptionFragment fragment of the UI
     * @param prescriptionList list of prescription to be displayed
     * @param patient patient to be edited
     */
    public PrescriptionListAdapter(Context context, PatientInfoFormListFragment createPatientInfoFormPrescriptionFragment, List<Prescription> prescriptionList, Patient patient) {
        this.inflater = LayoutInflater.from(context);
        this.prescriptionList = prescriptionList;
        this.fragment = createPatientInfoFormPrescriptionFragment;
        this.patient = patient;
        this.context = context;
    }

    @Override
    public PrescriptionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.prescription_card, parent, false);
        PrescriptionListViewHolder viewHolder = new PrescriptionListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PrescriptionListViewHolder holder, int position) {
        Context context = holder.nameEditText.getContext();

        Prescription prescription = prescriptionList.get(position);

        holder.nameEditText.setText(prescription.getName());
        holder.dosageEditText.setText(prescription.getDosage());
        if(prescription.getFreqPerDay() != null) {
            holder.freqEditText.setText(Integer.toString(prescription.getFreqPerDay()));
        }
        holder.instructionEditText.setText(prescription.getInstruction());

        DateTime startDate = prescription.getStartDate();
        if(startDate != null) {
            String startDateStr = prescription.getStartDate().toString();
            if(startDateStr != null && !startDateStr.isEmpty()) {
                holder.startDatePicker.setText(startDate.toString(Global.DATE_FORMAT));
            }
        }

        DateTime endDate = prescription.getEndDate();
        if(endDate != null) {
            String endDateStr = prescription.getEndDate().toString();
            if(endDateStr != null && !endDateStr.isEmpty()) {
                holder.endDatePicker.setText(endDate.toString(Global.DATE_FORMAT));
            }
        }

        holder.notesEditText.setText(prescription.getNotes());
        String beforeAfterMealStr = prescription.getBeforeAfterMeal();
        int idx = 0;
        String[] religionStrArray = context.getResources().getStringArray(R.array.option_prescription_before_after_meal);
        idx = Arrays.asList(religionStrArray).indexOf(beforeAfterMealStr);
        holder.beforeAfterMealSpinner.setSelection(idx + 1);
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    class PrescriptionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        EditText nameEditText;
        EditText dosageEditText;
        EditText freqEditText;
        EditText instructionEditText;
        EditText startDatePicker;
        EditText endDatePicker;
        EditText notesEditText;
        MaterialSpinner beforeAfterMealSpinner;

        ImageView menuButton;
        ExpandableLayout expandableLayout;
        Button cancelButton;
        Button saveButton;

        String oldName;
        String oldDosage;
        String oldFreq;
        String oldInstruction;
        String oldStartDate;
        String oldEndDate;
        String oldNotes;
        String oldBeforeAfterMeal;

        public PrescriptionListViewHolder(View itemView) {
            super(itemView);

            final Context context = itemView.getContext();

            nameEditText = (EditText) itemView.findViewById(R.id.prescription_item_name_edit_text);
            dosageEditText = (EditText) itemView.findViewById(R.id.prescription_item_dosage_edit_text);
            freqEditText = (EditText) itemView.findViewById(R.id.prescription_item_freq_edit_text);
            instructionEditText = (EditText) itemView.findViewById(R.id.prescription_item_instruction_edit_text);

            startDatePicker = (EditText) itemView.findViewById(R.id.prescription_item_start_date_picker);
            UtilsUi.setupEditTextToBeDatePicker(startDatePicker, context.getString(R.string.select_prescription_start_date));
            endDatePicker = (EditText) itemView.findViewById(R.id.prescription_item_end_date_picker);
            UtilsUi.setupEditTextToBeDatePicker(endDatePicker, context.getString(R.string.select_prescription_end_date));

            notesEditText = (EditText) itemView.findViewById(R.id.prescription_item_notes_edit_text);

            beforeAfterMealSpinner = (MaterialSpinner) itemView.findViewById(R.id.prescription_item_before_after_meal_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.option_prescription_before_after_meal, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            beforeAfterMealSpinner.setAdapter(adapter);

            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            menuButton.setOnClickListener(this);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_prescription_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_prescription_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_prescription_save_button);

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
                    dosageEditText.setError(null);
                    freqEditText.setError(null);
                    startDatePicker.setError(null);
                    endDatePicker.setError(null);
                    beforeAfterMealSpinner.setError(null);
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameStr = nameEditText.getText().toString();
                    String dosageStr = dosageEditText.getText().toString();
                    String freqStr = freqEditText.getText().toString();
                    String instructionStr = instructionEditText.getText().toString();
                    String startDateStr = startDatePicker.getText().toString();
                    String endDateStr = endDatePicker.getText().toString();

                    int beforeOrAfterMealPosition = beforeAfterMealSpinner.getSelectedItemPosition();
                    String beforeOrAfterMealStr = beforeAfterMealSpinner.getSelectedItem().toString();

                    String notesStr = notesEditText.getText().toString();

                    // Input checking
                    Resources resources = context.getResources();
                    boolean isValidForm = true;
                    String errorMessage = resources.getString(R.string.error_msg_field_required);

                    if(UtilsString.isEmpty(nameStr)) {
                        nameEditText.setError(errorMessage);
                        isValidForm = false;
                    }

                    if(UtilsString.isEmpty(dosageStr)) {
                        dosageEditText.setError(errorMessage);
                        isValidForm = false;
                    }

                    if(UtilsString.isEmpty(freqStr)) {
                        freqEditText.setError(errorMessage);
                        isValidForm = false;
                    }

                    DateTime startDate = null;
                    if(UtilsString.isEmpty(startDateStr)) {
                        startDatePicker.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
                    }

                    DateTime endDate = null;
                    if(UtilsString.isEmpty(endDateStr)) {
                        endDatePicker.setError(errorMessage);
                        isValidForm = false;
                    } else {
                        endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
                    }

                    if(beforeOrAfterMealPosition == 0) {
                        beforeAfterMealSpinner.setError(errorMessage);
                        isValidForm = false;
                    }

                    if(startDate != null && endDate != null) {
                        if(startDate.isAfter(endDate)) {
                            endDatePicker.setError(resources.getString(R.string.error_msg_end_date_must_be_after_start_date));
                            isValidForm = false;
                        }
                    }

                    if(isValidForm) {
                        ArrayList<Prescription> prescriptionList = patient.getPrescriptionList();
                        Prescription prescription = prescriptionList.get(getAdapterPosition());
                        dbfile db = new dbfile();
                        int x = db.getPatientId(patient.getNric());
                        DateTime d1 = prescription.getStartDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
                        DateTime d2 = prescription.getEndDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
                        String oldValue = prescription.getName() + ";" + prescription.getDosage() + ";" + prescription.getFreqPerDay()
                                +";"+ prescription.getInstruction() + ";" + d1 + ";" +
                                d2 + ";" + prescription.getBeforeAfterMeal().toString() +";"+
                                prescription.getNotes();
                        String newValue = nameStr + ";" + dosageStr + ";" + freqStr
                                +";"+ instructionStr + ";" + startDate + ";" +
                                endDate + ";" + beforeOrAfterMealStr +";"+
                                notesStr;
                        int getRowID = db.getAllergyRowId(oldValue,x);
                        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
                        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
                        db.updatePatientSpec(oldValue, newValue, x, 4, getRowID, UserTypeID, UserID);
                        if(UserTypeID == 3) {
                            prescription.setName(nameStr);
                            prescription.setDosage(dosageStr);
                            prescription.setFreqPerDay(Integer.parseInt(freqStr));
                            prescription.setInstruction(instructionStr);
                            prescription.setStartDate(startDate);
                            prescription.setEndDate(endDate);
                            prescription.setNotes(notesStr);
                            prescription.setBeforeAfterMeal(beforeOrAfterMealStr);
                            Toast.makeText(context, "Successfully Changed!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Pending Supervisor Approval", Toast.LENGTH_LONG).show();
                        }
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
            dosageEditText.setText(oldDosage);
            freqEditText.setText(oldFreq);
            instructionEditText.setText(oldInstruction);
            startDatePicker.setText(oldStartDate);
            endDatePicker.setText(oldEndDate);
            notesEditText.setText(oldNotes);


            switch (oldBeforeAfterMeal) {
                case "Before meal":
                    beforeAfterMealSpinner.setSelection(1);
                    break;
                case "After meal":
                    beforeAfterMealSpinner.setSelection(2);
                    break;
                case "No preferences":
                    beforeAfterMealSpinner.setSelection(3);
                    break;
                default:
                    beforeAfterMealSpinner.setSelection(0);
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
                    oldDosage = dosageEditText.getText().toString();
                    oldFreq = freqEditText.getText().toString();
                    oldInstruction = instructionEditText.getText().toString();
                    oldStartDate = startDatePicker.getText().toString();
                    oldEndDate = endDatePicker.getText().toString();
                    oldNotes = notesEditText.getText().toString();
                    oldBeforeAfterMeal = beforeAfterMealSpinner.getSelectedItem().toString();

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

            dosageEditText.setFocusable(isEditable);
            dosageEditText.setFocusableInTouchMode(isEditable);

            freqEditText.setFocusable(isEditable);
            freqEditText.setFocusableInTouchMode(isEditable);

            instructionEditText.setFocusable(isEditable);
            instructionEditText.setFocusableInTouchMode(isEditable);

            startDatePicker.setEnabled(isEditable);
            endDatePicker.setEnabled(isEditable);

            notesEditText.setFocusable(isEditable);
            notesEditText.setFocusableInTouchMode(isEditable);

            beforeAfterMealSpinner.setEnabled(isEditable);
        }
    }
}