package com.example.hechoamano.domain

import com.example.hechoamano.data.ProductRepository
import com.example.hechoamano.domain.model.Product
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repository: ProductRepository
){
    suspend operator fun invoke(): List<Product>? {
        return repository.getProductsFromApi()
    }
}