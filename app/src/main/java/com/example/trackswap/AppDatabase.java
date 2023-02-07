package com.example.trackswap;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.trackswap.model.Post;
import com.example.trackswap.model.PostDao;
import com.example.trackswap.model.Track;
import com.example.trackswap.model.User;
import com.example.trackswap.model.UserDao;

@Database(entities = {Post.class, Track.class, User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract UserDao userDao();
}
