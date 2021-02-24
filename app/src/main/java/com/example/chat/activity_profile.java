package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_profile extends AppCompatActivity {

    TextView text_prof;
    Button ac_logout;
    Button ac_exit;
    Dialog dialog;
    Button ac_sup,ac_ac,ac_ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        text_prof=(TextView) findViewById(R.id.text_prof);
        ac_logout=(Button) findViewById(R.id.ac_logout);
        ac_exit=(Button) findViewById(R.id.ac_exit);
        ac_sup=(Button) findViewById(R.id.ac_sup);
        ac_ac=(Button) findViewById(R.id.ac_ac);
        ac_ca=(Button) findViewById(R.id.ac_ca);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        String mail1=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        text_prof.setText(mail1);

        ac_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_profile.this, activity_ac.class);
                startActivity(intent);
            }
        });

        ac_sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ac_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(activity_profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ac_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
                System.exit(0);
            }
        });
        ac_ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference commandsRef = rootRef.child("upd");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getValue(String.class);
                            updating(text);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                commandsRef.addListenerForSingleValueEvent(eventListener);
            }
        });

        BottomNavigationView bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile_c);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.private_chats:
                        Intent intent=new Intent(activity_profile.this, activity_private_chat.class);
                        startActivity(intent);
                        break;
                    case R.id.public_chats:
                        intent=new Intent(activity_profile.this, activity_chat.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    void updating(String it_u) {
        try {
            int versionCode = BuildConfig.VERSION_CODE;
            if (versionCode < Integer.parseInt(it_u)) {

                AlertDialog.Builder adb=new AlertDialog.Builder(activity_profile.this);
                adb.setTitle("Update found");
                adb.setMessage("Download the update?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "https://andrback.000webhostapp.com/";
                        Uri uriUrl = Uri.parse(url);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }});
                adb.show();

            } else {
                Toast.makeText(activity_profile.this, "Updates not found",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception r){
            Toast.makeText(activity_profile.this, "Error"+ r,
                    Toast.LENGTH_SHORT).show();
        }
    }

    void showDialog  () {
        dialog = new Dialog(activity_profile.this);
        dialog.setTitle("Заголовок диалога");
        dialog.setContentView(R.layout.dialog);
        dialog.show();
    }
}