package com.example.hechoamano.data.dto

import com.google.gson.annotations.SerializedName

data class EmployeeOrderDTO(
    @SerializedName("employeeId") val employeeId: String,
    @SerializedName("details") val details: List<DetailOrderDTO>,
    @SerializedName("totalPrice") val totalPrice: Double
)
