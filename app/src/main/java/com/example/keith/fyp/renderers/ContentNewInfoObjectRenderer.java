package com.example.keith.fyp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.views.adapters.AttributeValueListAdapter;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ContentNewInfoObjectRenderer extends ContentRenderer {

    private ObjectToAttributeValueTransformer newObject;

    public ContentNewInfoObjectRenderer(LayoutInflater inflater, ObjectToAttributeValueTransformer newObject) {
        super(inflater);
        this.newObject = newObject;
    }

    @Override
    public View render() {
        Context context = inflater.getContext();

        View rootView = inflater.inflate(R.layout.notification_detail_content_new_info_object_layout, null);

        ListView newObjectListView = (ListView) rootView.findViewById(R.id.new_object_list_view);
        newObjectListView.setAdapter(new AttributeValueListAdapter(context, newObject.transform()));

        return rootView;
    }
}
