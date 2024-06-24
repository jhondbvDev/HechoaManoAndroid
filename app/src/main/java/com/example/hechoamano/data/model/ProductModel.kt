package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class ProductModel (
    @SerializedName("id") var id: String,
    @SerializedName("stock") var stock: Int,
    @SerializedName("family") var family: String?,
    @SerializedName("subFamily") var subfamily: String?,
    @SerializedName("familyType") var type: String?,
    @SerializedName("size") var size: String?,
    @SerializedName("region") var region: String?,
    @SerializedName("sellPrice") var salePrice: Double,
    @SerializedName("buyPrice") var buyPrice: Double,
    @SerializedName("name") var name: String?,
)