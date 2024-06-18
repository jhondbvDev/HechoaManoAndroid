package com.example.hechoamano.domain

import com.example.hechoamano.data.EmployeeRepository
import com.example.hechoamano.domain.model.Employee
import javax.inject.Inject

class GetEmployees @Inject constructor(
    private val repository: EmployeeRepository
){
    suspend operator fun invoke(): List<Employee>? {
        return repository.getEmployeesFromApi()
    }
}