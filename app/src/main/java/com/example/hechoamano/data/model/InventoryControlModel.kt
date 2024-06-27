package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class InventoryControlModel(
    @SerializedName("id") val id: String,
    @SerializedName("employeeName") val employeeName: String,
    @SerializedName("createdDate") val createdDate: String,
)
