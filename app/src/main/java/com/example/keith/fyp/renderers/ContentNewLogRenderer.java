package com.example.keith.fyp.renderers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.ProblemLog;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ContentNewLogRenderer extends ContentRenderer {

    private ProblemLog problemLog;

    public ContentNewLogRenderer(LayoutInflater inflater, ProblemLog problemLog) {
        super(inflater);
        this.problemLog = problemLog;
    }

    @Override
    public View render() {
        View rootView = inflater.inflate(R.layout.notification_detail_content_new_log_layout, null);

        TextView logCategoryTextView = (TextView) rootView.findViewById(R.id.log_category_text_view);
        TextView logNotesTextView = (TextView) rootView.findViewById(R.id.log_notes_text_view);

        logCategoryTextView.setText(problemLog.getCategory());
        logNotesTextView.setText(problemLog.getNotes());

        return rootView;
    }
}
