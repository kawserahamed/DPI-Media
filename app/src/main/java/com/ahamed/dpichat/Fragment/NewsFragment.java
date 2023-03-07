package com.ahamed.dpichat.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.PostRecyclerView;
import com.ahamed.dpichat.Model.PostModel;
import com.ahamed.dpichat.R;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentNewsBinding;
import com.ahamed.dpichat.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    public NewsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater, container, false);
        binding.floatingActionButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.newsToPostAction));
        DataViewModel viewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        List<PostModel> list = new ArrayList<>();
        PostRecyclerView adapter = new PostRecyclerView(getActivity(), list);
        binding.rvPostRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvPostRV.setAdapter(adapter);

        viewModel.getAllPost().observe(getViewLifecycleOwner(), postModels -> {
            list.clear();
            list.addAll(postModels);
            adapter.notifyDataSetChanged();
        });


        return binding.getRoot();
    }
}