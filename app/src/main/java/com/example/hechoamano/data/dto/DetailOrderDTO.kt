package com.example.hechoamano.data.dto

import com.google.gson.annotations.SerializedName

data class DetailOrderDTO(
    @SerializedName("productId") val productId: String,
    @SerializedName("quantity") val quantity: Long,
    @SerializedName("price") val price: Double
)