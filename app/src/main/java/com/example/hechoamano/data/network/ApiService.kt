package com.example.hechoamano.data.network

import com.example.hechoamano.data.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val api: ApiClient) {

    suspend fun getProducts(): List<ProductModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllProducts()
            response.body() ?: emptyList()
        }
    }
}