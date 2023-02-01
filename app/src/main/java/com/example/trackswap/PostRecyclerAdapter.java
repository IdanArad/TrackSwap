package com.example.trackswap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.ModelPosts;
import com.example.trackswap.model.Post;
import com.example.trackswap.model.Track;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


class PostViewHolder extends RecyclerView.ViewHolder {
    TextView nameTv;
    TextView artistTv;
    TextView descTv;
    TextView publisherTv;
    Button editButton;
    String postId;

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.tracklistrow_name_tv);
        artistTv = itemView.findViewById(R.id.tracklistrow_id_tv);
        publisherTv = itemView.findViewById(R.id.publisher_name_tv);
        descTv = itemView.findViewById(R.id.tracklistrow_desc_tv);

        editButton = itemView.findViewById(R.id.edit_button);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Edit Post:");

                // Set up the input
                final EditText input = new EditText(view.getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(descTv.getText());
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        Firestore.editPost(postId, m_Text, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                descTv.setText(m_Text);
                            }
                        });
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

    }

    public void bind(Post post, int pos) {
        nameTv.setText(post.track.name);
        artistTv.setText(post.track.artist);
        descTv.setText(post.desc);
        DocumentReference docRef = Firestore.instance().getDb().collection("users").document(post.getPublisher_uid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String fieldValue = documentSnapshot.getString("displayname");
                    publisherTv.setText(fieldValue);
                } else {
                    Log.d("TAG", "Document does not exist");
                    publisherTv.setText("No User");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error getting document", e);
                publisherTv.setText("No User");
            }
        });

        if (post.getPublisher_uid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            editButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
        }
    }

    public void setId(String id) {
        this.postId = id;
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {
        View view = inflater.inflate(R.layout.post_list_row, parent, false);
        return new PostViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, position);
        holder.setId(data.get(position).id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Post> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public Post getItem(int position) {
        return data.get(position);
    }

}

