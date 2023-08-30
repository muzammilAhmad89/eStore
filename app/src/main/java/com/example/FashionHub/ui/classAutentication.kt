package com.example.FashionHub.ui

import android.content.Context
import android.widget.Toast
import com.example.FashionHub.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates


class classAutentication {
    lateinit var email: String
    lateinit var name: String
    lateinit var password: String
    var profile by Delegates.notNull<Int>()


    lateinit var utils: Utils


    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    private lateinit var context: Context


    constructor(email: String, name: String, password: String, profile: Int) {
        this.email = email
        this.name = name
        this.password = password
        this.profile = profile
    }


    constructor(context: Context) {
        this.context = context
        utils = Utils(context)

    }


    interface LoginCallback {
        fun onSuccess()
        fun onFailure()
    }


    fun login(email: String, pass: String, callback: LoginCallback) {
        utils.startLoadingAnimation()
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(context, "Please input email and password", Toast.LENGTH_SHORT).show()
            utils.endLoadingAnimation()
            return
        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    utils.endLoadingAnimation()
                    Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                    callback.onSuccess()
                } else {
                    utils.endLoadingAnimation()
                    Toast.makeText(context, "Email or Password does not match", Toast.LENGTH_SHORT)
                        .show()
                    callback.onFailure()
                }
            }
        }
    }


    fun signupuser(name: String, email: String, password: String, compassword: String,callback: LoginCallback) {
        if (email.isBlank() || password.isBlank() || compassword.isBlank())
            Toast.makeText(context, "Please input all fields", Toast.LENGTH_SHORT).show()
        else if (password != compassword)
            Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()
        else {
            utils.startLoadingAnimation()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    utils.endLoadingAnimation()
                    if (task.isSuccessful) {
                        callback.onSuccess()
                        Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }


}