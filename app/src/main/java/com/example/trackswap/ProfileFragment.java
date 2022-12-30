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
import com.example.trackswap.model.Track;
import com.example.trackswap.model.Model;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileFragment extends Fragment {
    List<Track> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //Firestore.instance().getPublishedTracks();
        data = Model.instance().getMyTracks(FirebaseAuth.getInstance().getCurrentUser().getUid());
        RecyclerView list = view.findViewById(R.id.tracklistfrag_list);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        TrackRecyclerAdapter adapter = new TrackRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);
        return view;
    }
}
