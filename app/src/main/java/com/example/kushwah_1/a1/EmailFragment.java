package com.example.kushwah_1.a1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         final ArrayList<MessageClass> list=new ArrayList<>();

        //final MessageClass  data=new MessageClass("11/11/2018","gviw uh","Subject","this is text thn what can i do for you","11:02:13","ckIhSc7XH3OnstlLO2j60N1BHvj1" );

         //list.add(data);
         //list.add(data);
         //list.add(data);

        //RecyclerView recyclerView =view.findViewById(R.id.email_recycler);
        //Context context= getContext();
        //recyclerView.setLayoutManager(new GridLayoutManager(context,1));
       // MessageAdapter messageAdapter =new MessageAdapter(list);
       // recyclerView.setAdapter(messageAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String uid =FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final DatabaseReference myref = database.getReference().child("messages/" + uid  );




        myref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Countless",""+dataSnapshot.getChildrenCount());
                //Map<String,Boolean> hashMap= new HashMap<String,Boolean>();
                list.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    //MessageClass snap= snapshot.getValue(MessageClass.class);

                    String subject= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                    String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                    String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                    String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                    String sender= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                    String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");

                    MessageClass snap=new MessageClass(Date,sender,subject,text,time,senderUid);

                    //if(hashMap.get(sender)==null ){
                        list.add(snap);
                    //}

                   // hashMap.put(sender,Boolean.TRUE);
                    Log.e("Get data",snap.getSubject());

                }
                RecyclerView recyclerView =view.findViewById(R.id.email_recycler);
                Context context= getContext();
                recyclerView.setLayoutManager(new GridLayoutManager(context,1));
                //list.clear();
                //list.add(data);

                MessageAdapter messageAdapter =new MessageAdapter(list);
                recyclerView.setAdapter(messageAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Manv","error in loading", databaseError.toException());
            }
        });





    }
}
