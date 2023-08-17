package com.example.fashionhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fashionhub.databinding.ActivityItemDetailsBinding

class ActivityItemDetails : AppCompatActivity() {


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