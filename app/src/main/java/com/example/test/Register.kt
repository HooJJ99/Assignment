package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.test.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity(){

    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setProgressBar(R.id.progressBar)


        btn.setOnClickListener{
            SignUp()
        }

        tvlogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun SignUp(){
        val email = etemail.text.toString()
        val userName = etusername.text.toString()
        val password = etpassword.text.toString()

        showProgressBar()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please Enter text", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("Main Activity", "Email is "+email)
        Log.d("Main Activity", "UserName is "+userName)
        Log.d("Main Activity", "Password is "+password)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "Successfully signed up", Toast.LENGTH_SHORT).show()
                writeNewUser()
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun writeNewUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val userName = etusername.text.toString()
        val user = User(userName)
        ref.setValue(user).addOnSuccessListener{
            Log.d("RegisterActivity", "User saved to database")

            val intent = Intent(this, AfterLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            }

            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed saving user to database")
                Toast.makeText(this, "Failed saving user to database", Toast.LENGTH_SHORT).show()
            }
    }

    fun setProgressBar(resId: Int) {
        progressBar = findViewById(resId)
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }
    private fun verifyIfUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            val intent = Intent(this, Register::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean{
        when(item?.itemId){
            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Register::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.modifyuser -> {
                val intent = Intent(this, ModifyUser::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.extramenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
