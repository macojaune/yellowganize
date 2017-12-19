package com.marvinl.yellowganize;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Marvin on 16/12/2017.
 */
@Dao
public interface PostDao {
    @Query("Select * from post")
    List<Post> getAll();

    @Insert
    void insert(Post post);

    @Insert
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

}
