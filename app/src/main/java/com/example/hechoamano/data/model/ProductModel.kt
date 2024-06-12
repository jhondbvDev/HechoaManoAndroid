package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class ProductModel (
    @SerializedName("id") var id: String,
    @SerializedName("stock") var stock: String,
    @SerializedName("family") var family: String,
    @SerializedName("subfamily") var subfamily: String,
    @SerializedName("type") var type: String,
    @SerializedName("size") var size: String,
    @SerializedName("region") var region: String,
    @SerializedName("salePrice") var salePrice: String,
    @SerializedName("buyPrice") var buyPrice: String,
    @SerializedName("name") var name: String,
)