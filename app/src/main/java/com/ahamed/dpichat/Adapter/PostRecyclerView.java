package com.ahamed.dpichat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahamed.dpichat.Model.PostModel;
import com.ahamed.dpichat.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class PostRecyclerView extends RecyclerView.Adapter<PostRecyclerView.PostViewHolder> {


    Context context;
    List<PostModel> postList;

    public PostRecyclerView(Context context, List<PostModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    public PostRecyclerView() {
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel model = postList.get(position);
        holder.idName.setText(model.getName());
        holder.post.setText(model.getPost());

        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView idName;
        TextView post;
        ImageView imageView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            idName = itemView.findViewById(R.id.tv_userName);
            post = itemView.findViewById(R.id.tv_post);
            imageView = itemView.findViewById(R.id.circleImageView);
        }
    }
}
