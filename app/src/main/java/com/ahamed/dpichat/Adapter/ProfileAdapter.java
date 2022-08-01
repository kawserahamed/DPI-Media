package com.ahamed.dpichat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    List<ProfileModel> profileList;
    Context context;

    public ProfileAdapter(List<ProfileModel> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }

    public ProfileAdapter() {
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

        ProfileModel model = profileList.get(position);
        holder.title.setText(model.getName());
        holder.department.setText(model.getDepartment());
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView department;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.Iv_profile);
            title = itemView.findViewById(R.id.tv_profile_title);
            department = itemView.findViewById(R.id.tv_profileDepartment);

        }
    }
}
