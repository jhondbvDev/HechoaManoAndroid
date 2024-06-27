package com.example.hechoamano.data.dto

import com.google.gson.annotations.SerializedName

data class InventoryOrderDetailDTO (
    @SerializedName("productId") val productId: String,
    @SerializedName("countedQuantity") val countedQuantity: Long,
    @SerializedName("systemQuantity") val systemQuantity: Long
)