package com.example.thisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thisapp.Adapters.ChatAdapter;
import com.example.thisapp.Models.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetail extends AppCompatActivity {
    TextView mUsername;
    ImageView mBackBtn,iv_detailSend;
    CircleImageView iv_detail_photoChat;
    EditText  et_chatbottom;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView chatrecyclerView;
    private ArrayList<MessageModel> messageModels;
    private ChatAdapter chatAdapter;
   private   String senderId,receiverId,userName,profilePic,senderRoom,reciverRoom;
   //Who to whome send messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        init();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        //final make global

        senderId=auth.getUid();
         receiverId=getIntent().getStringExtra("userID");
         userName=getIntent().getStringExtra("userName");
         profilePic=getIntent().getStringExtra("userProfilePic");

        mUsername.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.user).into(iv_detail_photoChat);
        //inside chhat work
        messageModels =new ArrayList<>();
        chatAdapter=new ChatAdapter(messageModels,this);
        chatrecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        chatrecyclerView.setLayoutManager(layoutManager);
        //show in recycler view
//        ShowDataInRecyclerView();

        //Error // FIXME: 4/5/2021

        //send message
        senderRoom=senderId+receiverId;
        reciverRoom=receiverId+senderId;
        //recycler view data show
        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();//twice message deleted
                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                            MessageModel model=snapshot1.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        //instant update
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//send to db
        iv_detailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message =et_chatbottom.getText().toString();
                MessageModel  model=new MessageModel(senderId,message,new Date().getTime());
//                model.setTimestamp(new Date().getTime());
                et_chatbottom.setText("");
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatDetail.this, "Sender mgs stored in db", Toast.LENGTH_SHORT).show();
                                database.getReference().child("chats")
                                        .child(reciverRoom)
                                        .push()
                                        .setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ChatDetail.this, "Reciver mgs also in db", Toast.LENGTH_SHORT).show();
                                                //some work need
                                            }
                                        });
                            }
                        });
            }
        });





    }

    private void init() {
        mUsername=findViewById(R.id.tv_detail_nameChat);
        iv_detail_photoChat=findViewById(R.id.iv_detail_photoChat);
        mBackBtn=findViewById(R.id.iv_detail_backBtn);
        chatrecyclerView=findViewById(R.id.chatrecyclerView);
        et_chatbottom=findViewById(R.id.et_chatbottom);
        iv_detailSend=findViewById(R.id.iv_detailSend);
    }

    public void GoBackFunction(View view) {
        startActivity(new Intent(ChatDetail.this,MainActivity.class));
    }
}