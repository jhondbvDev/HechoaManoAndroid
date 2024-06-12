package com.example.hechoamano.data.network

import com.example.hechoamano.data.model.ProductModel
import java.util.Objects
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {
    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductModel>>
}