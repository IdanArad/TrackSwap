package com.example.trackswap;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.ModelPosts;
import com.example.trackswap.model.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.trackswap.viewmodels.PostsListViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {
    List<Post> data;
    PostsListViewModel viewModel = new PostsListViewModel();
    ImageView profilePicView;
    int SELECT_PICTURE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        Firestore.instance().fetchPosts();
        data = ModelPosts.instance().getMyPosts(FirebaseAuth.getInstance().getCurrentUser().getUid(), viewModel.getData().getValue());
        RecyclerView list = view.findViewById(R.id.tracklistfrag_list);
        list.setHasFixedSize(true);

        TextView textName = view.findViewById(R.id.name_profile_id);
        textName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        ImageView profilePicImageView = view.findViewById(R.id.profile_pic);
        profilePicView = profilePicImageView;
        TextView profilePicChange = view.findViewById(R.id.change_pic_profile_id);
        profilePicChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });


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
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(), data);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            String encodedImage= "";
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImageUri);
                        encodedImage = encodeImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    profilePicView.setImageURI(selectedImageUri);
                    Firestore.editUserProfilePic(FirebaseAuth.getInstance().getCurrentUser().getUid(), encodedImage, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(),
                                            "Picture Changed successfully",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        }


    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}
