package com.example.instagram.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.R;
import com.example.instagram.adapter.PostAdapter;
import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    Context context;
    List<Post> postList;
    RecyclerView rvPost;
    PostAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Context context) {
        HomeFragment fragment = new HomeFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPost = view.findViewById(R.id.rvPost);
        postList = new ArrayList<>();
        adapter = new PostAdapter(postList, context);
        rvPost.setLayoutManager(new LinearLayoutManager(context));
        rvPost.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rvPost.setAdapter(adapter);
        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                queryPosts();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    public void queryPosts(){
        // Create a query that gets the Post class
        ParseQuery<Post> query = ParseQuery.getQuery("Post");
        // Include the user data in our result
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATEDAT);
        // Execute our query
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    Log.d(TAG, "Post size: " + objects.size());
                    postList.clear();
                    postList.addAll(objects);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }else{
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }
}