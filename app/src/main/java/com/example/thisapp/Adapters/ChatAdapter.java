package com.example.thisapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thisapp.Models.MessageModel;
import com.example.thisapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;
    int Sender_View_Type=1;
    int Reciver_View_Type=2;
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == Sender_View_Type){
            View view=LayoutInflater.from(context).inflate(R.layout.sender_sample_code, parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view=LayoutInflater.from(context).inflate(R.layout.reciver_sample_code,parent, false);
            return new ReciverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      final   MessageModel mModel=messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are U sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid()+recId;

                                //node deleted
                                database.getReference().child("chats").child(senderRoom)
                                        .child(mModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                return false;
            }
        });
        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder) holder).senderMessage.setText(mModel.getMessage());
        }
        else{
            ((ReciverViewHolder)holder).reciverMessage.setText(mModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {

        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return Sender_View_Type;
        }
        else{
            return Reciver_View_Type;
        }
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder{
        TextView reciverMessage,reciverTime;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverMessage=itemView.findViewById(R.id.tv_reciver_chat);
            reciverTime=itemView.findViewById(R.id.tv_time_reciver);

        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMessage,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage=itemView.findViewById(R.id.tv_sender_chat);
            senderTime=itemView.findViewById(R.id.tv_time_sender);
        }
    }
    
}
