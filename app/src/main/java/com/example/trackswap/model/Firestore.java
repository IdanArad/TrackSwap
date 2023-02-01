package com.example.trackswap.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Firestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final Firestore _instance = new Firestore(db);
    public static final String TAG = "Firestore";

    static {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }

    public static Firestore instance(){
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

    public static void editPost(String postId, String newDesc, OnCompleteListener<QuerySnapshot> listener) {
        Map<String, Object> updateMap= new HashMap<>();
        updateMap.put("desc", newDesc);
        db.collection("published_tracks")
                .document(postId)
                .update(updateMap);
    }
}
