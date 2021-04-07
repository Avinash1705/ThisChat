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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thisapp.Adapters.ChatAdapter;
import com.example.thisapp.Models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupChat extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    EditText et_chatbottomGroup;
    ImageView iv_detailSendGroup;
    CircleImageView iv_detail_photoChatGroup;
    TextView tv_detail_nameChatGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        init();
        getSupportActionBar().hide();

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final  String GsenderId= FirebaseAuth.getInstance().getUid();
        tv_detail_nameChatGroup.setText("Fnds Chat Group");
        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
        recyclerView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //show db
        database.getReference().child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            MessageModel model=dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //send data to db
        iv_detailSendGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Gmessage=et_chatbottomGroup.getText().toString();
                MessageModel model=new MessageModel(GsenderId,Gmessage,new Date().getTime());
                et_chatbottomGroup.setText("");

                //db push
                database.getReference().child("Group Chat").push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(GroupChat.this, " Group db Active", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void GoBackFun(View view) {
        startActivity(new Intent(GroupChat.this,MainActivity.class));
    }
    private void init(){
        recyclerView=findViewById(R.id.Group_recyclerView);
        linearLayout=findViewById(R.id.Group_LinearLayout);
        et_chatbottomGroup=findViewById(R.id.et_chatbottomGroup);
        iv_detailSendGroup=findViewById(R.id.iv_detailSendGroup);
        iv_detail_photoChatGroup=findViewById(R.id.iv_detail_photoChatGroup);
        tv_detail_nameChatGroup=findViewById(R.id.tv_detail_nameChatGroup);
    }
}