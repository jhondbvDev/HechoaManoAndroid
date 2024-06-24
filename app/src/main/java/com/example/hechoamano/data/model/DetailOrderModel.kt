package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class DetailOrderModel(
    @SerializedName("productId") val productId: String,
    @SerializedName("quantity") val quantity: Long,
    @SerializedName("price") val price: Double
)