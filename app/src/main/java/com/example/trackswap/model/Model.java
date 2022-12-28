package com.example.trackswap.model;

import java.util.LinkedList;
import java.util.List;
public class Model {
    private static final Model _instance = new Model();

    public static Model instance(){
        return _instance;
    }
    private Model(){
        for(int i=0;i<2;i++){
            addTrack(new Track("name " + i,""+i));
        }
    }

    List<Track> data = new LinkedList<>();
    public List<Track> getAllTracks(){
        return data;
    }

    public void addTrack(Track st){
        data.add(st);
    }

    public int getNextId(){
        return this.getAllTracks().size();
    }

}
