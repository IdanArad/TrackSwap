package com.example.trackswap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trackswap.model.Firestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class AddTrackFragment extends Fragment {
    private static final String TAG = "AddTrackFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addTrackFragment);
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
        View view = inflater.inflate(R.layout.fragment_add_track, container, false);

        EditText nameEt = view.findViewById(R.id.addtrack_name_et);
        Button saveBtn = view.findViewById(R.id.addtrack_save_btn);
        Button cancelBtn = view.findViewById(R.id.addtrack_cancell_btn);

        saveBtn.setOnClickListener(view1 -> {
            String name = nameEt.getText().toString();

            // Add a new document with a generated id.
            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("artist", "TRY");

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

          //  Model.instance().addTrack(new Track(name,"1"));
            Toast.makeText(getContext(),
                            "Publish Successful!",
                            Toast.LENGTH_LONG)
                    .show();
        });

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.tracksListFragment,false));
        return view;
    }

}