package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class ClientOrderModel(
    @SerializedName("id") val id: String,
    @SerializedName("clientName") val clientName: String,
    @SerializedName("shopName") val shopName: String,
    @SerializedName("city") val city: String?,
    @SerializedName("calculetedDiscount") val calculetedDiscount: Double?,
    @SerializedName("totalPrice") val totalPrice: Double,
    @SerializedName("subtotal") val subtotal: Double,
    @SerializedName("createdDate") val createdDate: String?,
    @SerializedName("details") val details: List<DetailOrderModel>?
)
