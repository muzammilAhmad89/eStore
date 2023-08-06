package com.example.fashionhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView

class ActivitySignUp : AppCompatActivity() {
lateinit var back: ShapeableImageView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        back = findViewById(R.id.backarrow);
        back.setOnClickListener {
            super.onBackPressed();
        }

    }
}