package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.ProductModel

data class Product(
    val id: String,
    var stock: Int,
    val family: String?,
    val subfamily: String?,
    val type: String?,
    val size: String?,
    val region: String?,
    val salePrice: Double,
    val buyPrice: Double,
    val name: String?,
    var edited: Boolean = false,
    var stockEdited: Int = 0
)

fun ProductModel.toDomain() = Product(
    id,
    stock,
    family,
    subfamily,
    type,
    size,
    region,
    salePrice,
    buyPrice,
    name
)