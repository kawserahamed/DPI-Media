package com.ahamed.dpichat.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahamed.dpichat.Model.ChatModel;
import com.ahamed.dpichat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    FirebaseUser currentUser;
    static final int RIGHT = 0;
    static final int LEFT = 1;
    Context context;
    List<ChatModel> chatList;

    public ChatAdapter(Context context, List<ChatModel> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RIGHT) {
            View rightView = LayoutInflater.from(context).inflate(R.layout.layout_right_chat, parent, false);
            return new ChatViewHolder(rightView);
        } else {
            View leftView = LayoutInflater.from(context).inflate(R.layout.left_chat, parent, false);
            return new ChatViewHolder(leftView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatModel model = chatList.get(position);
        holder.message.setText(model.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentId = null;
        if (currentUser != null) {
            currentId = currentUser.getUid();
        }
        if (chatList.get(position).getSenderId().equals(currentId)) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tvChatMessage);
        }
    }
}
