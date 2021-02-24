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
import com.google.firebase.auth.FirebaseAuth;

public class activity_re extends AppCompatActivity {
    EditText emailText2;
    Button sendbut;
    ImageButton return3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_re);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        emailText2=(EditText) findViewById(R.id.emailText2);
        sendbut=(Button) findViewById(R.id.sendbut);
        return3=(ImageButton) findViewById(R.id.return3);

        return3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=emailText2.getText().toString();
                if (mail.equals("")) {
                    Toast.makeText(activity_re.this, "Enter mail",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (mail.contains("@") && mail.contains(".")) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);

                        auth.sendPasswordResetEmail(mail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            finish();
                                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                                        } else {
                                            Toast.makeText(activity_re.this, "Error.Probably no such user exists",
                                                    Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(activity_re.this, "Enter correct mail",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}