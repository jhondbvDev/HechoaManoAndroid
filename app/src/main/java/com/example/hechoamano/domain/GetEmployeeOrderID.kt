package com.example.hechoamano.domain

import com.example.hechoamano.data.ClientRepository
import com.example.hechoamano.data.EmployeeRepository
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.EmployeeOrder
import javax.inject.Inject

class GetEmployeeOrderID @Inject constructor(
    private val repository: EmployeeRepository
){
    suspend operator fun invoke(id: String): EmployeeOrder? {
        return repository.getEmployeeOrdersIDFromApi(id)
    }
}