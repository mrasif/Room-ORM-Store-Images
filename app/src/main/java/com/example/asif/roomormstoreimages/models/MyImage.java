package com.example.asif.roomormstoreimages.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by asif on 12/24/17.
 */
@Entity
public class MyImage {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public MyImage(String title, String photo, byte[] image) {
        this.title = title;
        this.photo = photo;
        this.image=image;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
