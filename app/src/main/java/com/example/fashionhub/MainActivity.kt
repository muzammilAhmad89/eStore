package com.example.fashionhub

import android.content.Intent
<<<<<<< HEAD
import android.os.Bundle
=======
>>>>>>> origin/master
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
<<<<<<< HEAD
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
                    startActivity(Intent(this@MainActivity, home_activity::class.java))
                    finish()
                }

                override fun onFailure() {
                }
            })
        }
    }
}

=======
    lateinit var signup: MaterialTextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signup=findViewById(R.id.tvsignup);
        signup.setOnClickListener {
            val intent=Intent(this,ActivitySignUp::class.java);
            startActivity(intent);
        }
    }
}
>>>>>>> origin/master
