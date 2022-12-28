package com.example.trackswap.model;

public class Track {
    public String name;
    public String artist;
    public  String publisher_uid;

    public Track(String name, String artist, String publisher_uid) {
        this.name = name;
        this.artist = artist;
        this.publisher_uid = publisher_uid;
    }
}
