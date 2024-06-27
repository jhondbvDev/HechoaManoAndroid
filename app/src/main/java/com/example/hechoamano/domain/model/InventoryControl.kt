package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.InventoryControlModel
import kotlinx.serialization.Serializable

@Serializable
data class InventoryControl (
    val id: String,
    val employeeName: String,
    val createdDate: String
)

fun InventoryControlModel.toDomain() = InventoryControl(
    id,
    employeeName,
    createdDate
)
