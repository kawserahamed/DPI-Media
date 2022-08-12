package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.databinding.FragmentFriendProfileBinding;
import com.bumptech.glide.Glide;

public class FriendProfile extends Fragment {
    public FriendProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFriendProfileBinding binding = FragmentFriendProfileBinding.inflate(inflater, container, false);
        ProfileModel otherProfile = FriendProfileArgs.fromBundle(getArguments()).getProfile();
        String nDep = otherProfile.getDepartment();
        String nPhone = "Phone : " + otherProfile.getPhone();
        String nReg = "Registration : " + otherProfile.getRegistration();
        String nRoll = "Roll : " + otherProfile.getRoll();
        String nEmail = "Email : " + otherProfile.getEmail();
        Glide.with(binding.getRoot()).load(otherProfile.getImageUrl()).into(binding.imageView);
        binding.tvDep.setText(nDep);
        binding.tvName.setText(otherProfile.getName());
        binding.tvEmail.setText(nEmail);
        binding.tvRoll.setText(nRoll);
        binding.tvReg.setText(nReg);
        binding.tvPhone.setText(nPhone);
        return binding.getRoot();
    }
}