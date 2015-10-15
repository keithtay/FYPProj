package com.example.keith.fyp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;
import com.example.keith.fyp.utils.UtilsUi;
import com.example.keith.fyp.views.adapters.AttributeValueListAdapter;
import com.example.keith.fyp.views.customviews.NotScrollableListView;

import org.w3c.dom.Attr;

/**
 * ContentUpdateInfoObjectRenderer is {@link Renderer} to render the content of a {@link com.example.keith.fyp.models.Notification} with type {@link com.example.keith.fyp.models.Notification#TYPE_UPDATE_INFO_OBJECT}.
 * Info object is a group of patient information that are related to each other (e.g. patient's allergy which consist of name, reaction and notes).
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class ContentUpdateInfoObjectRenderer extends ContentRenderer {

    private ObjectToAttributeValueTransformer oldObject;
    private ObjectToAttributeValueTransformer newObject;

    /**
     * Create a content update info object renderer with the specified values.
     */
    public ContentUpdateInfoObjectRenderer(LayoutInflater inflater, ObjectToAttributeValueTransformer oldObject, ObjectToAttributeValueTransformer newObject) {
        super(inflater);
        this.oldObject = oldObject;
        this.newObject = newObject;
    }

    @Override
    public View render() {
        Context context = inflater.getContext();

        View rootView = inflater.inflate(R.layout.notification_detail_content_update_info_object_layout, null);

        NotScrollableListView oldObjectListView = (NotScrollableListView) rootView.findViewById(R.id.old_object_list_view);
        NotScrollableListView newObjectListView = (NotScrollableListView) rootView.findViewById(R.id.new_object_list_view);

        oldObjectListView.setAdapter(new AttributeValueListAdapter(context, oldObject.transform()));
        newObjectListView.setAdapter(new AttributeValueListAdapter(context, newObject.transform()));

        return rootView;
    }
}
