package com.example.trackswap.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.trackswap.PostRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Firestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final Firestore _instance = new Firestore(db);
    public static final String TAG = "Firestore";

    public static Firestore instance(){
        return _instance;
    }
    public Firestore(FirebaseFirestore db) {
        this.db = db;
    }

    public FirebaseFirestore getDb() {
        return this.db;
    }

    public void getPublishedTracks() {
                db.collection("published_tracks")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        JSONObject jsonObject = new JSONObject(document.getData());
                                        try {
                                            Track currentTrack = new Track(jsonObject.getString("name"), jsonObject.getString("artist"));
                                            Post currentPost = new Post(currentTrack,jsonObject.getString("publisher_uid"), jsonObject.getString("desc"), document.getId());
                                            jsonObject.getString("artist");
                                            if (!ModelPosts.instance().isExist(currentPost)) {
                                                ModelPosts.instance().addPost(currentPost);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
    }
}
