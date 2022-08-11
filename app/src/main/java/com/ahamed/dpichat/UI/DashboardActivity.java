package com.ahamed.dpichat.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.ahamed.dpichat.Model.PostModel;
import com.ahamed.dpichat.Model.ProfileModel;
import com.ahamed.dpichat.R;
import com.ahamed.dpichat.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private DatabaseReference databaseReference;
    private DatabaseReference postDatabaseReference;
    private String currentId;
    public static ProfileModel model;
    public static List<PostModel> postList;
    public static List<ProfileModel> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(binding.bottomNavId, navHostFragment.getNavController());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile");
        postDatabaseReference = firebaseDatabase.getReference("Post");
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            currentId = user.getUid();
        }
        profileDataLoad();
        postList = new ArrayList<>();
        profileList = new ArrayList<>();
//        postDataLoad();
        profileDataListLoad();
    }

    private void profileDataLoad() {

        databaseReference.child(currentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(ProfileModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

  /*  private void postDataLoad() {
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
    }*/

    private void profileDataListLoad() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProfileModel profileModel = dataSnapshot.getValue(ProfileModel.class);
                    if (!currentId.equals(profileModel.getId())) {
                        profileList.add(profileModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}