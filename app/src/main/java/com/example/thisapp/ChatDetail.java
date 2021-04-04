package com.example.thisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetail extends AppCompatActivity {
    TextView mUsername;
    ImageView mBackBtn,iv_detailSend;
    CircleImageView iv_detail_photoChat;
    EditText  et_chatbottom;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        init();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        String senderId=auth.getUid();
        String receiverId=getIntent().getStringExtra("userID");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("userProfilePic");

        mUsername.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.user).into(iv_detail_photoChat);

    }

    private void init() {
        mUsername=findViewById(R.id.tv_detail_nameChat);
        iv_detail_photoChat=findViewById(R.id.iv_detail_photoChat);
        mBackBtn=findViewById(R.id.iv_detail_backBtn);
    }

    public void GoBackFunction(View view) {
        startActivity(new Intent(ChatDetail.this,MainActivity.class));
    }
}