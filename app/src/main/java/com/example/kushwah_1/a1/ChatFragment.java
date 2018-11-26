package com.example.kushwah_1.a1;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<MessageClass> list=new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String uid =FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final String currentUser=FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        final DatabaseReference myref = database.getReference().child("messages/" + uid  );
        final DatabaseReference myref2=database.getReference().child("messages");

        // MessageClass  data=new MessageClass("11/11/2018","gviw uh","Subject","this is text","11:02:13","ckIhSc7XH3OnstlLO2j60N1BHvj1" );

        // list.add(data);
        // list.add(data);
        // list.add(data);


        //myref.push().setValue();

        myref2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Countless",""+dataSnapshot.getChildrenCount());
                final Map<String,Boolean> hashMap= new HashMap<String,Boolean>();
                //MessageClass  data=new MessageClass("11/11/2018","gviw uh","Subject","this is text","11:02:13","ckIhSc7XH3OnstlLO2j60N1BHvj1" );
                // list.add(data);
                list.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                        //list.add(data);
                        Log.e("xyx",dataSnapshot1.child("sender").toString());
                        if(dataSnapshot1.hasChild("sender") && dataSnapshot1.child("sender").getValue().toString().equals(currentUser)){
                            Log.e("xxxx","xxxx");
                           final String subject= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                           final String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                           final String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                           final String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                           final String sender= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                           final String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");




                           final String key=snapshot.getKey().toString();

                            DatabaseReference dbrf=FirebaseDatabase.getInstance().getReference("users/"+key);

                            dbrf.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        if(dataSnapshot1.getKey().equals("username")){
                                            Log.e("ccc",dataSnapshot1.getValue().toString());
                                            if(hashMap.get(dataSnapshot1.getValue().toString())==null ){
                                                MessageClass snap=new MessageClass(Date,dataSnapshot1.getValue().toString(),subject,text,time,key);
                                                list.add(snap);
                                            }
                                            hashMap.put(dataSnapshot1.getValue().toString(),Boolean.TRUE);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    }


                }

                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         MessageClass  data=new MessageClass("11/11/2018","gviw uh","Subject","this is text","11:02:13","ckIhSc7XH3OnstlLO2j60N1BHvj1" );
                        // list.add(data);
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            //  MessageClass snap= snapshot.getValue(MessageClass.class);

                            String subject= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                            String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                            String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                            String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                            String sender= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                            String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");

                            MessageClass snap=new MessageClass(Date,sender,subject,text,time,senderUid);

                            if(hashMap.get(sender)==null ){
                                list.add(snap);
                            }

                            hashMap.put(sender,Boolean.TRUE);
                            Log.e("Get data",snap.getSubject());
                        }


                        RecyclerView recyclerView =view.findViewById(R.id.chat_recycler);
                        Context context= getContext();
                        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
                        MessageAdapter messageAdapter =new MessageAdapter(list);
                        recyclerView.setAdapter(messageAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Manv","error in loading", databaseError.toException());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Manv","error in loading", databaseError.toException());
            }
        });




    }
}