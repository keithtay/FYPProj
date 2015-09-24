package com.example.keith.fyp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.AttributeValueListAdapter;

import org.w3c.dom.Attr;

/**
 * Created by Sutrisno on 21/9/2015.
 */
public class ContentUpdateInfoObjectRenderer extends ContentRenderer {

    ObjectToAttributeValueTransformer oldObject;
    ObjectToAttributeValueTransformer newObject;

    public ContentUpdateInfoObjectRenderer(LayoutInflater inflater, ObjectToAttributeValueTransformer oldObject, ObjectToAttributeValueTransformer newObject) {
        super(inflater);
        this.oldObject = oldObject;
        this.newObject = newObject;
    }

    @Override
    public View render() {
        Context context = inflater.getContext();

        View rootView = inflater.inflate(R.layout.notification_detail_content_update_info_object_layout, null);

        ListView oldObjectListView = (ListView) rootView.findViewById(R.id.old_object_list_view);
        ListView newObjectListView = (ListView) rootView.findViewById(R.id.new_object_list_view);

        oldObjectListView.setAdapter(new AttributeValueListAdapter(context, oldObject.transform()));
        newObjectListView.setAdapter(new AttributeValueListAdapter(context, newObject.transform()));

        return rootView;
    }
}
