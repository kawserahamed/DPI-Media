package com.ahamed.dpichat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ahamed.dpichat.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater, container, false);


        binding.floatingActionButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.newsToPostAction));
        return binding.getRoot();
    }
}