package com.example.fashionhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.fashionhub.databinding.ActivityItemDetailsBinding

class ActivityItemDetails : AppCompatActivity() {


    private lateinit var binding: ActivityItemDetailsBinding
    private var itemCount=0
    private var selectedCard: CardView?=null
    private var selectedText: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding.backarrow.setOnClickListener {
            super.onBackPressed()
        }

        binding.decreaseButton.setOnClickListener {
            if (itemCount>0){
                itemCount--
                updateCounter()
            }
        }
        binding.increaseButton.setOnClickListener {
            itemCount++
            updateCounter()
        }

        binding.small.setOnClickListener {
            onCardClicked(binding.small, binding.s)
        }

        binding.medium.setOnClickListener {
            onCardClicked(binding.medium, binding.m)
        }

        binding.large.setOnClickListener {
            onCardClicked(binding.large, binding.l)
        }

        // Retrieve the serialized item data from the intent
        val selectedItem = intent.getParcelableExtra<Items>("selectedItem")

        // Display the item details if available
        if (selectedItem != null) {
            binding?.productName?.text = selectedItem.discount?.toString()
            binding?.productPrice?.text = "$${selectedItem.reducedPrice}"

            // Display other details as needed
        }
    }

    private fun onCardClicked(clickedCard: CardView, clickedTextView: TextView) {


        selectedCard?.setCardBackgroundColor(resources.getColor(android.R.color.white))
        selectedText?.setTextColor(resources.getColor(android.R.color.black))

        selectedCard = clickedCard
        selectedCard?.setCardBackgroundColor(resources.getColor(R.color.black))

        selectedText = clickedTextView
        selectedText?.setTextColor(resources.getColor(android.R.color.white))

        // Perform other actions for the selected product
    }

    private fun updateCounter() {
        binding.countTextView.text=itemCount.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}