package com.example.trackswap;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.Post;
import com.example.trackswap.model.ModelPosts;
import com.example.trackswap.model.Track;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostListFragment extends Fragment {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressbar;
    List<Post> data;
    PostRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts_list, container, false);
        progressbar = view.findViewById(R.id.postlistfrag_progressBar);
        List<Post> fetchedPosts = new ArrayList<>();
        db.collection("published_tracks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(Firestore.TAG, document.getId() + " => " + document.getData());
                        JSONObject jsonObject = new JSONObject(document.getData());
                        try {
                            Track currentTrack = new Track(jsonObject.getString("name"), jsonObject.getString("artist"));
                            Post currentPost = new Post(currentTrack, jsonObject.getString("publisher_uid"), jsonObject.getString("desc"), document.getId());
                            fetchedPosts.add(currentPost);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    data = fetchedPosts;
                    ModelPosts.instance().setPosts(fetchedPosts);
                    progressbar.setVisibility(View.GONE);
                    RecyclerView list = view.findViewById(R.id.postlistfrag_list);
                    list.setHasFixedSize(true);
                    list.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new PostRecyclerAdapter(getLayoutInflater(), data);
                    list.setAdapter(adapter);
                    adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            Log.d("TAG", "Row was clicked " + pos);
                            Post post = data.get(pos);
                        }
                    });
                } else {
                    Log.d(Firestore.TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return view;
    }

    public void refreshDataSet() {
        this.adapter.setData(ModelPosts.instance().getAllPosts());
        this.adapter.notifyDataSetChanged();
    }
}