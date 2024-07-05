package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class EmployeeOrderModel(
    @SerializedName("id") val id: String,
    @SerializedName("employeeName") val employeeName: String,
    @SerializedName("totalPrice") val totalPrice: Double,
    @SerializedName("createdDate") val createdDate: String,
    @SerializedName("details") val details: List<DetailOrderModel>?
)