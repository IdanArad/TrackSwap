package com.example.trackswap.model;

import java.util.LinkedList;
import java.util.List;
public class ModelPosts {
    private static final ModelPosts _instance = new ModelPosts();
    List<Post> data;

    public static ModelPosts instance(){
        return _instance;
    }
    private ModelPosts(){
        data = new LinkedList<>();
    }

    public List<Post> getAllPosts(){
        return data;
    }
    public void clearSongs(){
        data = new LinkedList<>();
    }

    public List<Post> getMyPosts(String uid) {
        List<Post> myposts = new LinkedList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).publisher_uid.equals(uid)) {
                myposts.add(data.get(i));
            }
        }
        return myposts;
    }


    public boolean isExist(Post post) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).track.name.equals(post.track.name) &&
                        data.get(i).track.artist.equals(post.track.artist) &&
                        data.get(i).publisher_uid.equals(post.publisher_uid) &&
                        data.get(i).desc.equals(post.desc)) {
                    return true;
                }
            }
            return false;
    }

    public void setPosts(List<Post> posts) {
        data = posts;
    }

    public void addPost(Post post){
        data.add(post);
    }
}
