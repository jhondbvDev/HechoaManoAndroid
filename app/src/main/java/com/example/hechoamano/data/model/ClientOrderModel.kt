package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class ClientOrderModel(
    @SerializedName("id") val id: String,
    @SerializedName("clientName") val clientName: String,
    @SerializedName("shopName") val shopName: String,
    @SerializedName("totalPrice") val totalPrice: Double,
    @SerializedName("createdDate") val createdDate: String,
)
