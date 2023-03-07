package com.ahamed.dpichat.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.ProfileAdapter;
import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.databinding.FragmentFriendsBinding;
import com.ahamed.dpichat.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    public FriendsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFriendsBinding binding = FragmentFriendsBinding.inflate(inflater, container, false);
        List<ProfileModel> list = new ArrayList<>();
        ProfileAdapter adapter = new ProfileAdapter(list, getActivity());
        binding.rcProfileList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rcProfileList.setAdapter(adapter);

        DataViewModel viewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        viewModel.getAllProfile().observe(getViewLifecycleOwner(), profileModels -> {
            list.clear();
            list.addAll(profileModels);
            adapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }
}