package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class activity_signup extends AppCompatActivity {
    ImageButton imageButton;
    Button signUp1;
    EditText emailtext, psswdtext, psswdtext1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        imageButton = (ImageButton) findViewById(R.id.return1);
        signUp1 = (Button) findViewById(R.id.signUp1);
        emailtext = (EditText) findViewById(R.id.emailText);
        psswdtext = (EditText) findViewById(R.id.psswdtext);
        psswdtext1 = (EditText) findViewById(R.id.psswdtext1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        signUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailtext.getText().toString();
                String password = psswdtext.getText().toString();
                String password1 = psswdtext1.getText().toString();

                if (email.equals("") || password.equals("") || password1.equals("")) {

                    Toast.makeText(activity_signup.this, "All fields must be filled",
                            Toast.LENGTH_SHORT).show();

                } else {

                    if (password.equals(password1)) {
                        if (email.contains("@") && email.contains(".")) {
                            if (password.length() < 8) {
                                Toast.makeText(activity_signup.this, "Password must include at least eight characters",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                register(email, password);
                            }

                        } else {
                            Toast.makeText(activity_signup.this, "Enter correct mail",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(activity_signup.this, "Passwords must be match",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    void create_ls(String email) {
        email=email.replaceAll("[@.]","");
        FirebaseDatabase.getInstance().getReference().child(email).push()
                .setValue(new cr_ls(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
    }

    void register(final String email, String password) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    create_ls(email);
                    sendEmailVerification();
                    Intent intent = new Intent(activity_signup.this, signing.class);
                    startActivity(intent);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                } else {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(activity_signup.this, "Error.Probably no such user exist",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendEmailVerification() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity_signup.this, "We sent you letter with confirmation",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}