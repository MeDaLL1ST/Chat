package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signing extends AppCompatActivity {
    ImageButton imageButton;
    private FirebaseAuth mAuth;
    EditText emailText,psswdtext;
    Button signIn1,buttonf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        emailText=(EditText) findViewById(R.id.emailText);
        psswdtext=(EditText) findViewById(R.id.psswdtext);
        signIn1=(Button) findViewById(R.id.signIn1);
        buttonf=(Button) findViewById(R.id.buttonf);
        imageButton=(ImageButton) findViewById(R.id.return1);

        buttonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                Intent intent=new Intent(signing.this, activity_re.class);
                startActivity(intent);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signing.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=emailText.getText().toString();
                String password=psswdtext.getText().toString();

                if (email.equals("")||password.equals("")) {

                    Toast.makeText(signing.this, "All fields must be filled",
                            Toast.LENGTH_SHORT).show();

                } else {
                        sign(email, password);
                }
            }
        });
    }
    public void sign(String email,String password) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(signing.this, activity_chat.class);
                    startActivity(intent);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                } else {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(signing.this, "Authentification failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
