package com.example.trackswap.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.trackswap.model.ModelPosts;
import com.example.trackswap.model.Post;

import java.util.List;

public class PostsListViewModel extends ViewModel {
    private LiveData<List<Post>> data = ModelPosts.instance().getAllPosts();

    public LiveData<List<Post>> getData() {
        return data;
    }
}
