package com.example.keith.fyp.views.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Sutrisno on 12/9/2015.
 */
public class SpacesCardItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesCardItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space / 2;
        outRect.top = space / 2;

        int numOfChild = parent.getChildCount();

        // Last item
        if (parent.getChildAdapterPosition(view) + 1 >= numOfChild) {
            outRect.bottom = space * 2;
        }
    }
}
