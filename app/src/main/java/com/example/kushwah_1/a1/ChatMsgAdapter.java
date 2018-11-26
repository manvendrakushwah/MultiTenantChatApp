package com.example.kushwah_1.a1;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatMsgAdapter extends RecyclerView.Adapter<ChatMsgAdapter.ViewHolder> {

    ArrayList<MessageClass> list;
    ChatMsgAdapter(ArrayList<MessageClass> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChatMsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View contactView= inflater.inflate(R.layout.chatmsgrecyclerview,viewGroup,false);
        return new ChatMsgAdapter.ViewHolder(contactView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if(list.get(i).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {

            holder.msgL.setVisibility(View.INVISIBLE);
            holder.msgR.setVisibility(View.VISIBLE);

            holder.msgR.setText(list.get(i).getText());
        }
        else{
            holder.msgL.setVisibility(View.VISIBLE);
            holder.msgR.setVisibility(View.INVISIBLE);

            holder.msgL.setText(list.get(i).getText());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        TextView msgL, msgR;

        ViewHolder(View view) {
            super(view);
            msgL =view.findViewById(R.id.chatmsgrecyclerview_left);
            msgR =view.findViewById(R.id.chatmsgrecyclerview_right);

        }

    }

}