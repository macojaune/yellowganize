package com.marvinl.yellowganize;

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

    @Delete
    void delete(Post post);
}
