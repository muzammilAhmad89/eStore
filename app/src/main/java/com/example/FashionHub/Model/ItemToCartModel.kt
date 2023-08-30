package com.example.FashionHub.Model

data class ItemToCartModel(
    var name: String = "",
    var price: String = "",
    var size: String = "",
    var quantity:String="",
    var isSelected: Boolean = false
)
