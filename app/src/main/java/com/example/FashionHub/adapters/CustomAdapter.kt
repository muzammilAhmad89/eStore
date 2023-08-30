package com.example.FashionHub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.FashionHub.data.Items
import com.example.fashionhub.R

class CustomAdapter(private val mList: ArrayList<Items>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    // Function to set the onClickListener
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]


        // sets the text to the textview from our itemHolder class
        holder.productInfo.text = ItemsViewModel.product.toString()
        holder.actualPrice.text = ItemsViewModel.price.toString()
        holder.discount.text=ItemsViewModel.discount.toString()
        holder.price.text=ItemsViewModel.reducedPrice.toString()


        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, itemDetails::class.java)


            holder.itemView.setOnClickListener {
                onClickListener?.onClick(position, ItemsViewModel)
            }

            // Pass any extra data if needed
//             intent.putExtra("id", data[position].Age)
//             intent.putExtra("name", data[position].Name)

//            holder.itemView.context.startActivity(intent)

        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val discount: TextView = itemView.findViewById(R.id.productInfo)
        val actualPrice: TextView = itemView.findViewById(R.id.actualPrice)
        val productInfo: TextView=itemView.findViewById(R.id.discount)
        val price: TextView=itemView.findViewById(R.id.price)
    }
    // Interface for click listener
    interface OnClickListener {
        fun onClick(position: Int, item: Items)
    }
}
