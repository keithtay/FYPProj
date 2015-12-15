package com.example.keith.fyp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.PhotoAlbum;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.utils.Global;

import java.util.Collections;
import java.util.List;

/**
 * Created by ks on 15/12/2015.
 */
public class PhotoAlbumTitleAdapter extends BaseAdapter {

    private List<PhotoAlbum> photoAlbumList = Collections.emptyList();
    private Context context;

    public PhotoAlbumTitleAdapter(Context context) {
        this.context = context;
    }

    public PhotoAlbumTitleAdapter(Context context, List<PhotoAlbum> photoAlbumList) {
        this.context = context;
        this.photoAlbumList = photoAlbumList;
    }

    @Override
    public int getCount() {
        return photoAlbumList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoAlbumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View photoAlbumItemView = convertView;

        if (photoAlbumItemView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            photoAlbumItemView = vi.inflate(R.layout.photo_album_item_layout, null);
        }

        PhotoAlbum photoAlbum = (PhotoAlbum) getItem(position);

        if (photoAlbum != null) {

            TextView photoAlbumTitleTextView = (TextView) photoAlbumItemView.findViewById(R.id.photo_album_title);
            GridView photosGridView = (GridView) photoAlbumItemView.findViewById(R.id.photos_grid_view);


            if(photoAlbumTitleTextView != null) {
                photoAlbumTitleTextView.setText(photoAlbum.getAlbumName());
            }

            if(photosGridView != null) {
                photosGridView.setAdapter(new PhotoListAdapter(context,photoAlbum.getPhotoList()));
            }
        }
        return photoAlbumItemView;
    }
}
