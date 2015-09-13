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
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormPrescriptionFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.PrescriptionListViewHolder> {

    private LayoutInflater inflater;
    private List<Prescription> prescriptionList;
    private CreatePatientInfoFormPrescriptionFragment fragment;

    public PrescriptionListAdapter(Context context, CreatePatientInfoFormPrescriptionFragment createPatientInfoFormPrescriptionFragment, List<Prescription> prescriptionList) {
        this.inflater = LayoutInflater.from(context);
        this.prescriptionList = prescriptionList;
        this.fragment = createPatientInfoFormPrescriptionFragment;
    }

    @Override
    public PrescriptionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.prescription_card, parent, false);
        PrescriptionListViewHolder viewHolder = new PrescriptionListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PrescriptionListViewHolder holder, int position) {
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
        String[] religionStrArray = fragment.getResources().getStringArray(R.array.option_prescription_before_after_meal);
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

            nameEditText = (EditText) itemView.findViewById(R.id.prescription_item_name_edit_text);
            dosageEditText = (EditText) itemView.findViewById(R.id.prescription_item_dosage_edit_text);
            freqEditText = (EditText) itemView.findViewById(R.id.prescription_item_freq_edit_text);
            instructionEditText = (EditText) itemView.findViewById(R.id.prescription_item_instruction_edit_text);

            startDatePicker = (EditText) itemView.findViewById(R.id.prescription_item_start_date_picker);
            fragment.setupEditTextToBeDatePicker(startDatePicker, fragment.getString(R.string.select_prescription_start_date));
            endDatePicker = (EditText) itemView.findViewById(R.id.prescription_item_end_date_picker);
            fragment.setupEditTextToBeDatePicker(endDatePicker, fragment.getString(R.string.select_prescription_end_date));

            notesEditText = (EditText) itemView.findViewById(R.id.prescription_item_notes_edit_text);

            beforeAfterMealSpinner = (MaterialSpinner) itemView.findViewById(R.id.prescription_item_before_after_meal_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(fragment.getActivity(),
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
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Prescription> prescriptionList = DataHolder.getCreatedPatient().getPrescriptionList();
                    Prescription prescription = prescriptionList.get(getAdapterPosition());

                    String startDateStr = startDatePicker.getText().toString();
                    DateTime startDate = null;
                    if(startDateStr != null && !startDateStr.isEmpty()) {
                        startDate = Global.DATE_FORMAT.parseDateTime(startDateStr);
                        prescription.setStartDate(startDate);
                    }

                    String endDateStr = endDatePicker.getText().toString();
                    DateTime endDate = null;
                    if(endDateStr != null && !endDateStr.isEmpty()) {
                        endDate = Global.DATE_FORMAT.parseDateTime(endDateStr);
                        prescription.setEndDate(endDate);
                    }

                    String beforeOrAfterMeal = beforeAfterMealSpinner.getSelectedItem().toString();
                    prescription.setBeforeAfterMeal(beforeOrAfterMeal);

                    prescription.setName(nameEditText.getText().toString());
                    prescription.setDosage(dosageEditText.getText().toString());

                    String freqStr = freqEditText.getText().toString();
                    if (freqStr != null && !freqStr.isEmpty()) {
                        prescription.setFreqPerDay(Integer.getInteger(freqStr));
                    }

                    prescription.setInstruction(instructionEditText.getText().toString());
                    prescription.setNotes(notesEditText.getText().toString());

                    setFormEditable(false);
                    if (expandableLayout.isOpened()) {
                        expandableLayout.hide();
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