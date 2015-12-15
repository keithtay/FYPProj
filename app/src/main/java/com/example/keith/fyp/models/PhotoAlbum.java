package com.example.keith.fyp.models;

import android.graphics.Bitmap;

import com.example.keith.fyp.interfaces.ObjectToAttributeValueTransformer;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo Album is a model to represent a {@link Patient}'s Photo Album
 *
 * @author ks
 */
public class PhotoAlbum {

    private String albumName;
    private List<Bitmap> photoList;

    public PhotoAlbum(String albumName, List<Bitmap> photoList) {
        this.albumName = albumName;
        this.photoList = photoList;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Bitmap> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Bitmap> photoList) {
        this.photoList = photoList;
    }
}