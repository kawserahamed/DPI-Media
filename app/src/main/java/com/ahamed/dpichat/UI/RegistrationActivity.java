package com.ahamed.dpichat.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahamed.dpichat.databinding.ActivityRegistrationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

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
    private Uri imageUri;
    int RESULT_LOAD_IMG = 78;
    private StorageReference storageReference;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profile");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        binding.ivImageView.setOnClickListener(view -> getImageFromAlbum());

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

            if (imageUri != null) {
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

                ref.putFile(imageUri).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {

                    imageURL = String.valueOf(uri);
                    HashMap<String, Object> myMap = new HashMap<>();
                    myMap.put("id", id);
                    myMap.put("email", email);
                    myMap.put("password", pass);
                    myMap.put("name", name);
                    myMap.put("department", department);
                    myMap.put("roll", roll);
                    myMap.put("registration", reg);
                    myMap.put("phone", phone);
                    myMap.put("imageUrl", imageURL);

                    databaseReference.child(id).setValue(myMap).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                            Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());

                })).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }).addOnProgressListener(taskSnapshot -> {
                    double progress
                            = (100.0
                            * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                });


            }

        });


    }

    private void getImageFromAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                RESULT_LOAD_IMG);

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (reqCode == RESULT_LOAD_IMG
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getContentResolver(), imageUri);
                binding.ivImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}