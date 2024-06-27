package com.example.hechoamano.data.dto

import com.google.gson.annotations.SerializedName

data class InventoryOrderDTO(
    @SerializedName("employeeId") val employeeId: String,
    @SerializedName("details") val details: List<InventoryOrderDetailDTO>
)