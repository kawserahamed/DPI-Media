package com.ahamed.dpichat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.databinding.FragmentPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PostFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference postDatabaseReference;
    private String currentId;
    private ProfileModel model;
    private FirebaseUser user;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPostBinding binding = FragmentPostBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile");
        postDatabaseReference = firebaseDatabase.getReference("Post");
        user = auth.getCurrentUser();
        if (user != null) {
            currentId = user.getUid();
        }

        databaseReference.child(currentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(ProfileModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnSubmit.setOnClickListener(view -> {

            String strPost = binding.etPost.getText().toString().trim();

            if (TextUtils.isEmpty(strPost)) {
                binding.etPost.setError("Can't be Empty");
                return;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("post", strPost);
            map.put("name", model.getName());
            map.put("id", model.getId());

            postDatabaseReference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Post uploaded", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.postTohome);
                    }

                }
            }).addOnFailureListener(e -> Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());


        });


        return binding.getRoot();
    }
}