package com.example.fashionhub.Model

import android.os.Parcel
import android.os.Parcelable

// Employee model
data class ProductModel(
    val name:String,   // name of the employee
    val email:String   // email of the employee
):java.io.Serializable // serializing the model