package com.example.kushwah_1.a1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ForumMsgAdapter extends RecyclerView.Adapter<ForumMsgAdapter.ViewHolder> {

    private ArrayList<MessageClass> list;
    ForumMsgAdapter(ArrayList<MessageClass> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ForumMsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context=viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View contactView= inflater.inflate(R.layout.forummsgrecyclerview,viewGroup,false);
        return new ForumMsgAdapter.ViewHolder(contactView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.msg.setText(list.get(i).getText());
        viewHolder.sender.setText(list.get(i).getSender());

        Log.d("mann2", "List: " + list.size() + " position: " + i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        TextView sender,msg;

        ViewHolder(View view) {
            super(view);

            msg=view.findViewById(R.id.forummsgtext);
            sender=view.findViewById(R.id.forummsgsubject);

        }
    }

}
