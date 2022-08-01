package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.R;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentPostBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PostFragment extends Fragment {

    private DatabaseReference postDatabaseReference;
    private ProfileModel model;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPostBinding binding = FragmentPostBinding.inflate(inflater, container, false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        postDatabaseReference = firebaseDatabase.getReference("Post");
        model = DashboardActivity.model;

        binding.btnSubmit.setOnClickListener(view -> {

            String strPost = binding.etPost.getText().toString().trim();

            if (TextUtils.isEmpty(strPost)) {
                binding.etPost.setError("Can't be Empty");
                return;
            }

            if (model != null) {

                HashMap<String, Object> map = new HashMap<>();
                map.put("post", strPost);
                map.put("name", model.getName());
                map.put("id", model.getId());
                map.put("imageUrl", model.getImageUrl());

                postDatabaseReference.push().setValue(map).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Post uploaded", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.postTohome);
                    }

                }).addOnFailureListener(e -> Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());


            }

        });

        return binding.getRoot();
    }
}