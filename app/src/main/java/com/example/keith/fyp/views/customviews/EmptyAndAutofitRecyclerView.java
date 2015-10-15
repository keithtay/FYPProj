package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.keith.fyp.R;

/**
 * A {@link RecyclerView} that provide empty and autofit features.
 * Empty: Give the RecyclerView and empty state (i.e. display specific view when it is empty).
 * Autofit: Each column item has a fixed width.
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class EmptyAndAutofitRecyclerView extends RecyclerView {
    private View noSearchResultView;
    private View noPatientView;
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

    /**
     * Create a empty and autofit recycler view with the specified values
     *
     * @param context context of the application
     */
    public EmptyAndAutofitRecyclerView(Context context) {
        super(context);
    }

    /**
     * Create a empty and autofit recycler view with the specified values
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    public EmptyAndAutofitRecyclerView(Context context, AttributeSet attrs) {
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

    /**
     * Create a empty and autofit recycler view with the specified values
     *
     * @param context context of the application
     * @param attrs attributes of the view
     * @param defStyle style definition
     */
    public EmptyAndAutofitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
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

    private void checkIfEmpty() {
        if (noSearchResultView != null &&
                getAdapter() != null &&
                noPatientView != null &&
                searchFieldView != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            String queryStr = searchFieldView.getQuery().toString();
            final boolean isQueryExist = queryStr != null && !queryStr.isEmpty();


            String noSearchResultFormat = getResources().getString(R.string.no_search_patient_result_notif);
            String noSearchResultMsg = String.format(noSearchResultFormat, queryStr);
            TextView noSearchResultTextView = (TextView) noSearchResultView.findViewById(R.id.no_search_result_notif);
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

    /**
     * Set the view to be displayed when there is no search result
     *
     * @param noSearchResultView view to be displayed
     */
    public void setNoSearchResultView(View noSearchResultView) {
        this.noSearchResultView = noSearchResultView;
        checkIfEmpty();
    }

    /**
     * Set the search view to filter the list displayed
     *
     * @param searchFieldView serach view for filtering the list
     */
    public void setSearchFieldView(SearchView searchFieldView) {
        this.searchFieldView = searchFieldView;
        checkIfEmpty();
    }

    /**
     * Set the view to be displayed when this recycler view is empty
     *
     * @param noPatientView view to be displayed
     */
    public void setNoPatientView(View noPatientView) {
        this.noPatientView = noPatientView;
        checkIfEmpty();
    }
}