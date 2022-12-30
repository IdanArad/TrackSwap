package com.example.trackswap;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.Post;
import com.example.trackswap.model.ModelPosts;

import java.util.List;

public class PostListFragment extends Fragment {
    List<Post> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_list, container, false);
        Firestore.instance().getPublishedTracks();
        data = ModelPosts.instance().getAllPosts();
        RecyclerView list = view.findViewById(R.id.postlistfrag_list);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = data.get(pos);
       //          TracksListFragmentDirections.ActionTracksListFragmentToBlueFragment action = TracksListFragmentDirections.actionTracksListFragmentToBlueFragment(st.name);
       //         Navigation.findNavController(view).navigate(action);
            }
        });

       // View addButton = view.findViewById(R.id.tracklistfrag_add_btn);
      //  NavDirections action = TracksListFragmentDirections.actionGlobalAddTrackFragment();
       // addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return view;
    }
}