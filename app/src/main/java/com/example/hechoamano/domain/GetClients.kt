package com.example.hechoamano.domain

import com.example.hechoamano.data.ClientRepository
import com.example.hechoamano.domain.model.Client
import javax.inject.Inject

class GetClients @Inject constructor(
    private val repository: ClientRepository
){
    suspend operator fun invoke(): List<Client>? {
        return repository.getClientsFromApi()
    }
}