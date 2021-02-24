package com.example.chat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class activity_ch_email : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ch_email)

        val emailText2: EditText = findViewById(R.id.emailText2) as EditText
        val ch_email5: Button = findViewById(R.id.ch_email5) as Button

        ch_email5.setOnClickListener {
            val email_c: String = emailText2.text.toString()

            if (email_c.equals("")) {

                Toast.makeText(this, "Enter mail",
                        Toast.LENGTH_SHORT).show()
            } else {

                if (email_c.contains("@") && email_c.contains(".")) {
                    ch_email(email_c)
                } else {
                    Toast.makeText(this, "Enter correct mail",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun ch_email(email: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user!!.updateEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        verificationen()
                        finish()
                    } else {
                        Toast.makeText(this, "Error8",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun verificationen() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Check mail",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}