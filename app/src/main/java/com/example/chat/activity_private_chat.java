package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class activity_private_chat extends AppCompatActivity {
    Button add_chat;
    ListView textmsg12;
    FirebaseListAdapter<cr_ls_txt> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_private_chat);
        displayMessages();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        add_chat = (Button) findViewById(R.id.add_chat);
        textmsg12 = (ListView) findViewById(R.id.textmsg12);

        add_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_private_chat.this, activity_cr_ls.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.private_chats);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.public_chats:
                        Intent intent = new Intent(activity_private_chat.this, activity_chat.class);
                        startActivity(intent);
                        break;
                    case R.id.profile_c:
                        intent = new Intent(activity_private_chat.this, activity_profile.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    private void displayMessages() {
        String mail1 = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mail1 = mail1.replaceAll("[@.]", "");
        String rt = String.valueOf(FirebaseDatabase.getInstance().getReference().child(mail1));

        ListView listMessages = (ListView) findViewById(R.id.textmsg12);
        Query query = FirebaseDatabase.getInstance().getReference().child(mail1);
        FirebaseListOptions<cr_ls_txt> options = new FirebaseListOptions.Builder<cr_ls_txt>().setQuery(query, cr_ls_txt.class).setLayout(R.layout.item_ls).build();
        adapter = new FirebaseListAdapter<cr_ls_txt>(options) {
            @Override
            protected void populateView(View v, cr_ls_txt model, int position) {
                final TextView ls;
                ls = (TextView) v.findViewById(R.id.itemls);
                ls.setText(model.getText());
            }
        };
        listMessages.setAdapter(adapter);
    }
}


