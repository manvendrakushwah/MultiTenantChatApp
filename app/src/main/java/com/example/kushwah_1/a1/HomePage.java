package com.example.kushwah_1.a1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {


    private ActionBar toolbar;
    private Fragment email, chat,forum;
    private FirebaseAuth mAuth;
    FloatingActionButton fabutton;
    boolean x=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toolbar = getSupportActionBar();
        fabutton=findViewById( R.id.fab);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

            //Toast.makeText(this, user.getUid().toString(), Toast.LENGTH_SHORT).show();

//            Log.d("suthar", user.getPhotoUrl().toString());
 //           Log.d("suthar", user.getDisplayName());
            String xxx="email";
            if(user!=null) xxx=(user.getPhotoUrl()!=null) ?user.getPhotoUrl().toString():"email";


            if (xxx.equals("email")) {

                email = new EmailFragment();
                loadFragment(email);
                toolbar.setTitle("Email");
                fabutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        startActivity(new Intent(HomePage.this,ComposeEmail.class));
                    }
                });

            } else if (xxx.equals("chat")) {
                chat = new ChatFragment();
                loadFragment(chat);
                toolbar.setTitle("chat");
                fabutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                        builder.setTitle("Entet UserName");

                        final EditText input = new EditText(HomePage.this);

                        input.setInputType(InputType.TYPE_CLASS_TEXT );
                        builder.setView(input);


                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String m_Text = input.getText().toString();
                                final Intent intent =new Intent(HomePage.this, ChatPage.class);
                                intent.putExtra("sender",m_Text);
                                DatabaseReference dbr=FirebaseDatabase.getInstance().getReference("users");

                                dbr.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                            String currentUser=dataSnapshot1.child("username").getValue().toString();
                                            Log.e("msk",currentUser);
                                            if(currentUser.equals(m_Text)){
                                                Log.e("msk","lll"+currentUser);
                                                intent.putExtra("senderUid",dataSnapshot1.getKey());
                                               // x=true;
                                            }
                                        }
                                        if(intent.hasExtra("senderUid")){startActivity(intent);}
                                        else{
                                            Toast.makeText(HomePage.this, "User does not exist ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.e("manv",""+databaseError.toException());
                                    }
                                });



                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();

                    }
                });

            } else {
                forum = new ForumFragment();
                loadFragment(forum);
                toolbar.setTitle("Forum");
                fabutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                      Intent intent =new Intent(HomePage.this, ComposeEmail.class);
                      intent.putExtra("meet","meet");
                      startActivity(intent);


                    }
                });
            }









        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);









    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_email:
                    toolbar.setTitle("Email");
                    loadFragment(email);
                    return true;
                case R.id.navigation_chat:
                    toolbar.setTitle("Chat");
                    loadFragment(chat);
                    return true;
                case R.id.navigation_forum:
                    toolbar.setTitle("forum");
                    loadFragment(forum);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null );
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:  {
                //Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this,profilepage.class);
                startActivity(intent);

                return true;
            }
            case R.id.item2:{
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();

                return true;
            }
            case R.id.item3: {
                Toast.makeText(getApplicationContext(),"Signed Out Successfully",Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
