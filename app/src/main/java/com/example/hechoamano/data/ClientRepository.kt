package com.example.hechoamano.data

import com.example.hechoamano.data.network.ApiService
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.toDomain
import javax.inject.Inject

class ClientRepository@Inject constructor(private val api: ApiService) {
    suspend fun getClientsFromApi() : List<Client>{
        val response = api.getClients()
        return response.map { it.toDomain() }
    }

    suspend fun getClientOrdersFromApi() : List<ClientOrder>{
        val response = api.getAllClientOrders()
        return response.map { it.toDomain() }
    }
}