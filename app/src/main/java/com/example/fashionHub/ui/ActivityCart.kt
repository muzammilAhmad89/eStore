package com.example.fashionHub.ui
import com.example.fashionHub.adapters.AdaptorCartItems
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionHub.model.ItemToCartModel
import com.example.fashionhub.R
import com.example.fashionhub.databinding.ActivityCartBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityCart : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var adaptor: AdaptorCartItems
    private lateinit var userArrayList: ArrayList<ItemToCartModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)

        setContentView(binding.root)



        // getting the recyclerview by its id
        recyclerView = findViewById(R.id.cartRecyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this)


        userArrayList= arrayListOf()

        database= FirebaseDatabase.getInstance().getReference("cart")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (dataSnapShot in snapshot.children){
                        val user=dataSnapShot.getValue(ItemToCartModel::class.java)
                        if (!userArrayList.contains(user)){
                            userArrayList.add(user!!)
                        }

                    }
                    // This will pass the ArrayList to our Adapter
                    adaptor = AdaptorCartItems(userArrayList)

                    // Setting the Adapter with the recyclerview
                    recyclerView.adapter = adaptor
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ActivityCart,error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        // Calculate and display initial total price
        updateTotalPrice()
    }

    fun updateTotalPrice() {
        val (price,tax)=calculateTotalPrice()
        // Update your UI element (e.g., a TextView) to display the total price
        // For example, if you have a TextView with the ID "totalPriceTextView":
        val totalPriceTextView: TextView = findViewById(R.id.subTotalPrice)
        // Convert the total price back to a string before setting it in the TextView
        totalPriceTextView.text = "$${price.toString()}"
        binding.gstPrice.text=tax.toString()
    }

    private fun calculateTotalPrice(): Pair<Double, Int> {

        var total = 0.0
        var totalQuantity=0
        var totalQuantityTax=0.0
        for (item in userArrayList) {
            if (item.isSelected) {
                val itemPriceString = "${item.price}".replace("$", "")

                // Convert the price string to double for calculation
                val itemPrice = itemPriceString.toDouble()
                total += itemPrice * item.quantity.toInt()
                totalQuantity += item.quantity.toInt()
            }
        }
        // Calculate the total quantity with 15% tax
        totalQuantityTax = totalQuantity * 1.15

        return Pair(total,totalQuantityTax.toInt())
    }

}

