package com.example.trackswap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackswap.model.ModelTracks;
import com.example.trackswap.model.Track;
import java.util.List;

public class TrackListFragment extends Fragment {
    List<Track> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracks_list, container, false);
        data = ModelTracks.instance().getAllSongs();
        RecyclerView list = view.findViewById(R.id.tracklistfrag_list);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        TrackRecyclerAdapter adapter = new TrackRecyclerAdapter(getLayoutInflater(), data);
        list.setAdapter(adapter);

        // View addButton = view.findViewById(R.id.tracklistfrag_add_btn);
        //  NavDirections action = TracksListFragmentDirections.actionGlobalAddTrackFragment();
        // addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return view;
    }
}
