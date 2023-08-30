package com.example.fashionHub.adapters

import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionHub.model.ItemToCartModel
import com.example.fashionHub.ui.ActivityCart
import com.example.fashionhub.R

class AdaptorCartItems(private val itemsList: ArrayList<ItemToCartModel>) : RecyclerView.Adapter<AdaptorCartItems.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = from(parent.context)
            .inflate(R.layout.cart_item_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = itemsList[position]

        holder.product.text=itemsViewModel.name
        holder.size.text=itemsViewModel.size
        holder.price.text=itemsViewModel.price
        holder.quantity.text=itemsViewModel.quantity

        holder.radioButton.isChecked = itemsViewModel.isSelected
        holder.radioButton.setOnCheckedChangeListener { _, isChecked ->
            itemsViewModel.isSelected = isChecked
            /* Notify the activity about the selection change */
            (holder.itemView.context as ActivityCart).updateTotalPrice()
        }

    }
    override fun getItemCount(): Int {
        return itemsList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val product:TextView=itemView.findViewById(R.id.cartDescription)
        val size: TextView=itemView.findViewById(R.id.cartSize)
        val price:TextView=itemView.findViewById(R.id.cartPrice)
        val quantity:TextView=itemView.findViewById(R.id.quantity)
        val radioButton: CheckBox = itemView.findViewById(R.id.radioButton)
    }


}