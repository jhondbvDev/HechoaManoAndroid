package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class ClientOrderModel (
    @SerializedName("clientId") val clientId: String,
    @SerializedName("details") val details: List<DetailOrderModel>,
    @SerializedName("totalPrice") val totalPrice: Double
)
