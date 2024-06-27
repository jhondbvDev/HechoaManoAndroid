package com.example.hechoamano.domain

import com.example.hechoamano.data.ClientRepository
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import javax.inject.Inject

class GetClientOrders @Inject constructor(
    private val repository: ClientRepository
){
    suspend operator fun invoke(): List<ClientOrder>? {
        return repository.getClientOrdersFromApi()
    }
}