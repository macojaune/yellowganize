package com.marvinl.yellowganize;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Marvin on 16/12/2017.
 */
@Database(entities= {Post.class}, version=1)
public abstract class AppBdd extends RoomDatabase {
    public abstract PostDao postDao();
}
