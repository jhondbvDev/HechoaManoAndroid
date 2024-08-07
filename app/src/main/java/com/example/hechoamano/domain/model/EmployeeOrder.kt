package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.EmployeeOrderModel
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeOrder (
    val id: String,
    val employeeName: String,
    val totalPrice: Double,
    val createdDate: String?,
    val details: List<DetailOrder>?
)

fun EmployeeOrderModel.toDomain() = EmployeeOrder(
    id,
    employeeName,
    totalPrice,
    createdDate,
    details?.map { it.toDomain() }
)