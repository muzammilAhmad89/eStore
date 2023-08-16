package com.example.fashionhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class home_activity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var logout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        logout=findViewById(R.id.btnlogout)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        /*for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.baseline_person_24, "Item " + i))
        }*/


        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            val userDocRef: DocumentReference =
                db.collection("users").document(currentUser.getUid())
            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get user data from the document snapshot
                    val username: String = documentSnapshot.getString("username").toString()
                    val email: String = documentSnapshot.getString("email").toString()
                    Toast.makeText(this@home_activity, email, Toast.LENGTH_SHORT).show()
                    // Update UI with user data
                    data.add(ItemsViewModel(R.drawable.baseline_person_24, email))
                    val adapter = CustomAdapter(data)
                    recyclerview.adapter = adapter
                }
            }.addOnFailureListener { e -> }
        }



        logout.setOnClickListener {
            logoutUser()
        }

    }
    private fun logoutUser() {
        // Sign out the user from Firebase authentication
        auth.signOut()

        // Navigate back to the login screen (MainActivity)
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Close the current activity to prevent going back to home screen
    }






}