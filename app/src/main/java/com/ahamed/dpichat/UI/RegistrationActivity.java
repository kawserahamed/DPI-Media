package com.ahamed.dpichat.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahamed.dpichat.databinding.ActivityRegistrationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    private DatabaseReference databaseReference;
    private String id;
    private String email;
    private String pass;
    private String name;
    private String department;
    private String roll;
    private String reg;
    private String phone;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile");

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("plz Wait... ...");


        binding.btnRegister.setOnClickListener(view -> {

            if (auth.getCurrentUser() != null) {
                id = auth.getCurrentUser().getUid();
            }
            email = getIntent().getStringExtra("email");
            pass = getIntent().getStringExtra("pass");

            name = binding.regName.getText().toString().trim();
            department = binding.regDep.getSelectedItem().toString().trim();
            roll = binding.regRoll.getText().toString().trim();
            reg = binding.regReg.getText().toString().trim();
            phone = binding.regPhone.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.regName.setError("Can't be Empty");
                return;

            } else if (TextUtils.isEmpty(roll)) {
                binding.regRoll.setError("Can't be Empty");
                return;

            } else if (TextUtils.isEmpty(reg)) {
                binding.regReg.setError("Can't be Empty");
                return;

            } else if (TextUtils.isEmpty(phone)) {
                binding.regPhone.setError("Can't be Empty");
                return;
            }

            HashMap<String, Object> myMap = new HashMap<>();
            myMap.put("id", id);
            myMap.put("email", email);
            myMap.put("password", pass);
            myMap.put("name", name);
            myMap.put("department", department);
            myMap.put("roll", roll);
            myMap.put("registration", reg);
            myMap.put("phone", phone);

            progressDialog.show();

            databaseReference.child(id).setValue(myMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }
            }).addOnFailureListener(e -> Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

            progressDialog.dismiss();
        });


    }
}