package com.example.kushwah_1.a1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    ArrayList<MessageClass> list;

    MessageAdapter(ArrayList<MessageClass> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context =viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View contactView= inflater.inflate(R.layout.recyclerviewlayout,viewGroup,false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user.getPhotoUrl().toString().equals("chat")){
            holder.subject.setText(list.get(i).getSender());
        }
        else if(user.getPhotoUrl().toString().equals("email")){
            holder.subject.setText(list.get(i).getSubject());
        }
        else{
            holder.subject.setText(list.get(i).getSender()+": "+list.get(i).getSubject());
        }

        //  holder.firstletter.setText(Character.toString(list.get(i).getSubject().charAt(0)));

        final int ii = i;
        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();
               // Toast.makeText(context, list.get(ii).getText(), Toast.LENGTH_SHORT).show();
                String Uid=FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();

                if(user.getPhotoUrl().toString().equals("chat")){
                    Intent intent = new Intent(v.getContext(), ChatPage.class);
                    intent.putExtra("sender", list.get(ii).getSender());
                    intent.putExtra("subject", list.get(ii).getSubject());
                    intent.putExtra("text", list.get(ii).getText());
                    intent.putExtra("time", list.get(ii).getTime());
                    intent.putExtra("date", list.get(ii).getDate());
                    intent.putExtra("senderUid", list.get(ii).getSenderUid());
                    context.startActivity(intent);

                }
                else if(user.getPhotoUrl().toString().equals("email")){
                    Intent intent = new Intent(v.getContext(), EmailPage.class);
                    intent.putExtra("sender", list.get(ii).getSender());
                    intent.putExtra("subject", list.get(ii).getSubject());
                    intent.putExtra("text", list.get(ii).getText());
                    intent.putExtra("time", list.get(ii).getTime());
                    intent.putExtra("date", list.get(ii).getDate());
                    intent.putExtra("senderUid", list.get(ii).getSenderUid());

                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(v.getContext(), ForumPage.class);
                    intent.putExtra("sender", list.get(ii).getSender());
                    intent.putExtra("subject", list.get(ii).getSubject());
                    intent.putExtra("text", list.get(ii).getText());
                    intent.putExtra("time", list.get(ii).getTime());
                    intent.putExtra("date", list.get(ii).getDate());
                    intent.putExtra("senderUid", list.get(ii).getSenderUid());

                    context.startActivity(intent);

                }

               // ArrayList<? extends MessageClass> messageClasses =  intent.getParcelableArrayListExtra("kuch bhi");

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView  subject;

        ViewHolder(View view) {
            super(view);


            //firstletter = view.findViewById(R.id.firstletter);
            subject = view.findViewById(R.id.subject);
        }
    }

}

