package com.example.fashionhub.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fashionhub.Data.ProductItem
import com.example.fashionhub.databinding.ActivityAdminPanelBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityAdminPanel : AppCompatActivity() {
    private lateinit var binding: ActivityAdminPanelBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val product=binding.productInfoAdmin.text.toString()
            val price=binding.actualPriceAdmin.text.toString().toInt()
            val discount=binding.offAdmin.text.toString().toInt()
            var reducedPrice:Int?=null
            if (product != null && price != null) {
                val discPrice = (price * discount) / 100
                reducedPrice = price - discPrice
            }

            database= FirebaseDatabase.getInstance().getReference("items")
            // Add '%' symbol to the discount value
            val formattedDiscount = "$discount%"

            val users = ProductItem(product, price, formattedDiscount, reducedPrice)
            database.child(product).setValue(users).addOnSuccessListener {
                binding.productInfoAdmin.text.clear()
                binding.actualPriceAdmin.text.clear()
                binding.offAdmin.text.clear()


                Toast.makeText(this,"saved successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"failed to save", Toast.LENGTH_SHORT).show()
            }

        }

    }

}