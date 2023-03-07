package com.ahamed.dpichat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.R;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.UI.MainActivity;
import com.ahamed.dpichat.databinding.FragmentProfileBinding;
import com.ahamed.dpichat.viewmodel.DataViewModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private FirebaseAuth auth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        DataViewModel viewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

        viewModel.getMyData().observe(getViewLifecycleOwner(), profileModel -> {
            String nDep = profileModel.getDepartment();
            String nPhone = "Phone : " + profileModel.getPhone();
            String nReg = "Registration : " + profileModel.getRegistration();
            String nRoll = "Roll : " + profileModel.getRoll();
            String nEmail = "Email : " + profileModel.getEmail();
            binding.tvName.setText(profileModel.getName());
            binding.tvDep.setText(nDep);
            binding.tvPhone.setText(nPhone);
            binding.tvReg.setText(nReg);
            binding.tvRoll.setText(nRoll);
            binding.tvEmail.setText(nEmail);
            Glide.with(binding.getRoot())
                    .load(profileModel.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.image)
                    .into(binding.imageView);

        });

        binding.btnLogOut.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });
        return binding.getRoot();
    }
}