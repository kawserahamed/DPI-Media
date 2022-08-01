package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.PostRecyclerView;
import com.ahamed.dpichat.R;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    private PostRecyclerView adapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater, container, false);

        binding.floatingActionButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.newsToPostAction));

        adapter = new PostRecyclerView(getActivity(), DashboardActivity.postList);
        binding.rvPostRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvPostRV.setAdapter(adapter);


        return binding.getRoot();
    }

}