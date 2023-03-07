package com.ahamed.dpichat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahamed.dpichat.Model.PostModel;
import com.ahamed.dpichat.Model.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
    FirebaseDatabase firebaseDatabase;
    private final DatabaseReference databaseReference;
    private final DatabaseReference postDatabaseReference;
    private final String currentId;


    public DataViewModel(@NonNull Application application) {
        super(application);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile");
        postDatabaseReference = firebaseDatabase.getReference("Post");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        currentId = user.getUid();
    }


    public LiveData<ProfileModel> getMyData() {
        MutableLiveData<ProfileModel> myData = new MutableLiveData<>();
        databaseReference.child(currentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileModel model = snapshot.getValue(ProfileModel.class);
                myData.postValue(model);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return myData;

    }

    public LiveData<List<PostModel>> getAllPost() {
        MutableLiveData<List<PostModel>> list = new MutableLiveData<>();

        postDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PostModel> postList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PostModel model = dataSnapshot.getValue(PostModel.class);
                    postList.add(model);
                }
                list.postValue(postList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return list;
    }

    public LiveData<List<ProfileModel>> getAllProfile() {
        MutableLiveData<List<ProfileModel>> liveData = new MutableLiveData<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProfileModel> profileList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProfileModel profileModel = dataSnapshot.getValue(ProfileModel.class);
                    if (!currentId.equals(profileModel.getId())) {
                        profileList.add(profileModel);
                    }
                }
                liveData.postValue(profileList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return liveData;
    }


}
