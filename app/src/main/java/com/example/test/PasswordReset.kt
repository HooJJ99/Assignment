package com.example.test


import android.os.Bundle
import kotlinx.android.synthetic.main.activity_passwordreset.*
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PasswordReset: AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwordreset)

        mAuth = FirebaseAuth.getInstance()

        btnResetPassword.setOnClickListener {
            val email = edtResetEmail.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
            } else {
                mAuth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener
                        Log.d("Main Activity", "Check email to reset your password!")
                        Toast.makeText(this, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Failed to send email!", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        btnBack.setOnClickListener {
            finish()
        }
    }
}