package com.example.instagram.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.models.Post;


import com.example.instagram.MainActivity;
import com.example.instagram.R;
import com.example.instagram.models.Post;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = "DetailedActivity";

    TextView tvCreatedAt;
    Button btClose;
    ImageView ivPicture;
    TextView tvCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        btClose = findViewById(R.id.btClose);
        ivPicture = findViewById(R.id.ivPicture);
        tvCaption = findViewById(R.id.tvCaption);

        // Getting the post we passed from our adapter in to this activity
        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvCaption.setText(post.getDescription());
        Glide.with(this)
                .load(post.getImage().getUrl())
                .override(410, 250)
                .centerCrop()
                .into(ivPicture);

        // Converts the date we get from parse to a day in the form like August 10 2021
        Date date = post.getCreatedAt();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        String dateStr = format.format(date);
        tvCreatedAt.setText(dateStr);

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close details button");
                backToMain();
            }
        });
    }

    private void backToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}