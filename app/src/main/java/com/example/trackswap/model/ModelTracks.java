package com.example.trackswap.model;

import java.util.LinkedList;
import java.util.List;

public class ModelTracks {
    private static final ModelTracks _instance = new ModelTracks();
    List<Track> data;

    public static ModelTracks instance(){
        return _instance;
    }
    private ModelTracks(){
        data = new LinkedList<>();
    }

    public List<Track> getAllSongs(){
        return data;
    }
    public void clearSongs(){
        data = new LinkedList<>();
    }

    public boolean isExist(Track track) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).name.equals(track.name)) {
                    return true;
                }
            }
            return false;
    }

    public void addTrack(Track track){
        data.add(track);
    }
}
