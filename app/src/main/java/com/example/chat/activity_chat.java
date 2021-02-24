package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class activity_chat extends AppCompatActivity {
     FirebaseListAdapter<Message> adapter;
    ImageButton sendmsg;
    EditText edittextmsg;
    ListView textmsg;
    ClipboardManager clipboardManager;
    ClipData clipData;
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Chat channel";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        displayChat();
        clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        sendmsg=(ImageButton) findViewById(R.id.sendmsg);
        edittextmsg=(EditText) findViewById(R.id.edittextmsg);
        textmsg=(ListView) findViewById(R.id.textmsg);

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=edittextmsg.getText().toString();
                if (msg.equals("")) {
                } else {
                    FirebaseDatabase.getInstance().getReference().child("msgs").push()
                            .setValue(new Message(msg, FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    edittextmsg.setText("");
                }
            }
        });

        BottomNavigationView bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.public_chats);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.private_chats:
                        Intent intent=new Intent(activity_chat.this, activity_private_chat.class);
                        startActivity(intent);
                        break;
                    case R.id.profile_c:
                       intent=new Intent(activity_chat.this, activity_profile.class);
                       startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
    private void displayChat() {

ListView listMessages=(ListView) findViewById(R.id.textmsg);
        Query query = FirebaseDatabase.getInstance().getReference().child("msgs");
        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>().setQuery(query, Message.class).setLayout(R.layout.item).build();
       adapter = new FirebaseListAdapter<Message>(options){
            @Override
            protected void populateView(View v, Message model, int position) {

                final TextView textMessage, autor, timeMessage;
                textMessage = (TextView)v.findViewById(R.id.tvMessage);
                autor = (TextView)v.findViewById(R.id.tvUser);
                timeMessage = (TextView)v.findViewById(R.id.tvTime);

                textMessage.setText(model.getTextMessage()+"\n");
                autor.setText(model.getAutor());
                timeMessage.setText(model.getTimeMessage());

                textMessage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        clipData = ClipData.newPlainText("text", textMessage.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(activity_chat.this, "Copied",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                notification(textMessage.getText().toString());
            }
        };
        listMessages.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onBackPressed() {
    }
    void notification(String notification) {
        Intent notificationIntent = new Intent(activity_chat.this, activity_chat.class);
        PendingIntent contentIntent = PendingIntent.getActivity(activity_chat.this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(activity_chat.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ico1)
                        .setContentTitle("Новое сообщение")
                        .setContentText(notification)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(activity_chat.this);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}