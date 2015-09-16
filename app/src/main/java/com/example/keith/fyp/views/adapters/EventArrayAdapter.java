package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.EditScheduleActivity;
import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;

import java.util.List;

/**
 * Created by Sutrisno on 16/9/2015.
 */
public class EventArrayAdapter extends ArrayAdapter<Event> {

    private int selectedPosition;

    public EventArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public EventArrayAdapter(Context context, int resource, List<Event> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View eventView = convertView;

        if (eventView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            eventView = vi.inflate(R.layout.event_layout_editable, null);
        }

        final Event event = getItem(position);

        if (event != null) {
            TextView startTimeTextView = (TextView) eventView.findViewById(R.id.event_start_time_text_view);
            TextView durationTextView = (TextView) eventView.findViewById(R.id.event_duration_text_view);
            TextView titleTextView = (TextView) eventView.findViewById(R.id.event_title_text_view);
            TextView descriptionTextView = (TextView) eventView.findViewById(R.id.event_description_text_view);
            ImageView menuImageView = (ImageView) eventView.findViewById(R.id.menu_button);

            if (startTimeTextView != null) {
                startTimeTextView.setText(event.getStartTime().toString(Global.TIME_FORMAT));
            }

            if (durationTextView != null) {
                durationTextView.setText(UtilsUi.convertDurationToString(event.getDuration()));
            }

            if (titleTextView != null) {
                titleTextView.setText(event.getTitle());
            }

            if (descriptionTextView != null) {
                descriptionTextView.setText(event.getDescription());
            }

            if(menuImageView != null) {
                menuImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(), v);
                        popup.inflate(R.menu.menu_edit_item);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_item_edit:
                                        return true;
                                    case R.id.action_item_delete:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                        String dialogMessage = "Confirm to remove the \"" + event.getTitle() + "\" event at " + event.getStartTime().toString(Global.TIME_FORMAT) + "?";
                                        builder.setMessage(dialogMessage);

                                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Event eventToBeRemoved = getItem(position);
                                                remove(eventToBeRemoved);
                                                ((EditScheduleActivity) getContext()).updateScheduleListViewHeight();
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popup.show();
                    }
                });
            }
        }

        return eventView;
    }
}
