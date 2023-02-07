package com.example.trackswap.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    LiveData<List<Post>> getAll();
}
