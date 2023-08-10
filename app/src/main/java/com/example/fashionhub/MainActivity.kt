package com.example.fashionhub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionhub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        binding.tvsignup?.setOnClickListener {
            startActivity(Intent(this@MainActivity, ActivitySignUp::class.java))
        }

        binding.btnsignin.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser() {
        val email = binding.etemail.text.toString()
        val pass = binding.etpassword.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this@MainActivity, "Please input email and password", Toast.LENGTH_SHORT).show()
            return
        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, home_activity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Log In failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
