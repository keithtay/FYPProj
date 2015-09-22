package com.example.keith.fyp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;

/**
 * Created by Keith on 23/9/2015.
 */
public class ScheduleRecycleView extends RecyclerView {

    private View noSearchResultView;
//    private View noPatientView;
    private SearchView searchFieldView;

    private int columnWidth;
    private GridLayoutManager manager;
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

    public ScheduleRecycleView(Context context) {
        super(context);
    }

    public ScheduleRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            // Read android:columnWidth from xml
            int[] attrsArray = {
                    android.R.attr.columnWidth
            };
            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
            columnWidth = array.getDimensionPixelSize(0, -1);
            array.recycle();
        }

        manager = new GridLayoutManager(getContext(), 1);
        setLayoutManager(manager);
    }

    public ScheduleRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (columnWidth > 0) {
            int spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
            manager.setSpanCount(spanCount);
        }
    }

    void checkIfEmpty() {
        if (noSearchResultView != null &&
                getAdapter() != null &&

                searchFieldView != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            String queryStr = searchFieldView.getQuery().toString();
            final boolean isQueryExist = queryStr != null && !queryStr.isEmpty();


            String noSearchResultFormat = getResources().getString(R.string.no_search_patient_result_notif1);
            String noSearchResultMsg = String.format(noSearchResultFormat, queryStr);
            TextView noSearchResultTextView = (TextView) noSearchResultView.findViewById(R.id.no_search_result_notif1);
            noSearchResultTextView.setText(noSearchResultMsg);

            noSearchResultView.setVisibility(emptyViewVisible && isQueryExist ? VISIBLE : GONE);
//            noPatientView.setVisibility(emptyViewVisible && !isQueryExist ? VISIBLE : GONE);
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

//    public void setNoPatientView(View noPatientView) {
//        this.noPatientView = noPatientView;
//        checkIfEmpty();
//    }
}
