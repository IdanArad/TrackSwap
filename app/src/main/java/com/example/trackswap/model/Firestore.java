package com.example.trackswap.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static void fetchPosts( OnCompleteListener<QuerySnapshot> listener) {
                db.collection("published_tracks")
                        .get()
                        .addOnCompleteListener(listener);
    }
}
