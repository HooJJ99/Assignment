package com.example.test


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.databinding.ActivityAfterloginBinding

class AfterLogin: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
            setContentView(R.layout.activity_afterlogin)



            val navController = this.findNavController(R.id.fragHost)


    }
}

