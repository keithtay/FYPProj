package com.example.keith.fyp.views.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.ScheduleList;
import com.example.keith.fyp.views.activities.NotificationDetailActivity;

import java.util.ArrayList;

/**
 * Created by Keith on 26/1/2016.
 */
public class FlexibleEvent extends Fragment {
    private View rootView;
    Button btn;
    EditText ed1, ed2, ed3;
    MyCustomAdapter dataAdapter = null;
    ArrayList<ScheduleList> schedule;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.flexible_timeslot, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int UserTypeID = Integer.parseInt(preferences.getString("userTypeId", ""));
        if(UserTypeID ==3) {
            displayListView();
            checkButtonClick();
            insertClick();
        }
        return rootView;
    }

    private void displayListView() {

        //Array list of countries
        schedule = new ArrayList<ScheduleList>();
        dbfile db = new dbfile();
        schedule = db.getAllFlexibleEvents();

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(getActivity(),
                R.layout.event_info, schedule);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_checkbox);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                ScheduleList s2 = (ScheduleList) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Clicked on Row: " + s2.getEventName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<ScheduleList> {

        private ArrayList<ScheduleList> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<ScheduleList> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<ScheduleList>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            TextView duration;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.event_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.duration = (TextView) convertView.findViewById(R.id.duration);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        ScheduleList s1 = (ScheduleList) cb.getTag();
                        Toast.makeText(getActivity(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        s1.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            ScheduleList s2 = countryList.get(position);
            holder.code.setText(" (" +  s2.getEventDesc() + ")");
            holder.name.setText(s2.getEventName());
            holder.duration.setText(s2.getEventDuration());
            holder.name.setChecked(s2.isSelected());
            holder.name.setTag(s2);

            return convertView;

        }

    }



    private void checkButtonClick() {


        Button myButton = (Button) rootView.findViewById(R.id.button2);


        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<String> abc = new ArrayList<String>();
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were removed from the database...\n");

                ArrayList<ScheduleList> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    ScheduleList country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getEventName());
                        abc.add(country.getEventName());
                    }
                }
                if(abc.size()!=0) {
                    dbfile db = new dbfile();
                    db.removeFlexibleEvent(abc);
                    Toast.makeText(getActivity(),
                            responseText, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),
                            "You have not select any events", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    private void insertClick() {


        Button myButton1 = (Button) rootView.findViewById(R.id.button);
        ed1 = (EditText)rootView.findViewById(R.id.textEventName);
        ed2 = (EditText)rootView.findViewById(R.id.textScheduleDescription);
        ed3 = (EditText)rootView.findViewById(R.id.numberText);
        myButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    String name = ed1.getText().toString();
                    String desc = ed2.getText().toString();
                    String number = ed3.getText().toString();
                if(name.equals("")||name.equals(" ")||desc.equals("")|| desc.equals(" ")||number.equals("")||number.equals(" ")){
                    Toast.makeText(getActivity(), "Please do not leave any field blank", Toast.LENGTH_LONG).show();
                }else{
                    dbfile db = new dbfile();
                    db.insertFlexibleEvent(name, desc, number);
                    Toast.makeText(getActivity(), "Successfully Inserted", Toast.LENGTH_LONG).show();

                }



            }
        });

    }
}

