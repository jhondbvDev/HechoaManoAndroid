package com.example.hechoamano.data

import com.example.hechoamano.data.network.ApiService
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.EmployeeOrder
import com.example.hechoamano.domain.model.toDomain
import javax.inject.Inject

class EmployeeRepository@Inject constructor(private val api: ApiService) {
    suspend fun getEmployeesFromApi(): List<Employee>{
        val response = api.getEmployees()
        return response.map { it.toDomain() }
    }

    suspend fun  getEmployeeOrdersFromApi(): List<EmployeeOrder>{
        val response = api.getAllEmployeeOrders()
        return response.map { it.toDomain() }
    }

    suspend fun  getEmployeeOrdersIDFromApi(id: String): EmployeeOrder? {
        val response = api.getAllEmployeeOrderId(id)
        return response?.toDomain()
    }
}