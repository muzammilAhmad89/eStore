package com.example.fashionhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fashionhub.databinding.ActivityItemDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class itemDetails : AppCompatActivity() {


    private var binding: ActivityItemDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Retrieve the serialized item data from the intent
        val selectedItem = intent.getParcelableExtra<Items>("selectedItem")

        // Display the item details if available
        if (selectedItem != null) {
            binding?.productName?.text = selectedItem.product?.toString()
            binding?.productPrice?.text = selectedItem.price?.toString()
            // Display other details as needed
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}