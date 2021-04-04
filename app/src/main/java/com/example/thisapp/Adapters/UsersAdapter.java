package com.example.thisapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thisapp.ChatDetail;
import com.example.thisapp.Models.Users;
import com.example.thisapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.sample_user_show,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users=list.get(position);
        Picasso.get().load(users.getuProfilePic()).placeholder(R.drawable.user).into(holder.adpImage);
        holder.adpName.setText(users.getuName());

        holder.adpName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatDetail.class);
                intent.putExtra("userID",users.getuId());
                intent.putExtra("userProfilePic",users.getuProfilePic());
                intent.putExtra("userName",users.getuName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adpImage;
        TextView adpName,adpLastMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adpImage=itemView.findViewById(R.id.profile_image);
            adpName=itemView.findViewById(R.id.sample_name);
            adpLastMessage=itemView.findViewById(R.id.sample_lastmessage);
        }
    }
}
