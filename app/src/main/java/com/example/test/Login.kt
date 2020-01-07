package com.example.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginbtn.setOnClickListener{
            val email = loginemail.text.toString()
            val password = loginpassword.text.toString()

            Log.d("Main Activity", "Attemping to login")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) return@addOnCompleteListener

                    val intent = Intent(this, AfterLogin::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                    Toast.makeText(this, "Successfully signed in", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Log.d("login", "Failed to login: ${it.message}")
                }
        }

        tvregister.setOnClickListener{
            finish()
        }

        forgotpassword.setOnClickListener{
            val intent = Intent(this, PasswordReset::class.java)
            startActivity(intent)
        }
    }

}