package com.example.FashionHub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.FashionHub.Utils
import com.example.fashionhub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var utils: Utils



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



        val authentication = classAutentication(this@MainActivity)



        utils = Utils(this)


        binding.tvsignup?.setOnClickListener { startActivity(Intent(this@MainActivity, ActivitySignUp::class.java)) }



        binding.btnsignin.setOnClickListener {
            authentication.login(binding.etemail.text.toString(), binding.etpassword.text.toString(), object :
                classAutentication.LoginCallback {
                override fun onSuccess() {
                    startActivity(Intent(this@MainActivity, ActivityHome::class.java))
                    finish()
                }

                override fun onFailure() {
                }
            })
        }
    }
}

