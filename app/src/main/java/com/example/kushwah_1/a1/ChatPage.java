package com.example.kushwah_1.a1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChatPage extends AppCompatActivity {

    private static void order(ArrayList<MessageClass> list) {

        Collections.sort(list, new Comparator<MessageClass>() {
            public int compare(MessageClass o1, MessageClass o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
    }


    EditText edittext;
    ImageButton imagebutton;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();


    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        Intent intent=getIntent();

        final String sender= intent.getStringExtra("sender");
        final String senderUid=intent.getStringExtra("senderUid");
        final DatabaseReference databaseReference=firebaseDatabase.getReference("messages/"+senderUid);

        final String currentUser=FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
        final String currentUserUid=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        // Toast.makeText(this, "xx"+sender, Toast.LENGTH_SHORT).show();
        toolbar = getSupportActionBar();
        toolbar.setTitle(sender);


        edittext=findViewById(R.id.entermsgbox);
        imagebutton=findViewById(R.id.sendmessagebutton);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String messageContent= edittext.getText().toString();

                // Toast.makeText(ChatPage.this,messageContent, Toast.LENGTH_SHORT).show();
                if(messageContent.length()>0){
                    //Toast.makeText(ChatPage.this, "Bhosda", Toast.LENGTH_SHORT).show();
                    String key=databaseReference.push().getKey();
                    // MessageClass messageClass=new MessageClass("22/11/2018",currentUser,"My Subject",messageContent,"sdv",currentUserUid);

                    DateTime dateTime=new DateTime();
                    String date= dateTime.currentDate();
                    String time=dateTime.currentTime();

                    databaseReference.child(key).child("Date").setValue(date);
                    databaseReference.child(key).child("sender").setValue(currentUser);
                    databaseReference.child(key).child("subject").setValue("(No Subject)");
                    databaseReference.child(key).child("text").setValue(messageContent);
                    databaseReference.child(key).child("time").setValue(time);
                    databaseReference.child(key).child("senderUid").setValue(currentUserUid);
                }
                edittext.getText().clear();
            }
        });


        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        String Uid1=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final DatabaseReference databaseReference1 =firebaseDatabase.getReference("messages/"+Uid1);
        final DatabaseReference databaseReference2 =firebaseDatabase.getReference("messages/"+senderUid);
       // Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, ""+sender, Toast.LENGTH_SHORT).show();



        final ArrayList<MessageClass> finalList=new ArrayList<>();
        final ArrayList<MessageClass> list1= new ArrayList<>();
        final ArrayList<MessageClass> list2= new ArrayList<>();



        databaseReference1.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1.clear();finalList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    String subject= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                    String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                    String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                    String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                    String sender1= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                    String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");

                    MessageClass snap=new MessageClass(Date,sender1,subject,text,time,senderUid);


                    if(snap.getSender().equals(sender)){list1.add(snap);}
                }

                finalList.addAll(list1);
                finalList.addAll(list2);
                order(finalList);
                RecyclerView recyclerView =findViewById(R.id.chat_page_recycler);
                Context context= getBaseContext();
                recyclerView.setLayoutManager(new GridLayoutManager(context,1));
                ChatMsgAdapter chatMsgAdapter =new ChatMsgAdapter(finalList);
                recyclerView.setAdapter(chatMsgAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("manv","Error"+databaseError.toException());
            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2.clear();finalList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ){
                    if(dataSnapshot1.child("text").getValue()!=null && dataSnapshot1.child("time").getValue()!=null && dataSnapshot1.child("senderUid").getValue()!=null) {
                        String subject= (dataSnapshot1.child("subject").getValue()!=null)?dataSnapshot1.child("subject").getValue().toString():"(Empty Message)";
                        String Date= (dataSnapshot1.child("Date").getValue()!=null)?(dataSnapshot1.child("Date").getValue().toString()):"(Invalid Date)";
                        String time= (dataSnapshot1.child("time").getValue()!=null)?(dataSnapshot1.child("time").getValue().toString()):"(Invalid time)";
                        String text= (dataSnapshot1.child("text").getValue()!=null)?(dataSnapshot1.child("text").getValue().toString()):"(Invalid time)";
                        String sender1= (dataSnapshot1.child("sender").getValue()!=null)?(dataSnapshot1.child("sender").getValue().toString()):("(Invalid sender)");
                        String senderUid=(dataSnapshot1.child("senderUid").getValue()!=null)?(dataSnapshot1.child("senderUid").getValue().toString()):("Invalid User");

                        MessageClass snap1=new MessageClass(Date,sender1,subject,text,time,senderUid);

                        if(snap1.getSender().equals(currentUser)){list2.add(snap1);}
                    }
                }
                finalList.addAll(list1);
                finalList.addAll(list2);
                order(finalList);
                RecyclerView recyclerView =findViewById(R.id.chat_page_recycler);
                Context context= getBaseContext();
                recyclerView.setLayoutManager(new GridLayoutManager(context,1));
                ChatMsgAdapter chatMsgAdapter =new ChatMsgAdapter(finalList);
                recyclerView.setAdapter(chatMsgAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("manv","Error"+databaseError.toException());
            }
        });


    }


}