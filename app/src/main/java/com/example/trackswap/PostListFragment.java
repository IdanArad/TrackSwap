package com.example.trackswap;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.trackswap.model.Post;
import com.example.trackswap.viewmodels.PostsListViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class PostListFragment extends Fragment {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressbar;
    List<Post> data;
    PostRecyclerAdapter adapter;
    private PostsListViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts_list, container, false);
        progressbar = view.findViewById(R.id.postlistfrag_progressBar);
        viewModel.getData().observe(getViewLifecycleOwner(), posts -> {
            data = posts;
            if (data.size() > 0) {
                progressbar.setVisibility(View.GONE);
            }
            RecyclerView list = view.findViewById(R.id.postlistfrag_list);
            list.setHasFixedSize(true);
            list.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new PostRecyclerAdapter(getLayoutInflater(), data);
            list.setAdapter(adapter);
        });
        return view;
    }
}