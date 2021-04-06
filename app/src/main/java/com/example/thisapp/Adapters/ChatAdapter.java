package com.example.thisapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thisapp.Models.MessageModel;
import com.example.thisapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<MessageModel> messageModels;
    Context context;
    int Sender_View_Type=1;
    int Reciver_View_Type=1;
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
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
        MessageModel mModel=messageModels.get(position);
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
