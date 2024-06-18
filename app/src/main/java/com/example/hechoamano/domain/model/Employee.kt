package com.example.hechoamano.domain.model

import com.example.hechoamano.data.model.EmployeeModel

data class Employee (
    val id: String,
    val name: String
)

fun EmployeeModel.toDomain() = Employee(
    id,
    name
)