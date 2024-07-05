package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class DetailOrderModel(
    @SerializedName("productId") val productId: String,
    @SerializedName("productName") val productName: String,
    @SerializedName("productFamily") val productFamily: String?,
    @SerializedName("productSubFamily") val productSubFamily: String?,
    @SerializedName("productRegion") val productRegion: String?,
    @SerializedName("productFamilyType") val productFamilyType: String?,
    @SerializedName("productSize") val productSize: String?,
    @SerializedName("quantity") val quantity: Long,
    @SerializedName("price") val price: Double,
)
