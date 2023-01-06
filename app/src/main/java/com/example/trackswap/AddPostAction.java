package com.example.trackswap;

import com.example.trackswap.model.Firestore;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPostAction {
    private String name;
    private String artist;
    private static final AddPostAction _instance = new AddPostAction("", "");
    public static AddPostAction instance(){
        return _instance;
    }
    public AddPostAction(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }
}
