package com.example.fashionhub.ui
import com.example.fashionhub.adapters.AdaptorCartItems
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionhub.Model.ItemToCartModel
import com.example.fashionhub.R
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)



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
        val totalPrice = calculateTotalPrice()
        // Update your UI element (e.g., a TextView) to display the total price
        // For example, if you have a TextView with the ID "totalPriceTextView":
        val totalPriceTextView: TextView = findViewById(R.id.subTotalPrice)
        // Convert the total price back to a string before setting it in the TextView
        totalPriceTextView.text = "$${totalPrice.toString()}"
    }

    private fun calculateTotalPrice(): Double {

        var total = 0.0
        for (item in userArrayList) {
            if (item.isSelected) {
                val itemPriceString = "${item.price}".replace("$", "")

                // Convert the price string to double for calculation
                val itemPrice = itemPriceString.toDouble()
                total += itemPrice * item.quantity.toInt()
            }
        }
        return total
    }
}

