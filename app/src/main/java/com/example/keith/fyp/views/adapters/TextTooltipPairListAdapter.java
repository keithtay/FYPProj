package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.TextTooltipPair;
import com.example.keith.fyp.utils.UtilsString;
import com.example.keith.fyp.views.customviews.ToolTipViewWrapper;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import java.util.Collections;
import java.util.List;

/**
 * An {@link BaseAdapter} to display list of text and its description pair
 */
public class TextTooltipPairListAdapter extends BaseAdapter {

    private List<TextTooltipPair> textTooltipPairsList = Collections.emptyList();
    private Context context;

    /**
     * Create an text tooltip pair list adapter with the specified values
     *
     * @param context context of the application
     */
    public TextTooltipPairListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Create an text tooltip pair list adapter with the specified values
     *
     * @param context context of the application
     * @param textTooltipPairList list of text and its description to be displayed
     */
    public TextTooltipPairListAdapter(Context context, List<TextTooltipPair> textTooltipPairList) {
        this.context = context;
        this.textTooltipPairsList = textTooltipPairList;
    }

    @Override
    public int getCount() {
        return textTooltipPairsList.size();
    }

    @Override
    public TextTooltipPair getItem(int position) {
        return textTooltipPairsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View textItemView = convertView;

        if (textItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            textItemView = vi.inflate(R.layout.text_list_item_layout, null);
        }

        TextTooltipPair textTooltipPair = getItem(position);

        if (textTooltipPair != null) {
            final TextView textItemTextView = (TextView) textItemView.findViewById(R.id.text_item_text_view);
            final ImageView infoImageView = (ImageView) textItemView.findViewById(R.id.info_image_view);

            if (textItemTextView != null) {
                textItemTextView.setText(textTooltipPair.getText());
            }

            final ToolTipViewWrapper toolTipViewWrapper = new ToolTipViewWrapper();
            final String tooltipStr = textTooltipPair.getTooltip();
            if(UtilsString.isEmpty(tooltipStr)) {
                infoImageView.setVisibility(View.INVISIBLE);
            } else {
                if (infoImageView != null) {
                    final View finalTextItemView = textItemView;
                    infoImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(toolTipViewWrapper.getToolTipView() == null) {
                                ToolTip toolTip = new ToolTip()
                                        .withText(tooltipStr)
                                        .withTextColor(context.getResources().getColor(R.color.white))
                                        .withColor(context.getResources().getColor(R.color.black_87))
                                        .withShadow();

                                ToolTipRelativeLayout mToolTipFrameLayout = (ToolTipRelativeLayout) finalTextItemView.findViewById(R.id.tooltip_relative_layout);
                                toolTipViewWrapper.setToolTipView(mToolTipFrameLayout.showToolTipForView(toolTip, textItemTextView));
                            } else {
                                toolTipViewWrapper.getToolTipView().remove();
                                toolTipViewWrapper.setToolTipView(null);
                            }

                        }
                    });
                }
            }
        }
        return textItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
