package com.example.hechoamano.data

import com.example.hechoamano.data.network.ApiService
import com.example.hechoamano.domain.model.InventoryControl
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.domain.model.toDomain
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ApiService) {

    suspend fun getProductsFromApi(): List<Product>{
        val response = api.getProducts()
        return response.map { it.toDomain() }
    }

    suspend fun getInventaryControlFromApi(): List<InventoryControl>{
        val response = api.getAllInventoryControls()
        return response.map { it.toDomain() }
    }
}