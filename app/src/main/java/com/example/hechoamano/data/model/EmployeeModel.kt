package com.example.hechoamano.data.model

import com.google.gson.annotations.SerializedName

data class EmployeeModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)