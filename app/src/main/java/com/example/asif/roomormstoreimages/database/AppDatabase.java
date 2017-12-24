package com.example.asif.roomormstoreimages.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.asif.roomormstoreimages.dao.MyImageDao;
import com.example.asif.roomormstoreimages.models.MyImage;

/**
 * Created by asif on 12/24/17.
 */

@Database(entities = {MyImage.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyImageDao myImageDao();
}