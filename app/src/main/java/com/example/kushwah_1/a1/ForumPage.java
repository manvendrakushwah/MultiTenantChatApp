package com.example.kushwah_1.a1;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ForumPage extends AppCompatActivity {

    private static void order(ArrayList<MessageClass> list) {

        Collections.sort(list, new Comparator<MessageClass>() {
            public int compare(MessageClass o1, MessageClass o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
    }


    private ActionBar toolbar;
    public FloatingActionButton reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);

        final Intent intent=getIntent();
        toolbar=getSupportActionBar();
        toolbar.setTitle(intent.getStringExtra("subject"));
        reply = findViewById(R.id.forumpagereplybutton);

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ForumPage.this,ComposeEmail.class);
                intent1.putExtra("forum","xyx");
                intent1.putExtra("subject",intent.getStringExtra("subject"));
                intent1.putExtra("senderUid",intent.getStringExtra("senderUid"));
                startActivity(intent1);
            }
        });

        final RecyclerView recyclerView =findViewById(R.id.forumpage_recycler);
        Context context= getBaseContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        final FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("messages/"+firebaseUser.getUid());
        final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("messages");
        final String subject=intent.getStringExtra("subject");
        final String senderUidd=intent.getStringExtra("senderUid");
        final ArrayList<MessageClass> listfinal= new ArrayList<>();
        final ArrayList<MessageClass> list1= new ArrayList<>();
        final ArrayList<MessageClass> list2= new ArrayList<>();

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                listfinal.clear();
                list1.clear();






                for(DataSnapshot dataSnapshot1: _dataSnapshot.getChildren()){
                        if(dataSnapshot1.getKey().equals(intent.getStringExtra("senderUid"))){
                            Log.e("Uid",dataSnapshot1.getKey() +" : "+intent.getStringExtra("senderUid"));

                            for(DataSnapshot ds: dataSnapshot1.getChildren()){
                                if(ds.hasChild("subject") && ds.child("subject").getValue().equals(subject) && ds.hasChild("sender")&& ds.child("sender").getValue().equals(firebaseUser.getDisplayName())){
                                    String subject= (ds.child("subject").getValue()!=null)?ds.child("subject").getValue().toString():"(Empty Message)";
                                    String Date= (ds.child("Date").getValue()!=null)?(ds.child("Date").getValue().toString()):"(Invalid Date)";
                                    String time= (ds.child("time").getValue()!=null)?(ds.child("time").getValue().toString()):"(Invalid time)";
                                    String text= (ds.child("text").getValue()!=null)?(ds.child("text").getValue().toString()):"(Invalid text)";
                                    String sender= (ds.child("sender").getValue()!=null)?(ds.child("sender").getValue().toString()):("(Invalid sender)");
                                    String senderUid=(ds.child("senderUid").getValue()!=null)?(ds.child("senderUid").getValue().toString()):("Invalid User");

                                    MessageClass snap=new MessageClass(Date,sender,subject,text,time,senderUid);



                                    list1.add(snap);

                                }
                            }
                        }


                }

                listfinal.addAll(list1);
                listfinal.addAll(list2);
                order(listfinal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            listfinal.clear();
            list2.clear();


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    //  Log.d("mann", "Outer Loop: " + subject);
                    if((dataSnapshot1.hasChild("subject")) && dataSnapshot1.child("subject").getValue().equals(subject) &&dataSnapshot1.child("senderUid").getValue().equals(senderUidd)){
                        Log.d("mann", "Passes if condition");
                        String subject1= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                        String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                        String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                        String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                        String sender= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                        String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");

                        MessageClass snap = new MessageClass(Date,sender,subject1,text,time,senderUid);
                        list2.add(snap);
                    }
                }
                listfinal.addAll(list1);
                listfinal.addAll(list2);
                order(listfinal);

                // Log.d("mann", "List size: "  + list.size());
                ForumMsgAdapter messageAdapter =new ForumMsgAdapter(listfinal
                );
                recyclerView.setAdapter(messageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.toException().toString());
            }
        });



    }
}
