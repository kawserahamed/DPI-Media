package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MessageFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMessageBinding binding = FragmentMessageBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Chat");
        ProfileModel model = MessageFragmentArgs.fromBundle(getArguments()).getProfileData();


        binding.btnSend.setOnClickListener(view -> {
            String massageId = databaseReference.push().getKey();
            String myId = DashboardActivity.model.getId();
            String strMessage = binding.textMessage.getText().toString();
            String strReceiver = model.getId();

            if (TextUtils.isEmpty(strMessage)) {
                binding.textMessage.setError("Can't be empty");
            } else {
                HashMap<String, Object> chatMap = new HashMap<>();
                chatMap.put("senderId", myId);
                chatMap.put("receiverId", strReceiver);
                chatMap.put("message", strMessage);
                chatMap.put("messageId", massageId);

                assert massageId != null;
                databaseReference.child(massageId).setValue(chatMap).addOnCompleteListener(task -> {
                    binding.textMessage.setText("");
                }).addOnFailureListener(e -> Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        return binding.getRoot();
    }
}