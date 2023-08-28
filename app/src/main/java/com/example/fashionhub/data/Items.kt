package com.example.fashionhub.data

import android.os.Parcel
import android.os.Parcelable

data class Items(
    var discount: Any? = null,
    var price: Any? = null,
    var product: Any? = null,
    var reducedPrice: Any? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Any::class.java.classLoader),
        parcel.readValue(Any::class.java.classLoader),
        parcel.readValue(Any::class.java.classLoader),
        parcel.readValue(Any::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(discount)
        parcel.writeValue(price)
        parcel.writeValue(product)
        parcel.writeValue(reducedPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Items> {
        override fun createFromParcel(parcel: Parcel): Items {
            return Items(parcel)
        }

        override fun newArray(size: Int): Array<Items?> {
            return arrayOfNulls(size)
        }
    }
}
