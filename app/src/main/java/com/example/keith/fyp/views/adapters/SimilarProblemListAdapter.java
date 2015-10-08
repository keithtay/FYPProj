package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.AttributeValuePair;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.Global;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sutrisno on 8/10/2015.
 */
public class SimilarProblemListAdapter extends BaseAdapter {

    private List<ProblemLog> similarProblemList = Collections.emptyList();
    private Context context;

    public SimilarProblemListAdapter(Context context) {
        this.context = context;
    }

    public SimilarProblemListAdapter(Context context, List<ProblemLog> similarProblemList) {
        this.context = context;
        this.similarProblemList = similarProblemList;
    }

    @Override
    public int getCount() {
        return similarProblemList.size();
    }

    @Override
    public ProblemLog getItem(int position) {
        return similarProblemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View similarProblemItemView = convertView;

        if (similarProblemItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            similarProblemItemView = vi.inflate(R.layout.similar_problem_item_layout, null);
        }

        ProblemLog similarProblem = getItem(position);

        if (similarProblem != null) {
            TextView dateTextView = (TextView) similarProblemItemView.findViewById(R.id.similar_problem_date_text_view);
            TextView notesTextView = (TextView) similarProblemItemView.findViewById(R.id.similar_problem_notes_text_view);

            if (dateTextView != null) {
                dateTextView.setText(similarProblem.getCreationDate().toString(Global.DATE_FORMAT));
            }

            if (notesTextView != null) {
                notesTextView.setText(similarProblem.getNotes());
            }
        }
        return similarProblemItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
