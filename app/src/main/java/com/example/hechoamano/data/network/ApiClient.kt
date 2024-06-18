package com.example.hechoamano.data.network

import com.example.hechoamano.data.model.ClientModel
import com.example.hechoamano.data.model.EmployeeModel
import com.example.hechoamano.data.model.ProductModel
import java.util.Objects
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClient {
    @GET("Products")
    suspend fun getAllProducts(): Response<List<ProductModel>>

    @GET("Employees")
    suspend fun getAllEmployees(): Response<List<EmployeeModel>>

    @GET("Clients")
    suspend fun getAllClients(): Response<List<ClientModel>>

    @POST("Authentication")
    suspend fun login(username: String, password: String): Response<Boolean>
}