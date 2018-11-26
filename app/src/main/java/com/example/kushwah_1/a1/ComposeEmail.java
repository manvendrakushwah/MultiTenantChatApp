package com.example.kushwah_1.a1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ComposeEmail extends AppCompatActivity {

    private ActionBar toolbar;
    FloatingActionButton button;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Email");

        textView1=findViewById(R.id.composeemailid);
        textView2=findViewById(R.id.composeemailsubject);
        textView3=findViewById(R.id.composeemailtext);
        button=findViewById(R.id.composeemailsendbutton);

        if(intent.hasExtra("to")){
            textView1.setText(intent.getStringExtra("to"));
            textView2.setText(intent.getStringExtra("sub"));
        }
        if(intent.hasExtra("meet")){
            toolbar.setTitle("Forum");
            textView3.setHint("Enter text");
        }
        if(intent.hasExtra("forum")){
            toolbar.setTitle("Forum");
            textView3.setHint("Enter text");
            textView2.setText(intent.getStringExtra("subject"));
            final String senderUid= intent.getStringExtra("senderUid");

            DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("users");


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnap: dataSnapshot.getChildren()){

                        if(dataSnap.getKey().toString().equals(senderUid)){
                           String senderEmailId=dataSnap.child("email").getValue().toString();
                            textView1.setText(senderEmailId);
                            //Toast.makeText(EmailPage.this, ""+senderEmailId, Toast.LENGTH_SHORT).show();
                            Log.e("Mannn","dvdfvcvc");
                        }
                        Log.e("Mannnn","dddddddddd");
                    }

                    //Toast.makeText(EmailPage.this, ""+senderUid, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String receiver=textView1.getText().toString();
                subject= textView2.getText().toString();
                final String text= textView3.getText().toString();
                if(subject.length()==0){
                    subject="(no subject)";
                }


                final HashMap<String,String> hashMap =new HashMap<>();
                if(text.length()==0 ){
                    Toast.makeText(ComposeEmail.this, "Please Enter some input", Toast.LENGTH_SHORT).show();
                }

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datasnap : dataSnapshot.getChildren()){
                            String email=datasnap.child("email").getValue().toString();
                            String Uid= datasnap.getKey().toString();
                            hashMap.put(email,Uid);
                        }


                        if(receiver.length()==0 ||(! hashMap.containsKey(receiver))){
                            Toast.makeText(ComposeEmail.this, "Please Enter a valid email id", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final DatabaseReference databaseReference2 =FirebaseDatabase.getInstance().getReference("messages/"+hashMap.get(receiver));

                                    String key=databaseReference2.push().getKey();
                                    String currentUser=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                    String currentUserUid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    DateTime dateTime=new DateTime();
                                    String date= dateTime.currentDate();
                                    String time=dateTime.currentTime();

                                    databaseReference2.child(key).child("Date").setValue(date);
                                    databaseReference2.child(key).child("sender").setValue(currentUser);
                                    databaseReference2.child(key).child("subject").setValue(subject);
                                    databaseReference2.child(key).child("text").setValue(text);
                                    databaseReference2.child(key).child("time").setValue(time);

                                    databaseReference2.child(key).child("senderUid").setValue(currentUserUid);

                            Toast.makeText(ComposeEmail.this, " Sent Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(ComposeEmail.this,HomePage.class);
                            startActivity(intent);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("manv","Error");
                    }
                });





            }


        });

    }
}
