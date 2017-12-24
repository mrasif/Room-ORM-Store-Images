package com.example.asif.roomormstoreimages.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.asif.roomormstoreimages.models.MyImage;

import java.util.List;

/**
 * Created by asif on 12/24/17.
 */
@Dao
public interface MyImageDao {
    @Query("SELECT * FROM myimage")
    List<MyImage> getAll();

    @Query("SELECT * FROM myimage ORDER BY UID DESC LIMIT 0,1")
    MyImage last();

    @Query("SELECT * FROM myimage ORDER BY UID ASC LIMIT 0,1")
    MyImage first();

    @Insert
    void insertAll(MyImage... myImages);

    @Delete
    void delete(MyImage myImage);
}
