package com.example.trackswap.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class ModelPosts {
    private static final ModelPosts _instance = new ModelPosts();
    final MutableLiveData<List<Post>> data = new
            MutableLiveData<>();


    public static ModelPosts instance(){
        return _instance;
    }

    public List<Post> getMyPosts(String uid, List<Post> allPosts) {
        List<Post> myposts = new LinkedList<>();
        for (int i = 0; i < allPosts.size(); i++) {
            if (allPosts.get(i).publisher_uid.equals(uid)) {
                myposts.add(allPosts.get(i));
            }
        }
        return myposts;
    }

//    public boolean isExist(Post post) {
//            for (int i = 0; i < data.size(); i++) {
//                if (data.get(i).track.name.equals(post.track.name) &&
//                        data.get(i).track.artist.equals(post.track.artist) &&
//                        data.get(i).publisher_uid.equals(post.publisher_uid) &&
//                        data.get(i).desc.equals(post.desc)) {
//                    return true;
//                }
//            }
//            return false;
//    }

//    public void addPost(Post post){
//        data.add(post);
//    }

    class PostListData extends MutableLiveData<List<Post>>{
        @Override
        protected void onActive() {
            super.onActive();
            Firestore.fetchPosts(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<Post> fetchedPosts = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(Firestore.TAG, document.getId() + " => " + document.getData());
                        JSONObject jsonObject = new JSONObject(document.getData());
                        try {
                            Track currentTrack = new Track(jsonObject.getString("name"), jsonObject.getString("artist"));
                            Post currentPost = new Post(currentTrack, jsonObject.getString("publisher_uid"), jsonObject.getString("desc"), document.getId());
                            fetchedPosts.add(currentPost);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    setValue(fetchedPosts);
                }
            });
        }
        @Override
        protected void onInactive() {
            super.onInactive();
//            modelFirebase.cancellGetAllPosts();
            Log.d("TAG","cancellGetAllPosts");
        }
        public PostListData() {
            super();
            setValue(new LinkedList<Post>());
        }
    }

    PostListData postListData = new PostListData();
    public LiveData<List<Post>> getAllPosts(){
        return postListData;
    }


}
