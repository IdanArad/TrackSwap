package com.example.trackswap.model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Firestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final Firestore _instance = new Firestore(db);
    public static final String TAG = "Firestore";

    static {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }

    public static Firestore instance() {
        return _instance;
    }

    public Firestore(FirebaseFirestore db) {
        this.db = db;
    }

    public FirebaseFirestore getDb() {
        return this.db;
    }

    public static void fetchPosts(OnCompleteListener<QuerySnapshot> listener) {
        db.collection("published_tracks")
                .get()
                .addOnCompleteListener(listener);
    }

    public static void editPost(String postId, String newDesc, OnCompleteListener<Void> listener) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("desc", newDesc);
        db.collection("published_tracks")
                .document(postId)
                .update(updateMap).addOnCompleteListener(listener);
    }

    public static void editUserProfilePic(String userId, String imgUri, OnCompleteListener<Void> listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference ref
                = storageReference
                .child(
                        "images/"
                                + userId);
        ref.putBytes(imgUri.getBytes(StandardCharsets.UTF_8)).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to upload: ", e.getMessage());
            }

        });
    }
}
