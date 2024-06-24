package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class InventoryOrderDetailModel (
    @SerializedName("productId") val productId: String,
    @SerializedName("countedQuantity") val countedQuantity: Long,
    @SerializedName("systemQuantity") val systemQuantity: Long
)