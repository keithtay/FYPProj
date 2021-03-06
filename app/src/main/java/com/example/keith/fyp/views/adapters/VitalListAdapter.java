package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.keith.fyp.models.Vital;
import com.example.keith.fyp.utils.DataHolder;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormVitalFragment;
import com.example.keith.fyp.views.fragments.PatientInfoFormListFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * An {@link android.support.v7.widget.RecyclerView.Adapter} to display list of vital measurement
 */
public class VitalListAdapter extends RecyclerView.Adapter<VitalListAdapter.VitalListViewHolder> {

    private LayoutInflater inflater;
    private List<Vital> vitalList;
    private PatientInfoFormListFragment fragment;
    private Patient patient;
    private Context context;
    /**
     * Create vital list adapter with the specified values
     *
     * @param context context of the application
     * @param createPatientInfoFormVitalFragment fragment of the UI
     * @param vitalList list of vital to be displayed
     * @param patient patient to be edited
     */
    public VitalListAdapter(Context context, PatientInfoFormListFragment createPatientInfoFormVitalFragment, List<Vital> vitalList, Patient patient) {
        this.inflater = LayoutInflater.from(context);
        this.vitalList = vitalList;
        this.fragment = createPatientInfoFormVitalFragment;
        this.patient = patient;
        this.context = context;
    }

