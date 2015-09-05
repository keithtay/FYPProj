package com.example.keith.fyp.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.keith.fyp.R;

/**
 * Created by Sutrisno on 5/9/2015.
 */
public class EmptyRecyclerView extends RecyclerView {
    private View noSearchResultView;
    private View noPatientView;
    private SearchView searchFieldView;

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (noSearchResultView != null &&
                getAdapter() != null &&
                noPatientView != null &&
                searchFieldView != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            String queryStr = searchFieldView.getQuery().toString();
            final boolean isQueryExist = queryStr != null && !queryStr.isEmpty();


            String noSearchResultFormat = getResources().getString(R.string.no_search_patient_result_notif);
            String noSearchResultMsg = String.format(noSearchResultFormat, queryStr);
            TextView noSearchResultTextView = (TextView) noSearchResultView.findViewById(R.id.noSearchResultNotif);
            noSearchResultTextView.setText(noSearchResultMsg);

            noSearchResultView.setVisibility(emptyViewVisible && isQueryExist ? VISIBLE : GONE);
            noPatientView.setVisibility(emptyViewVisible && !isQueryExist ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    public void setNoSearchResultView(View noSearchResultView) {
        this.noSearchResultView = noSearchResultView;
        checkIfEmpty();
    }

    public void setSearchFieldView(SearchView searchFieldView) {
        this.searchFieldView = searchFieldView;
        checkIfEmpty();
    }

    public void setNoPatientView(View noPatientView) {
        this.noPatientView = noPatientView;
        checkIfEmpty();
    }
}