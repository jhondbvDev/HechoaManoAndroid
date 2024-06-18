package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class ClientModel (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("shopName") val shopName: String,
    @SerializedName("city") val city: String
)