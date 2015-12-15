package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.keith.fyp.R;

import java.util.List;

/**
 * Created by ks on 15/12/2015.
 */
public class PhotoListAdapter extends BaseAdapter{

    private List<Bitmap> photoList;
    private Context context;

    public PhotoListAdapter(Context context) {
        this.context = context;
    }

    public PhotoListAdapter(Context context, List<Bitmap> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View photoItemView = convertView;

        if (photoItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            photoItemView = vi.inflate(R.layout.photo_album_individual_photo_layout, null);
        }

        Bitmap photo = (Bitmap) getItem(position);

        if (photo != null) {
            ImageView photoImageView = (ImageView) photoItemView.findViewById(R.id.photo_image_view);

            if(photoImageView != null) {
                photoImageView.setImageBitmap(photo);
            }
        }

        return photoItemView;
    }
}
