package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.ClientOrderModel
import com.example.hechoamano.data.model.DetailOrderModel
import kotlinx.serialization.Serializable

@Serializable
data class ClientOrder (
    val id: String,
    val clientName: String,
    val shopName: String,
    val city: String?,
    val calculetedDiscount: Double?,
    val totalPrice: Double,
    val subtotal: Double,
    val createdDate: String?,
    val details: List<DetailOrder>?
)

fun ClientOrderModel.toDomain() = ClientOrder(
    id,
    clientName,
    shopName,
    city,
    calculetedDiscount,
    totalPrice,
    subtotal,
    createdDate,
    details?.map { it.toDomain() }
)