package com.example.kushwah_1.a1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    ArrayList<EmailData> list;

    EmailAdapter(ArrayList<EmailData> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recyclerviewlayout, viewGroup, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.subject.setText(list.get(i).getSubject());
        //holder.firstletter.setText(Character.toString(list.get(i).getSubject().charAt(0)));

        final int ii = i;
        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("subject", list.get(ii).getSubject());
                intent.putExtra("sender", list.get(ii).getSender());
                intent.putExtra("receiver", list.get(ii).getReceivers());



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {

        TextView subject;

        ViewHolder(View view){
            super(view);

            //firstletter = view.findViewById(R.id.firstletter);
            subject = view.findViewById(R.id.subject);
        }

    }
}
