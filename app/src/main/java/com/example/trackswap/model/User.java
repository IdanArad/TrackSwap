package com.example.trackswap.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String uid;
    @ColumnInfo(name = "displayname")
    public String displayName;
    public String password;

}
