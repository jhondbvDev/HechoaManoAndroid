package com.example.hechoamano.domain

import com.example.hechoamano.data.ClientRepository
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import javax.inject.Inject

class GetClientOrderID @Inject constructor(
    private val repository: ClientRepository
){
    suspend operator fun invoke(id: String): ClientOrder? {
        return repository.getClientOrdersIDFromApi(id)
    }
}