package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.ProductModel

data class Product(
    val id: String,
    val stock: String,
    val family: String,
    val subfamily: String,
    val type: String,
    val size: String,
    val region: String,
    val salePrice: String,
    val buyPrice: String,
    val name: String
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