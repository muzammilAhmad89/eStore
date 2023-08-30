package com.example.FashionHub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.FashionHub.data.Items
import com.example.FashionHub.Model.ItemToCartModel
import com.example.fashionhub.R
import com.example.fashionhub.databinding.ActivityItemDetailsBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class ActivityItemDetails : AppCompatActivity() {


    private lateinit var binding: ActivityItemDetailsBinding
    private var itemCount=0
    private var selectedCard: CardView?=null
    private var selectedText: TextView?=null

    // Declare the product variables globally
    private var productName: String = ""
    private var productPriceString: String = ""
    private var selectedSize: String=""
    private var productPrice:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        val db=FirebaseDatabase.getInstance().getReference("cart")

        binding.cartIcon.setOnClickListener {
            startActivity(Intent(this@ActivityItemDetails,ActivityCart::class.java))
        }


        binding.btnAddToCart.setOnClickListener {


            if (selectedSize.isNotEmpty() && itemCount!=0){

                val product = ItemToCartModel(productName, productPriceString, selectedSize, itemCount.toString())

                db.child(productName).setValue(product).addOnSuccessListener {

                    Toast.makeText(this,"added to cart", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Failed to add", Toast.LENGTH_SHORT).show()
                }

            }

            else{
                if (itemCount==0){
                    Toast.makeText(this,"please select the quantity",Toast.LENGTH_SHORT).show()
                }

                else Toast.makeText(this@ActivityItemDetails,"please select the size",Toast.LENGTH_SHORT).show()
            }

        }

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
            productName = selectedItem.discount?.toString() ?: ""
            productPriceString = selectedItem.reducedPrice.toString()
            productPrice =productPriceString.toDouble()

            binding.productName.text = productName
            binding.productPrice.text = productPriceString


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
        selectedSize=clickedTextView.text.toString()
    }

    private fun updateCounter() {
        binding.countTextView.text=itemCount.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}