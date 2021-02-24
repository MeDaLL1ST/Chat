package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activity_cr_ls extends AppCompatActivity {
    Button button_snd;
    EditText text_get;
    EditText em_get;
    LinearLayout fon_nadoel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_ls);
        fon_nadoel=(LinearLayout) findViewById(R.id.fon_nadoel);
        text_get=(EditText) findViewById(R.id.text_get);
        em_get=(EditText) findViewById(R.id.em_get);
        button_snd=(Button) findViewById(R.id.button_snd);
        fon_nadoel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_cr_ls.this, activity_private_chat.class);
                startActivity(intent);
            }
        });
        button_snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
               String emg= String.valueOf(em_get.getText());
                String tmg= String.valueOf(text_get.getText());
                if (emg.isEmpty()||tmg.isEmpty()) {
                    Toast.makeText(activity_cr_ls.this, "Enter correctly",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (emg.contains("@")&&emg.contains(".")) {

                        if (mail1==emg){
                            Toast.makeText(activity_cr_ls.this, "You can't write to yourself",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            emg = emg.replaceAll("[@.]", "");
                            mail1 = mail1.replaceAll("[@.]", "");
                            FirebaseDatabase.getInstance().getReference().child(emg).child(mail1).push()
                                    .setValue(new cr_ls_txt(tmg));
                            FirebaseDatabase.getInstance().getReference().child(mail1).child(emg).push()
                                    .setValue(new cr_ls_txt(tmg));
                            Intent intent = new Intent(activity_cr_ls.this, activity_private_chat.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(activity_cr_ls.this, "Enter correct mail",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }
}