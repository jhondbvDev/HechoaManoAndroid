package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.ClientOrderModel
import kotlinx.serialization.Serializable

@Serializable
data class ClientOrder (
    val id: String,
    val clientName: String,
    val shopName: String,
    val totalPrice: Double,
    val createdDate: String,
)

fun ClientOrderModel.toDomain() = ClientOrder(
    id,
    clientName,
    shopName,
    totalPrice,
    createdDate
)