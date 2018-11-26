package com.example.kushwah_1.a1;

import android.content.Intent;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmailPage extends AppCompatActivity {
    private ActionBar toolbar;
    TextView textView1;
    TextView textView2;
    //TextView textView3;
    TextView textView4;
    FloatingActionButton button;
    String senderEmailId;
    TextView textView5;
    TextView textView6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_page);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Email");

        Intent intent=getIntent();
        final String subject=intent.getStringExtra("subject");
        String text=intent.getStringExtra("text");
        String name=intent.getStringExtra("sender");
        String date=intent.getStringExtra("date");
        final String senderUid=intent.getStringExtra("senderUid");

         textView1=findViewById(R.id.emailpagesubject);
         textView2=findViewById(R.id.emailpagemessage);
         //textView3=findViewById(R.id.emailpagename);
         textView4=findViewById(R.id.emailpagedate);
         button  =findViewById(R.id.emailpagereplybutton);
         textView5=findViewById(R.id.emailpagetoemail);
         textView6=findViewById(R.id.emailpagefromemail);

        textView1.setText(subject);
        textView2.setText(text);
        //textView3.setText(name);
        textView4.setText(date);
        textView5.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("users");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnap: dataSnapshot.getChildren()){

                    if(dataSnap.getKey().toString().equals(senderUid)){
                        senderEmailId=dataSnap.child("email").getValue().toString();
                        textView6.setText(senderEmailId);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EmailPage.this,ComposeEmail.class);
                intent.putExtra("to",senderEmailId);
                intent.putExtra("sub",subject);
                startActivity(intent);
            }
        });
    }
}
