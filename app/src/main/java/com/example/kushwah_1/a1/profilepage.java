package com.example.kushwah_1.a1;

import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;

public class profilepage extends AppCompatActivity {
    TextView textViewEmail, textViewPass, textViewSignOut, textViewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String s=mAuth.getCurrentUser().getDisplayName().toString();
        textViewProfile=findViewById(R.id.profileusername);
        textViewProfile.setText(s);

        String email = mAuth.getCurrentUser().getEmail().toString();
        textViewEmail=findViewById(R.id.email_detail);
        textViewEmail.setText(email);

        textViewPass = findViewById(R.id.pass_title);
        textViewSignOut = findViewById(R.id.signout_title);

        textViewSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(profilepage.this,MainActivity.class));
                finish();
            }
        });
    }

    public void changepass(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(profilepage.this);
        builder.setTitle("Enter New Password");

        // Set up the input
        final EditText input = new EditText(profilepage.this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                Log.d("meet", m_Text);
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(m_Text).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(profilepage.this, "Password changed Successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(profilepage.this, "Password was not changed", Toast.LENGTH_LONG).show();
                        }
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
}
