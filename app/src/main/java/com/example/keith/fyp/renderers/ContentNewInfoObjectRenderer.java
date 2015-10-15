package com.example.keith.fyp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.views.adapters.AttributeValueListAdapter;
import com.example.keith.fyp.views.customviews.NotScrollableListView;

/**
 * ContentNewInfoObjectRenderer is {@link Renderer} to render the content of a {@link com.example.keith.fyp.models.Notification} with type {@link com.example.keith.fyp.models.Notification#TYPE_NEW_INFO_OBJECT}.
 * Info object is a group of patient information that are related to each other (e.g. patient's allergy which consist of name, reaction and notes)
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ContentNewInfoObjectRenderer extends ContentRenderer {

    private ObjectToAttributeValueTransformer newObject;

    /**
     * Create a content new info object renderer with the specified values.
     */
    public ContentNewInfoObjectRenderer(LayoutInflater inflater, ObjectToAttributeValueTransformer newObject) {
        super(inflater);
        this.newObject = newObject;
    }

    @Override
    public View render() {
        Context context = inflater.getContext();

        View rootView = inflater.inflate(R.layout.notification_detail_content_new_info_object_layout, null);

        NotScrollableListView newObjectListView = (NotScrollableListView) rootView.findViewById(R.id.new_object_list_view);
        newObjectListView.setAdapter(new AttributeValueListAdapter(context, newObject.transform()));

        return rootView;
    }
}
