package com.example.hechoamano.data.dto

import com.google.gson.annotations.SerializedName

data class ClientOrderDTO (
    @SerializedName("clientId") val clientId: String,
    @SerializedName("details") val details: List<DetailOrderDTO>,
    @SerializedName("totalPrice") val totalPrice: Double
)
