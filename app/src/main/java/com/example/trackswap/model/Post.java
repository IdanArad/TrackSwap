package com.example.trackswap.model;

public class Post {
    public String publisher_uid;
    public Track track;
    public String desc;

    public Post(Track track, String publisher_uid, String desc) {
        this.track = track;
        this.publisher_uid = publisher_uid;
        this.desc = desc;
    }
}
