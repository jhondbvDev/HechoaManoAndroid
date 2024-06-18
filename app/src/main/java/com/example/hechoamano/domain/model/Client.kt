package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.ClientModel

data class Client (
    val id: String,
    val name: String,
    val shopName : String,
    val city: String
)

fun ClientModel.toDomain() = Client(
    id,
    name,
    shopName,
    city
)