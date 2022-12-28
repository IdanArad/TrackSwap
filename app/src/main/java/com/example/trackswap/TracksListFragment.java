package com.example.trackswap;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackswap.model.Track;
import com.example.trackswap.model.Model;

import java.util.List;

public class TracksListFragment extends Fragment {
    List<Track> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracks_list, container, false);
        data = Model.instance().getAllTracks();
        RecyclerView list = view.findViewById(R.id.tracklistfrag_list);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        TrackRecyclerAdapter adapter = new TrackRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new TrackRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Track st = data.get(pos);
       //          TracksListFragmentDirections.ActionTracksListFragmentToBlueFragment action = TracksListFragmentDirections.actionTracksListFragmentToBlueFragment(st.name);
       //         Navigation.findNavController(view).navigate(action);
            }
        });

        View addButton = view.findViewById(R.id.tracklistfrag_add_btn);
      //  NavDirections action = TracksListFragmentDirections.actionGlobalAddTrackFragment();
       // addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return view;
    }
}