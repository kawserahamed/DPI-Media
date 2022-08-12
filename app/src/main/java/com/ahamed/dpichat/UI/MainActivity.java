package com.ahamed.dpichat.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahamed.dpichat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        binding.tvLogIn.setOnClickListener(view -> {
            binding.layoutRegister.setVisibility(View.GONE);
            binding.LayoutLogIn.setVisibility(View.VISIBLE);
            binding.tvLogIn.setVisibility(View.GONE);
            binding.tvRegister.setVisibility(View.VISIBLE);
        });

        binding.tvRegister.setOnClickListener(view -> {
            binding.layoutRegister.setVisibility(View.VISIBLE);
            binding.LayoutLogIn.setVisibility(View.GONE);
            binding.tvLogIn.setVisibility(View.VISIBLE);
            binding.tvRegister.setVisibility(View.GONE);
        });

        binding.btnCreate.setOnClickListener(view -> {
            String nEmail = binding.regEmil.getText().toString();
            String nPass = binding.regPass.getText().toString();
            if (nEmail.equals("")) {
                binding.regEmil.setError("Can't be Empty");
                return;
            }
            if (nPass.equals("")) {
                binding.regPass.setError("Can't be Empty");
                return;
            }
            if (nEmail != null && nPass != null) {
                binding.progressBarId.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(nEmail, nPass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                        intent.putExtra("email", nEmail);
                        intent.putExtra("pass", nPass);
                        startActivity(intent);
                        finish();
                        binding.progressBarId.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(e -> {
                    binding.progressBarId.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });


            }
        });

        binding.btnLogIn.setOnClickListener(view -> {
            String strEmail = binding.logMail.getText().toString().trim();
            String srtPass = binding.logPass.getText().toString().trim();
            if (TextUtils.isEmpty(strEmail)) {
                binding.logMail.setError("Can't be Empty");
                return;
            }
            if (TextUtils.isEmpty(srtPass)) {
                binding.logPass.setError("Can't be Empty");
                return;
            }
            binding.progressBarId.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(strEmail, srtPass).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    finish();
                    binding.progressBarId.setVisibility(View.GONE);
                }
            }).addOnFailureListener(e -> {
                binding.progressBarId.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            });
        });
    }
}