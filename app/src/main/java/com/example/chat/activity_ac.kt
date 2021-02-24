package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity_ac : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac)

        val ch_pass:Button=findViewById(R.id.ch_pass) as Button
        val ch_email:Button=findViewById(R.id.ch_email) as Button

        ch_pass.setOnClickListener {
            val intent = Intent(this, activity_re::class.java)
            startActivity(intent)
        }
        ch_email.setOnClickListener {
            val intent = Intent(this, activity_ch_email::class.java)
            startActivity(intent)
        }


    }

}