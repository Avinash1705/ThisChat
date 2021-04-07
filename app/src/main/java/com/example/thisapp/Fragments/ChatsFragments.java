package com.example.thisapp.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thisapp.Adapters.UsersAdapter;
import com.example.thisapp.Models.Users;
import com.example.thisapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragments extends Fragment {
    ArrayList<Users> list=new ArrayList<>();
    RecyclerView recyclerViewChats;
    FirebaseDatabase database;


    public ChatsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        // Inflate the layout for this fragment

        view =inflater.inflate(R.layout.fragment_chats_fragments, container, false);
        recyclerViewChats=view.findViewById(R.id.recycler_chatFrag);
        database=FirebaseDatabase.getInstance();

        UsersAdapter usersAdapter=new UsersAdapter(list,getContext());
        recyclerViewChats.setAdapter(usersAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewChats.setLayoutManager(linearLayoutManager);
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list to load fast
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    users.setuId(dataSnapshot.getKey());
                    //removing user who is login
                    if(!users.getuId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }

                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}