package com.example.hechoamano.domain

import com.example.hechoamano.data.ProductRepository
import com.example.hechoamano.domain.model.InventoryControl
import com.example.hechoamano.domain.model.Product
import javax.inject.Inject

class GetInventoryControl @Inject constructor(
    private val repository: ProductRepository
){
    suspend operator fun invoke(): List<InventoryControl>? {
        return repository.getInventaryControlFromApi()
    }
}