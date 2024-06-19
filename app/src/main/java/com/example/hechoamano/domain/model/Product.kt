package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.ProductModel

data class Product(
    val id: String,
    var stock: String,
    val family: String,
    val subfamily: String,
    val type: String,
    val size: String,
    val region: String,
    val salePrice: String,
    val buyPrice: String,
    val name: String,
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