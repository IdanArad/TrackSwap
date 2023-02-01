package com.example.trackswap.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
    public String publisher_uid;
    public Track track;
    public String desc;

    public Post(Track track, String publisher_uid, String desc) {
        this.track = track;
        this.publisher_uid = publisher_uid;
        this.desc = desc;
    }

    public static void addPost(String name, String artist, String desc) {
        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("artist", artist);
        data.put("desc", desc);
        data.put("publisher_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        Firestore.instance().getDb().collection("published_tracks")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(Firestore.TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(Firestore.TAG, "Error adding document", e);
                    }
                });
    }

}
