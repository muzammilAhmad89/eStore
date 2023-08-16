package com.example.fashionhub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ActivitySplash : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // 5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler().postDelayed({
            if (user != null && user.isEmailVerified) {
                startActivity(Intent(this@ActivitySplash, MainActivity::class.java))
            } else {
                startActivity(Intent(this@ActivitySplash, home_activity::class.java))
            }
            finish()
        }, SPLASH_DELAY)
    }
}
