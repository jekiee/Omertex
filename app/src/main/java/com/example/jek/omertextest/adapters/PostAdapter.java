package com.example.jek.omertextest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jek.omertextest.R;
import com.example.jek.omertextest.ResultView;
import com.example.jek.omertextest.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> listPosts = new ArrayList<>();

    public PostAdapter(List<Post> listPosts) {
        this.listPosts = listPosts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_preview, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = listPosts.get(position);
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewUrl.setText(post.getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                intent = new Intent(holder.context, ResultView.class);
                intent.putExtra("URL", post.getUrl());
                intent.putExtra("TITLE", post.getTitle());
                intent.putExtra("BODY", post.getBody());
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
//        TextView textViewBody;
        TextView textViewUrl;
        private final Context context;

        PostViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textViewTitle = itemView.findViewById(R.id.tvTitle);
//            textViewBody = itemView.findViewById(R.id.tvBody);
            textViewUrl = itemView.findViewById(R.id.tvUrl);
        }
    }
}
