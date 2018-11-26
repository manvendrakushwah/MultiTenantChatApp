package com.example.kushwah_1.a1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mAuth = FirebaseAuth.getInstance();
        toolbar = getSupportActionBar();
        toolbar.hide();

        final EditText email= findViewById(R.id.editText);
        final EditText password=findViewById(R.id.editText2);

        final Button login  = findViewById(R.id.button2);
        final Button signup = findViewById(R.id.button3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                if(email.getText().toString().length()==0 || password.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Please Enter all required details", Toast.LENGTH_SHORT).show();
                    email.getText().clear();
                    password.getText().clear();
                    login.setEnabled(true);
                    return;
                }
               mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                       .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                  // Toast.makeText(MainActivity.this, "Welcome "+username, Toast.LENGTH_LONG).show();
                                  // addUserToDatabase();
                                   startActivity(new Intent(MainActivity.this, HomePage.class));
                                   //finish();
                               }
                               else{
                                   Toast.makeText(MainActivity.this, "Error While Login " + task.getException(), Toast.LENGTH_LONG).show();

                                   login.setEnabled(true);
                               }
                           }
                       });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setEnabled(false);
                if(email.getText().toString().length()==0 || password.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Please Enter all required details", Toast.LENGTH_SHORT).show();
                    email.getText().clear();
                    password.getText().clear();
                    signup.setEnabled(true);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_LONG).show();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            addUserToDatabase();
                                        }
                                    });
                                }
                                else{
                                    login.setEnabled(true);
                                    Toast.makeText(MainActivity.this, "Error While SignUp " + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }


    private void addUserToDatabase(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference().child("users/" + user.getUid());


        EditText usernameEditText= findViewById(R.id.editText3);
        final String username= usernameEditText.getText().toString();
        Log.d("suthar", username);

        HashMap<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("email", user.getEmail());

        myref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Do something

                    //startActivity(new Intent(MainActivity.this, HomePage.class));


                    //popup alert dialog

                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                    String[] fruitArray = {"Chat", "Email", "Forum"};
                    builder.setTitle("Choose");
                    builder.setItems(fruitArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent intent=new Intent(MainActivity.this, HomePage.class);
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                           // Toast.makeText(MainActivity.this, user.getUid().toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this, "Pel diya "+which, Toast.LENGTH_SHORT).show();
                            Log.d("suthar", "" + which);
                            Log.d("suthar", username);
                            if(which==0){
                                ///intent.putExtra("type","chat");
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .setPhotoUri(Uri.parse("chat"))
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Log.d(TAG, "");
                                                   // Toast.makeText(MainActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();


                                                   // Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                            else if(which==1){
                                //intent.putExtra("type","email");
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .setPhotoUri(Uri.parse("email"))
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Log.d(TAG, "");
                                                   // Toast.makeText(MainActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();


                                                   // Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                }
                                            }
                                        });

                            }
                            else{
                               // intent.putExtra("type","forum");
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .setPhotoUri(Uri.parse("forum"))
                                        .build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Log.d(TAG, "");
                                                  //  Toast.makeText(MainActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();

                                                  //  Toast.makeText(MainActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }



                            //Update  user profile


                        }
                    });
                    builder.show();

                    //
                }
                else {
                    //Do something
                    Toast.makeText(MainActivity.this, "Error While add user to database " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
