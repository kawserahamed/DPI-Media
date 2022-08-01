package com.ahamed.dpichat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.UI.MainActivity;
import com.ahamed.dpichat.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

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

        ProfileModel model = DashboardActivity.model;
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


        binding.btnLogOut.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });


        return binding.getRoot();
    }
}