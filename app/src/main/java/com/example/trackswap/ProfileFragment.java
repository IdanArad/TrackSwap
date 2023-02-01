package com.example.trackswap;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.ModelPosts;
import com.example.trackswap.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        TextView textChangeName = view.findViewById(R.id.change_name_profile_id);
        textChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Change Your Name:");

                // Set up the input
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = input.getText().toString();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(newName)
                                .build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
                        updateDisplayName(newName);
                        textName.setText(newName);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);
        return view;
    }

    private void updateDisplayName(String newName) {
        Map<String, Object> data = new HashMap<>();
        data.put("displayname", newName);
        DocumentReference docRef = Firestore.instance().getDb().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Document update successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }
}
