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

    public List<Track> getMyTracks(String uid) {
        List<Track> mytracks = new LinkedList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).publisher_uid.equals(uid)) {
                mytracks.add(data.get(i));
            }
        }
        return mytracks;
    }


    public boolean isExist(Track track) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).name.equals(track.name)) {
                    return true;
                }
            }
            return false;
    }

    public void addTrack(Track st){
        data.add(st);
    }
}
