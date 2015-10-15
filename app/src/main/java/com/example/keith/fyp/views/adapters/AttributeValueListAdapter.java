package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.AttributeValuePair;

import java.util.Collections;
import java.util.List;

/**
 * An {@link BaseAdapter} to display list of attribute name and value pair
 */
public class AttributeValueListAdapter extends BaseAdapter {

    private List<AttributeValuePair> attributeValueList = Collections.emptyList();
    private Context context;

    /**
     * Create an attribute value list adapter with the specified values
     *
     * @param context context of the application
     */
    public AttributeValueListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Create an attribute value list adapter with the specified values
     *
     * @param context context of the application
     * @param attributeValueList list of attribute name and value to be displayed
     */
    public AttributeValueListAdapter(Context context, List<AttributeValuePair> attributeValueList) {
        this.context = context;
        this.attributeValueList = attributeValueList;
    }

    @Override
    public int getCount() {
        return attributeValueList.size();
    }

    @Override
    public AttributeValuePair getItem(int position) {
        return attributeValueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View atrributeValueItemView = convertView;

        if (atrributeValueItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            atrributeValueItemView = vi.inflate(R.layout.attribute_value_item_layout, null);
        }

        final AttributeValuePair attributeValuePair = getItem(position);

        if (attributeValuePair != null) {
            TextView attributeNameTextView = (TextView) atrributeValueItemView.findViewById(R.id.attribute_name_text_view);
            TextView attributeValueTextView = (TextView) atrributeValueItemView.findViewById(R.id.attribute_value_text_view);

            if (attributeNameTextView != null) {
                attributeNameTextView.setText(attributeValuePair.getAttributeName());
            }

            if (attributeValueTextView != null) {
                attributeValueTextView.setText(attributeValuePair.getAttributeValue());
            }
        }
        return atrributeValueItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
