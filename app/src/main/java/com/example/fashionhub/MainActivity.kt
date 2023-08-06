package com.example.fashionhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
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