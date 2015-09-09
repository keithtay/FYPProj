package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.PatientFormSpec;
import com.example.keith.fyp.utils.DataHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javadz.beanutils.PropertyUtils;

/**
 * Created by Sutrisno on 9/9/2015.
 */
public class CreatePatientInfoFormFragment extends Fragment {
    protected final String TEXT_VIEW = "TextView";

    protected Patient createdPatient;
    protected ArrayList<PatientFormSpec> patientFormSpecs;

    public void init() {
        createdPatient = DataHolder.getCreatedPatient();
        patientFormSpecs = new ArrayList<>();
    }

    // Fill the form with the previously inserted value and add listener on change value
    protected void prepareForm(View rootView, ArrayList<PatientFormSpec> patientFormSpecs) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for(final PatientFormSpec spec : patientFormSpecs) {
            View view = rootView.findViewById(spec.getViewId());
            switch(spec.getViewClassName()) {
                case TEXT_VIEW:
                    TextView textView = (TextView) view;
                    textView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            try {
                                PropertyUtils.setProperty(createdPatient, spec.getAttributeName(), s.toString());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    String atributeValue = (String) PropertyUtils.getProperty(createdPatient, spec.getAttributeName());
                    if(atributeValue != null && !atributeValue.isEmpty()) {
                        textView.setText(atributeValue);
                    }
            }
        }
    }
}
