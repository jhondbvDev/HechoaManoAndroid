package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.DetailOrderModel
import kotlinx.serialization.Serializable

@Serializable
data class DetailOrder(
    val productId: String,
    val productName: String,
    val productFamily: String?,
    val productSubFamily: String?,
    val productRegion: String?,
    val productFamilyType: String?,
    val productSize: String?,
    val quantity: Long,
    val price: Double,
)

fun DetailOrderModel.toDomain() = DetailOrder(
    productId,
    productName,
    productFamily,
    productSubFamily,
    productRegion,
    productFamilyType,
    productSize,
    quantity,
    price
)
