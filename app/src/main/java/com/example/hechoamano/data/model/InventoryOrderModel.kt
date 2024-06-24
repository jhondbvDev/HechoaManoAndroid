package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class InventoryOrderModel(
    @SerializedName("employeeId") val employeeId: String,
    @SerializedName("details") val details: List<InventoryOrderDetailModel>
)