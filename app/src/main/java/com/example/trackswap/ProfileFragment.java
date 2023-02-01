package com.example.trackswap;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.ModelPosts;
import com.example.trackswap.model.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileFragment extends Fragment {
    List<Post> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Firestore.instance().getPublishedTracks();
        data = ModelPosts.instance().getMyPosts(FirebaseAuth.getInstance().getCurrentUser().getUid());
        RecyclerView list = view.findViewById(R.id.tracklistfrag_list);
        list.setHasFixedSize(true);

        TextView textName = view.findViewById(R.id.name_profile_id);
        textName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);
        return view;
    }
}
