package com.example.trackswap.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
    public String id;
    public String publisher_uid;
    public Track track;
    public String desc;

    public Post(Track track, String publisher_uid, String desc, String id) {
        this.track = track;
        this.publisher_uid = publisher_uid;
        this.desc = desc;
    }

    public String getId() {
        return this.id;
    }

    public String getPublisher_uid() {
        return this.publisher_uid;
    }

    public static void addPost(String name, String artist, String desc, String id) {
        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("artist", artist);
        data.put("desc", desc);
        data.put("publisher_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        Firestore.instance().getDb().collection("published_tracks").document(id).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Document has been added with custom ID");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public static void editPost(String newDesc, Post post) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", post.track.name);
        data.put("artist", post.track.artist);
        data.put("desc", newDesc);
        data.put("publisher_uid", post.getPublisher_uid());
        DocumentReference docRef = Firestore.instance().getDb().collection("published_tracks").document(post.getId());

        docRef.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Document update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }

}
