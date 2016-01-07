package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo Album is a model to represent a {@link Patient}'s Photo Album
 *  //if not using Picasso, change List<string> to list<bitmap>. change all.
 * @author ks
 */
public class PhotoAlbum {

    private String albumName;
    private List<String> photoList;

    public PhotoAlbum(String albumName, List<String> photoList) {
        this.albumName = albumName;
        this.photoList = photoList;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }


}