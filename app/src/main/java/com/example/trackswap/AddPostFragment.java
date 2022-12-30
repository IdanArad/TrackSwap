package com.example.trackswap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.ModelTracks;
import com.example.trackswap.model.Track;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPostFragment extends Fragment {
    private static final String TAG = "AddPostFragment";
    private static final String API_KEY = "10110a7d3dde85354cb9b949bb84bef6";
    List<Track> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addPostFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   //     ModelTracks.instance().clearSongs();
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        SearchView nameSv = view.findViewById(R.id.search_view);



        nameSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ModelTracks.instance().clearSongs();
                searchSongs(query);
                View trackview = inflater.inflate(R.layout.fragment_tracks_list, container, false);
                RecyclerView list = trackview.findViewById(R.id.tracklistfrag_list);
                list.setHasFixedSize(true);
                list.setLayoutManager(new LinearLayoutManager(getContext()));
                data = ModelTracks.instance().getAllSongs();
                TrackRecyclerAdapter adapter = new TrackRecyclerAdapter(getLayoutInflater(),data);
                list.setAdapter(adapter);
                adapter.setData(data);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // You can optionally add code here to update the search results as the user types
                return false;
            }
        });


        //EditText nameEt = view.findViewById(R.id.addtrack_name_et);
        Button saveBtn = view.findViewById(R.id.addtrack_save_btn);

        saveBtn.setOnClickListener(view1 -> {
            String name = nameSv.toString();

            // Add a new document with a generated id.
            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("artist", "TRY");
            data.put("publisher_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

            Firestore.instance().getDb().collection("published_tracks")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

          //  ModelPosts.instance().Track(new Track(name,"1"));
            Toast.makeText(getContext(),
                            "Publish Successful!",
                            Toast.LENGTH_LONG)
                    .show();
        });

        return view;
    }

    private void searchSongs(String query) {
        Thread gfgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    String baseUrl = "https://ws.audioscrobbler.com/2.0/";
                    String method = "track.search";
                    String track = query;
                    String artist = query;
                    String apiKey = API_KEY;
                    String format = "json";

                    String url = baseUrl + "?method=" + method + "&track=" + track + "&artist=" + artist + "&api_key=" + apiKey + "&format=" + format;

                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) new URL(url).openConnection();
                        connection.setRequestMethod("GET");

                        // Read the response from the LastFM API
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // Parse the response into a Java object using a JSON parser
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONObject trackSearchResult = jsonObject.getJSONObject("results").getJSONObject("trackmatches");
                        JSONArray tracks = trackSearchResult.getJSONArray("track");
                        Log.d(TAG, "Got Tracks!: " + tracks.toString());
                        JSONObject currentTrackJson;
                        Track newTrack;
                        for (int i=0; i < tracks.length(); i++) {
                            currentTrackJson = tracks.getJSONObject(i);
                            newTrack = new Track(currentTrackJson.getString("name"),  currentTrackJson.getString("artist"));
                            if (!ModelTracks.instance().isExist(newTrack)) {
                                ModelTracks.instance().addTrack(newTrack);
                            }
                        }

                        // Do something with the list of tracks
                    } catch (IOException | JSONException e) {
                        System.out.println(e);
                        Log.d(TAG, "Got error: " + e);

                        // Handle the error
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        gfgThread.start();
    }

}