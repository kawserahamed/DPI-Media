package com.ahamed.dpichat.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahamed.dpichat.Adapter.ChatAdapter;
import com.ahamed.dpichat.Model.ChatModel;
import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.UI.DashboardActivity;
import com.ahamed.dpichat.databinding.FragmentMessageBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference chatDatabase;
    List<ChatModel> chatList;

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
        ProfileModel otherProfile = MessageFragmentArgs.fromBundle(getArguments()).getProfileData();
        chatList = new ArrayList<>();
        ProfileModel myProfile = DashboardActivity.model;

        Glide.with(binding.getRoot()).load(otherProfile.getImageUrl()).into(binding.ivOtherProfile);
        binding.ivOtherName.setText(otherProfile.getName());


        binding.btnSend.setOnClickListener(view -> {
            String massageId = databaseReference.push().getKey();
            String myId = myProfile.getId();
            String strMessage = binding.textMessage.getText().toString();
            String strReceiver = otherProfile.getId();

            if (TextUtils.isEmpty(strMessage)) {
                binding.textMessage.setError("Can't be empty");
            } else {
                HashMap<String, Object> chatMap = new HashMap<>();
                chatMap.put("senderId", myId);
                chatMap.put("receiverId", strReceiver);
                chatMap.put("message", strMessage);
                chatMap.put("messageId", massageId);

                assert massageId != null;
                databaseReference.child(massageId).setValue(chatMap).addOnCompleteListener(task ->
                        binding.textMessage.setText("")).addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });


        chatDatabase = FirebaseDatabase.getInstance().getReference("Chat");

        chatDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);

                    assert chatModel != null;
                    if (otherProfile.getId().equals(chatModel.getSenderId())
                            && chatModel.getReceiverId().equals(myProfile.getId())
                            || chatModel.getSenderId().equals(myProfile.getId())
                            && chatModel.getReceiverId().equals(otherProfile.getId())) {
                        chatList.add(chatModel);
                    }
                }
                ChatAdapter adapter = new ChatAdapter(getActivity(), chatList);
                binding.RcMessageViewId.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.RcMessageViewId.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }

}