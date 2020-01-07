package com.example.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_afterlogin.*
import kotlinx.android.synthetic.main.activity_login.*
import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.AuthCredential
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_modifyuser.*


class ModifyUser: AppCompatActivity(){

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifyuser)

        val sharedPref = getSharedPreferences("login_credentials", Context.MODE_PRIVATE)

        val eemail = sharedPref.getString("Email", "")
        val ppassword = sharedPref.getString("Password", "")

        fetchUser()

        modifyBtn.setOnClickListener{
            modifyUser()
        }

        modifyemail.setOnClickListener{
            modifyEmail()
        }

        modifypass.setOnClickListener{
            changePassword()
        }
    }

    private fun verifyIfUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            val intent = Intent(this, Register::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun fetchUser(){
        val uid = FirebaseAuth.getInstance().uid
        val mDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/$uid")


        mDatabaseReference.addValueEventListener(object: ValueEventListener  {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user1 = dataSnapshot.getValue(User::class.java)
                val username = user1?.username.toString()
                loginStatus.setText(username)
            }
        })
    }

    fun modifyUser(){
        val uid = FirebaseAuth.getInstance().uid
        val mDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val userName = mUser.text.toString().trim()

        val user = User(userName)

        mDatabaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.getRef().setValue(user)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun modifyEmail(){
        mAuth = FirebaseAuth.getInstance()
        val loggedUser = mAuth!!.currentUser
        if(loggedUser != null && loggedUser.email != null){
            if(authpass.text.isNotEmpty()) {
                val credential: AuthCredential = EmailAuthProvider.getCredential(loggedUser.email!!, authpass.text.toString())
                loggedUser?.reauthenticate(credential)?.addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    if (newemail.text.isNotEmpty()) {
                        if(newemail.text.toString().equals(loggedUser.email)){
                            Toast.makeText(this, "Email is the same!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            loggedUser.updateEmail(newemail.text.toString())
                        }
                    } else {
                        Toast.makeText(this, "Please enter all fields!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun changePassword(){
        mAuth = FirebaseAuth.getInstance()
        val loggedUser = mAuth!!.currentUser
        if(loggedUser != null && loggedUser.email != null){
            if(oldpass.text.isNotEmpty() && newpass.text.isNotEmpty() && newpasscfm.text.isNotEmpty()) {
                if(newpass.text.toString().equals(newpasscfm.text.toString())) {
                    val credential: AuthCredential = EmailAuthProvider.getCredential(loggedUser.email!!, oldpass.text.toString())

                    loggedUser?.reauthenticate(credential)?.addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener
                        Toast.makeText(this, "Re Authentication Success", Toast.LENGTH_SHORT)

                        loggedUser?.updatePassword(newpass.text.toString())?.addOnCompleteListener {
                            if (!it.isSuccessful) return@addOnCompleteListener
                            Toast.makeText(
                                    this,
                                    "Password successfully changed",
                                    Toast.LENGTH_SHORT
                            )
                            mAuth!!.signOut()
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show()
                                }
                    }
                }else{
                    Toast.makeText(this, "Password mismatch!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
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