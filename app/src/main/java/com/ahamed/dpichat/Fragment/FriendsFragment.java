package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.ProfileAdapter;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentFriendsBinding;

public class FriendsFragment extends Fragment {


    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentFriendsBinding binding = FragmentFriendsBinding.inflate(inflater, container, false);
        ProfileAdapter adapter = new ProfileAdapter(DashboardActivity.profileList, getActivity());
        binding.rcProfileList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rcProfileList.setAdapter(adapter);

        return binding.getRoot();
    }
}