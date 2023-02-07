package com.example.trackswap;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

public class DatabaseInstance {
    private static DatabaseInstance instance;
    private AppDatabase localDb;

    private DatabaseInstance(@NonNull Context context) {
        localDb = Room.databaseBuilder(context, AppDatabase.class, "published_tracks").build();
    }

    public static DatabaseInstance getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseInstance(context);
        }

        return instance;
    }
}
