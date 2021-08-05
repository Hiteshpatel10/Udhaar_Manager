package com.example.udhaarmanager.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.udhaarmanager.R
import com.example.udhaarmanager.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if ((currentUser != null) && (currentUser.isEmailVerified)) {
            val intent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(intent).also {
                finish()
            }
        }
    }
}