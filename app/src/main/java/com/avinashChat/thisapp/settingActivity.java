package com.avinashChat.thisapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avinashChat.thisapp.Models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class settingActivity extends AppCompatActivity {
    ImageView iv_setting_back, iv_setting_add, iv_setting_photo;
    EditText et_setting_about, et_setting_username;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    AppCompatButton bt_setting_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().hide();
        init();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        iv_setting_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");  //for all */*
                startActivityForResult(intent, 33);
            }
        });

        //error fix profile img disapper
        database.getReference().child("Users").child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        Picasso.get().load(users.getuProfilePic()).placeholder(R.drawable.user)
                                .into(iv_setting_photo);
                        //same username ,status errror
                        et_setting_about.setText(users.getStatus());
                        et_setting_username.setText(users.getuName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            Uri uriFile = data.getData();
            iv_setting_photo.setImageURI(uriFile);

            //+storing image also
            StorageReference storageReference = storage.getReference().child("profile picture")
                    .child(auth.getUid());
            storageReference.putFile(uriFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(settingActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    //get download link
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(auth.getUid())
                                    .child("profilepic").setValue(uri.toString());
                            Toast.makeText(settingActivity.this, "profile pic updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void init() {
        iv_setting_back = findViewById(R.id.iv_setting_back);
        iv_setting_add = findViewById(R.id.iv_setting_add);
        iv_setting_photo = findViewById(R.id.iv_setting_photo);
        bt_setting_save = findViewById(R.id.bt_setting_save);
        et_setting_username = findViewById(R.id.et_setting_username);
        et_setting_about = findViewById(R.id.et_setting_about);
    }

    public void GobackFromSetting(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void settingSaveFn(View view) {
        String username = et_setting_username.getText().toString();
        String about = et_setting_about.getText().toString();

        //for updating hashmap use
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("uName", username);
        obj.put("status", about);  //new node will auto create new
        database.getReference().child("Users").child(auth.getUid())
                .updateChildren(obj);
    }
}