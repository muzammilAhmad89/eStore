package com.example.fashionhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fashionhub.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ActivitySignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.backarrow.setOnClickListener { super.onBackPressed() }
        binding.btnsignup.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.etemail.text.toString()
        val pass = binding.etpassword.text.toString()
        val confirmPassword = binding.etcmpassword.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this@ActivitySignUp, "please input", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this@ActivitySignUp, "not match", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this@ActivitySignUp, "successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ActivitySignUp, "failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
