package com.ahamed.dpichat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahamed.dpichat.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                Toast.makeText(DashboardActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
            }
        });


    }
}