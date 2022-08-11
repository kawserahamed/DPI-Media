package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.PostRecyclerView;
import com.ahamed.dpichat.Model.PostModel;
import com.ahamed.dpichat.R;
import com.ahamed.dpichat.databinding.FragmentNewsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private PostRecyclerView adapter;
    public List<PostModel> postList;
    private DatabaseReference postDatabaseReference;
    private FirebaseDatabase firebaseDatabase;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNewsBinding binding = FragmentNewsBinding.inflate(inflater, container, false);

        binding.floatingActionButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.newsToPostAction));

        firebaseDatabase = FirebaseDatabase.getInstance();

        postList = new ArrayList<>();
        postDatabaseReference = firebaseDatabase.getReference("Post");

        postDataLoad();

        adapter = new PostRecyclerView(getActivity(), postList);
        binding.rvPostRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvPostRV.setAdapter(adapter);


        return binding.getRoot();
    }

    private void postDataLoad() {
        postDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PostModel model = dataSnapshot.getValue(PostModel.class);
                    postList.add(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}