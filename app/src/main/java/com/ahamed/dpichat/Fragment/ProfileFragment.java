package com.ahamed.dpichat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.UI.MainActivity;
import com.ahamed.dpichat.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private String currentId;
    private ProfileModel model;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile");
        user = auth.getCurrentUser();

        if (user != null) {
            currentId = user.getUid();
        }


        databaseReference.child(currentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(ProfileModel.class);
                if (model != null) {
                    String nDep = model.getDepartment();
                    String nPhone = "Phone : " + model.getPhone();
                    String nReg = "Registration : " + model.getRegistration();
                    String nRoll = "Roll : " + model.getRoll();

                    binding.tvName.setText(model.getName());
                    binding.tvDep.setText(nDep);
                    binding.tvPhone.setText(nPhone);
                    binding.tvReg.setText(nReg);
                    binding.tvRoll.setText(nRoll);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });


        return binding.getRoot();
    }
}