package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class EmployeeOrderModel(
    @SerializedName("employeeId") val employeeId: String,
    @SerializedName("details") val details: List<DetailOrderModel>,
    @SerializedName("totalPrice") val totalPrice: Long
)
