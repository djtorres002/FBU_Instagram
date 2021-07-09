package com.example.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.MainActivity;
import com.example.instagram.R;
import com.example.instagram.models.DetailsActivity;
import com.example.instagram.models.Post;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public static final String TAG = "PostAdapter";

    List<Post> postList;
    Context context;

    public PostAdapter (List<Post> postList, Context context){
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This creates the view for our recycler view
        View v = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        // This sets the data in our View
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        // This returns the number of posts in our list
        return postList.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        postList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        postList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // Variables from our xml
        TextView tvUser;
        TextView tvDescription;
        ImageView ivPicture;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUsername);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivPicture = itemView.findViewById(R.id.ivImage);
            tvTime = itemView.findViewById(R.id.tvTime);
        }



        public void bind(Post post){
            tvUser.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            Glide.with(context)
                    .load(post.getImage().getUrl())
                    .override(410, 250)
                    .centerCrop()
                    .into(ivPicture);
            ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "go to details");
                    goToDetails(post);
                }
            });
            Date createdAt = post.getCreatedAt();
            String timeAgo = Post.calculateTimeAgo(createdAt);
            tvTime.setText(timeAgo);
        }

        private void goToDetails(Post post) {
            Intent i = new Intent(context, DetailsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Using parcels to pass a post to detail activity
            // "post" is our key and post variable is our value that we are passing
            i.putExtra("post", Parcels.wrap(post));
            context.startActivity(i);
        }
    }
}
