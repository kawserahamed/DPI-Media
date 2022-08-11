package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.ProfileAdapter;
import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentFriendsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FriendsFragment extends Fragment {

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFriendsBinding binding = FragmentFriendsBinding.inflate(inflater, container, false);
        ProfileAdapter adapter = new ProfileAdapter(DashboardActivity.profileList, getActivity());
        binding.rcProfileList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rcProfileList.setAdapter(adapter);
        return binding.getRoot();
    }
}