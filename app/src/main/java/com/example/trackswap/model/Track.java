package com.example.trackswap.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Track {
    @PrimaryKey
    @NonNull
    public String name;
    public String artist;

    public Track(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
}
