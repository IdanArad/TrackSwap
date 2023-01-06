package com.example.trackswap.model;

import android.os.AsyncTask;

import com.example.trackswap.PostRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GetAllPostsTask extends AsyncTask<Void, Void, List<Post>> {
    PostRecyclerAdapter adapter;
    ArrayList<Post> values = new ArrayList<>();

    public GetAllPostsTask(PostRecyclerAdapter newAdapter) {
        this.adapter = newAdapter;
    }

    @Override
    protected List<Post> doInBackground(Void... voids) {
        ArrayList<Post> result = new ArrayList<>();
        // long operation, for example: get results from url
        return result;
    }

    @Override
    protected void onPostExecute(List<Post> list) {
        adapter.setData(list);
    }


}