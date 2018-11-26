package com.example.kushwah_1.a1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ForumFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forum,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final ArrayList<MessageClass> list=new ArrayList<>();

        final MessageClass message = new MessageClass("Date", "fsdv", "fdsg", "NA", "NA", "NA");
        list.add(message);
        list.add(message);

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("messages/"+firebaseUser.getUid());

        final HashMap<String,Boolean> hashMap=new HashMap<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("mann", "onDataChange");
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    String subject= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                    String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                    String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                    String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                    String sender= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                    String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");

                    MessageClass snap=new MessageClass(Date,sender,subject,text,time,senderUid);
                    if(hashMap.get(subject+senderUid)==null){
                        list.add(snap);
                        Log.e("aaa","asdf "+snap);
                    }

                    hashMap.put(subject+senderUid,Boolean.TRUE);
                }


                RecyclerView recyclerView=  view.findViewById(R.id.forum_recycler);
                Context context=getContext();
                recyclerView.setLayoutManager(new GridLayoutManager(context,1));
                MessageAdapter messageAdapter =new MessageAdapter(list);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("manv",databaseError.toException().toString());
            }
        });
    }
}