    @Override
    public VitalListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.vital_card, parent, false);
        VitalListViewHolder viewHolder = new VitalListViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VitalListViewHolder holder, int position) {
        Vital vital = vitalList.get(position);
        holder.dateTaken.setText(vital.getDateTimeTaken().toString(Global.DATE_FORMAT));
        holder.timeTaken.setText(vital.getDateTimeTaken().toString(Global.TIME_FORMAT));
        if(vital.isBeforeMeal() != null) {
            if(vital.isBeforeMeal()) {
                holder.beforeAfterMeal.setSelection(1);
            } else {
                holder.beforeAfterMeal.setSelection(2);
            }
        } else {
            holder.beforeAfterMeal.setSelection(0);
        }

        if(vital.getTemperature() != null) {
            holder.temperature.setText(Float.toString(vital.getTemperature()));
        }

        if(vital.getBloodPressureSystol() != null) {
            holder.bloodPressureSystol.setText(Float.toString(vital.getBloodPressureSystol()));
        }

        if(vital.getBloodPressureDiastol() != null) {
            holder.bloodPressureDiastol.setText(Float.toString(vital.getBloodPressureDiastol()));
        }

        if(vital.getHeight() != null) {
            holder.height.setText(Float.toString(vital.getHeight()));
        }

        if(vital.getWeight() != null) {
            holder.weight.setText(Float.toString(vital.getWeight()));
        }

        holder.notes.setText(vital.getNotes());
    }

    @Override
    public int getItemCount() {
        return vitalList.size();
    }

    class VitalListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        EditText dateTaken;
        EditText timeTaken;
        MaterialSpinner beforeAfterMeal;
        EditText temperature;
        EditText bloodPressureSystol;
        EditText bloodPressureDiastol;
        EditText height;
        EditText weight;
        EditText notes;

        ImageView menuButton;
        ExpandableLayout expandableLayout;
        Button cancelButton;
        Button saveButton;

        String oldDateTaken;
        String oldTimeTaken;
        String oldBeforeAfterMeal;
        String oldTemperature;
        String oldBloodPressureSystol;
        String oldBloodPressureDiastol;
        String oldHeight;
        String oldWeight;
        String oldNotes;

        public VitalListViewHolder(View itemView) {
            super(itemView);

            final Context context = itemView.getContext();

            dateTaken = (EditText) itemView.findViewById(R.id.vital_item_date_picker);
            UtilsUi.setupEditTextToBeDatePicker(dateTaken, context.getString(R.string.select_vital_date));

            timeTaken = (EditText) itemView.findViewById(R.id.vital_item_time_picker);
            UtilsUi.setupEditTextToBeTimePicker(timeTaken, context.getString(R.string.select_time_add_new_vital));

            beforeAfterMeal = (MaterialSpinner) itemView.findViewById(R.id.vital_item_label_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.option_vital_label, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            beforeAfterMeal.setAdapter(adapter);

            temperature = (EditText) itemView.findViewById(R.id.vital_item_temperature_edit_text);
            bloodPressureSystol = (EditText) itemView.findViewById(R.id.vital_item_blood_pressure_systol_edit_text);
            bloodPressureDiastol = (EditText) itemView.findViewById(R.id.vital_item_blood_pressure_diastol_edit_text);
            height = (EditText) itemView.findViewById(R.id.vital_item_height_edit_text);
            weight = (EditText) itemView.findViewById(R.id.vital_item_weight_edit_text);
            notes = (EditText) itemView.findViewById(R.id.vital_item_notes_edit_text);

            menuButton = (ImageView) itemView.findViewById(R.id.menu_button);
            menuButton.setOnClickListener(this);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.edit_vital_expandable_layout);
            cancelButton = (Button) itemView.findViewById(R.id.edit_vital_cancel_button);
            saveButton = (Button) itemView.findViewById(R.id.edit_vital_save_button);

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
                    ArrayList<Vital> vitalList = patient.getVitalList();
                    Vital vital = vitalList.get(getAdapterPosition());
                    Boolean a1;
                    if (vital.isBeforeMeal() == true) {
                        a1 = true;
                    } else {
                        a1 = false;
                    }
                    float diastol = vital.getBloodPressureDiastol();
                    float temp = vital.getTemperature();
                    float weight1 = vital.getWeight();
                    float sys = vital.getBloodPressureSystol();
                    float height1 = vital.getHeight();
                    String oldValue = vital.getDateTimeTaken().toString() + ";" + a1.toString() + ";" +
                            String.valueOf(temp) + ";" + String.valueOf(sys) + ";" +
                            String.valueOf(diastol) + ";" + String.valueOf(height1) + ";" +
                            String.valueOf(weight1) + ";" + vital.getNotes();
                    DateTime date = Global.DATE_FORMAT.parseDateTime(dateTaken.getText().toString());
                    int mYear = date.getYear();
                    int mMonth = date.getMonthOfYear();
                    int mDay = date.getDayOfMonth();

                    DateTime time = Global.TIME_FORMAT.parseDateTime(timeTaken.getText().toString());
                    int mHour = time.getHourOfDay();
                    int mMinutes = time.getMinuteOfHour();

                    DateTime savedDateTime = DateTime.now();
                    savedDateTime = savedDateTime.withYear(mYear);
                    savedDateTime = savedDateTime.withMonthOfYear(mMonth);
                    savedDateTime = savedDateTime.withDayOfMonth(mDay);
                    savedDateTime = savedDateTime.withHourOfDay(mHour);
                    savedDateTime = savedDateTime.withMinuteOfHour(mMinutes);

                    vital.setDateTimeTaken(savedDateTime);
                    Boolean isValid = true;
                    String beforeOrAfterMeal = beforeAfterMeal.getSelectedItem().toString();
                    if (beforeOrAfterMeal.equals("Before meal")) {
                        vital.setIsBeforeMeal(true);
                        beforeOrAfterMeal = "true";
                    } else if (beforeOrAfterMeal.equals("After meal")) {
                        vital.setIsBeforeMeal(false);
                        beforeOrAfterMeal = "false";
                    } else {
                        vital.setIsBeforeMeal(null);
                        beforeAfterMeal.setError("This field is required!");
                        isValid = false;
                    }


                    String tempStr = temperature.getText().toString();
                    if (tempStr != null && !tempStr.isEmpty()) {
                        vital.setTemperature(Float.parseFloat(tempStr));
                    } else {
                        temperature.setError("This field is required!");
                        isValid = false;
                    }

                    String systolStr = bloodPressureSystol.getText().toString();
                    if (systolStr != null && !systolStr.isEmpty()) {
                        vital.setBloodPressureSystol(Float.parseFloat(systolStr));
                    } else {
                        bloodPressureSystol.setError("This field is required!");
                        isValid = false;
                    }

                    String diastolStr = bloodPressureDiastol.getText().toString();
                    if (diastolStr != null && !diastolStr.isEmpty()) {
                        vital.setBloodPressureDiastol(Float.parseFloat(diastolStr));
                    } else {
                        bloodPressureDiastol.setError("This field is required!");
                        isValid = false;
                    }

                    String heightStr = height.getText().toString();
                    if (heightStr != null && !heightStr.isEmpty()) {
                        vital.setHeight(Float.parseFloat(heightStr));
                    } else {
                        height.setError("This field is required!");
                        isValid = false;
                    }

                    String weightStr = weight.getText().toString();
                    if (weightStr != null && !weightStr.isEmpty()) {
                        vital.setWeight(Float.parseFloat(weightStr));
                    } else {
                        weight.setError("This field is required!");
                        isValid = false;
                    }

                    vital.setNotes(notes.getText().toString());
                    if (notes.getText().toString().equals(null) || notes.getText().toString().isEmpty()) {
                        notes.setError("This field is required!");
                        isValid = false;
                    }
                    if (isValid) {
                        dbfile db = new dbfile();
                        String newValue = savedDateTime + ";" + beforeOrAfterMeal + ";" +
                                tempStr + ";" + systolStr + ";" +
                                diastolStr + ";" + heightStr + ";" +
                                weightStr + ";" + notes.getText().toString();
                        int x = db.getPatientId(patient.getNric());
                        int getRowID = db.getAllergyRowId(oldValue, x);
                        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                        final int UserID = Integer.parseInt(preferences.getString("userid", ""));
                        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
                        db.updatePatientSpec(oldValue, newValue, x, 2, getRowID, UserTypeID, UserID);
                        if (UserTypeID == 3) {
                            Toast.makeText(context, "Successfully Changed!", Toast.LENGTH_LONG).show();
                        } else {
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
            dateTaken.setText(oldDateTaken);
            timeTaken.setText(oldTimeTaken);
            if(oldBeforeAfterMeal.equals("Before meal")) {
                beforeAfterMeal.setSelection(1);
            } else if(oldBeforeAfterMeal.equals("After meal")) {
                beforeAfterMeal.setSelection(2);
            } else {
                beforeAfterMeal.setSelection(0);
            }

            temperature.setText(oldTemperature);
            bloodPressureSystol.setText(oldBloodPressureSystol);
            bloodPressureDiastol.setText(oldBloodPressureDiastol);
            height.setText(oldHeight);
            weight.setText(oldWeight);
            notes.setText(oldNotes);
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
                    oldDateTaken = dateTaken.getText().toString();
                    oldTimeTaken = timeTaken.getText().toString();
                    oldBeforeAfterMeal = beforeAfterMeal.getSelectedItem().toString();
                    oldTemperature = temperature.getText().toString();
                    oldBloodPressureSystol = bloodPressureSystol.getText().toString();
                    oldBloodPressureDiastol = bloodPressureDiastol.getText().toString();
                    oldHeight = height.getText().toString();
                    oldWeight = weight.getText().toString();
                    oldNotes = notes.getText().toString();

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
            dateTaken.setEnabled(isEditable);

            timeTaken.setEnabled(isEditable);

            beforeAfterMeal.setFocusable(isEditable);
            beforeAfterMeal.setFocusableInTouchMode(isEditable);
            beforeAfterMeal.setEnabled(isEditable);

            temperature.setFocusable(isEditable);
            temperature.setFocusableInTouchMode(isEditable);

            bloodPressureSystol.setFocusable(isEditable);
            bloodPressureSystol.setFocusableInTouchMode(isEditable);

            bloodPressureDiastol.setFocusable(isEditable);
            bloodPressureDiastol.setFocusableInTouchMode(isEditable);

            height.setFocusable(isEditable);
            height.setFocusableInTouchMode(isEditable);

            weight.setFocusable(isEditable);
            weight.setFocusableInTouchMode(isEditable);

            notes.setFocusable(isEditable);
            notes.setFocusableInTouchMode(isEditable);
        }
    }
}
