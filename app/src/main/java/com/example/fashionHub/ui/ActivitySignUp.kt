package com.example.fashionHub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionhub.R
import com.example.fashionHub.Utils
import com.example.fashionhub.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class ActivitySignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_GOOGLE_SIGN_IN = 1001

    private lateinit var authentication: classAutentication


    lateinit var utils: Utils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        authentication = classAutentication(this@ActivitySignUp)


        utils = Utils(this)
        auth = FirebaseAuth.getInstance()
        binding.backarrow.setOnClickListener {
            super.onBackPressed()

        }





        /* val toolbar = findViewById<Toolbar>(R.id.toolbar)
         setSupportActionBar(toolbar)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.title = "Your Activity Title"
         toolbar.setNavigationOnClickListener {
             onBackPressed()
         }*/





        binding.btnsignup.setOnClickListener {
            authentication.signupuser(
                binding.etname.text.toString(),
                binding.etemail.text.toString(),
                binding.etpassword.text.toString(),
                binding.etcmpassword.text.toString(),
                object : classAutentication.LoginCallback {
                    override fun onSuccess() {
                        startActivity(Intent(this@ActivitySignUp, MainActivity::class.java))
                    }

                    override fun onFailure() {
                        TODO("Not yet implemented")
                    }

                }
            )


        }
        binding.googlesignin.setOnClickListener {
            SIgnUpgoogle()
        }
    }




    private fun SIgnUpgoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        utils.startLoadingAnimation()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            utils.endLoadingAnimation()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { signInTask ->
                if (signInTask.isSuccessful) {
                    utils.endLoadingAnimation()
                    // Google Sign-In successful, you can proceed with your app logic
                    Toast.makeText(this, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                } else {
                    utils.endLoadingAnimation()
                    // Google Sign-In failed
                    Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
