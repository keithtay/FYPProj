package com.example.keith.fyp.views.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom view to make a {@link android.widget.ListView} that is not scrollable
 *
 * @author  Sutrisno Suryajaya Dwi Putra
 */
public class NotScrollableListView extends LinearLayout {

    private ListAdapter adapter;
    private DataChangeObserver dataChangeObserver;
    private Drawable divider;
    private int dividerHeight;

    private List<View> reusableViews = new ArrayList<>();

    /**
     * Create a unscrollable list view with the specified values
     *
     * @param context context of the application
     */
    public NotScrollableListView(Context context) {
        super(context);
    }

    /**
     * Create a unscrollable list view with the specified values
     *
     * @param context context of the application
     * @param attrs attributes of the view
     */
    public NotScrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setAttributes(attrs);
    }

    /**
     * Create a unscrollable list view with the specified values
     *
     * @param context context of the application
     * @param attrs attributes of the view
     * @param defStyleAttr style definition attribute
     */
    public NotScrollableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAttributes(attrs);
    }

    /**
     * @return list adapter
     */
    public ListAdapter getAdapter() {
        return adapter;
    }

    /**
     * @param adapter list adapter to set
     */
    public void setAdapter(ListAdapter adapter) {
        if (this.adapter != null && dataChangeObserver != null) {
            this.adapter.unregisterDataSetObserver(dataChangeObserver);
        }

        this.adapter = adapter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (adapter != null) {
            dataChangeObserver = new DataChangeObserver();
            adapter.registerDataSetObserver(dataChangeObserver);

            fillContents();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (adapter != null) {
            adapter.unregisterDataSetObserver(dataChangeObserver);
            dataChangeObserver = null;
        }
    }

    private void fillContents() {

        // clearing contents
        this.removeAllViews();

        final int count = adapter.getCount();   // item count
        final int reusableCount = reusableViews.size(); // count of cached reusable views

        // calculating of divider properties
        ViewGroup.LayoutParams dividerLayoutParams = null;
        if (divider != null && dividerHeight > 0) {
            dividerLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerHeight);
        }

        // adding items
        for (int i = 0; i < count; i++) {
            // adding item
            View converView = null;
            if (i < reusableCount) {    // we have cached view
                converView = reusableViews.get(i);
            }
            View view = adapter.getView(i, converView, this);

            if (i >= reusableCount) {   // caching view
                reusableViews.add(view);
            }

            addView(view);

            // adding divider
            if (divider != null && dividerHeight > 0) {
                if (i < count - 1) {
                    ImageView dividerView = new ImageView(getContext());
                    dividerView.setImageDrawable(divider);
                    dividerView.setLayoutParams(dividerLayoutParams);
                    addView(dividerView);
                }
            }
        }
    }

    private void setAttributes(AttributeSet attributes) {
        int[] dividerAttrs = new int[]{android.R.attr.divider, android.R.attr.dividerHeight};

        TypedArray a = getContext().obtainStyledAttributes(attributes, dividerAttrs);
        try {
            divider = a.getDrawable(0);
            dividerHeight = a.getDimensionPixelSize(1, 0);
        } finally {
            a.recycle();
        }

        setOrientation(VERTICAL);
    }

    private class DataChangeObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();

            fillContents();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();

            fillContents();
        }
    }
}
