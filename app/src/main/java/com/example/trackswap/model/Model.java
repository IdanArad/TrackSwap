package com.example.trackswap.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;
public class Model {
    private static final Model _instance = new Model();
    List<Track> data;

    public static Model instance(){
        return _instance;
    }
    private Model(){
        data = new LinkedList<>();
    }

    public List<Track> getAllTracks(){
        return data;
    }

    public void addTrack(Track st){
        data.add(st);
    }
}
