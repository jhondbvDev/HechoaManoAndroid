package com.example.hechoamano.data.network

import com.example.hechoamano.data.authentication.LoginRequest
import com.example.hechoamano.data.authentication.LoginResponse
import com.example.hechoamano.data.model.ClientModel
import com.example.hechoamano.data.model.ClientOrderModel
import com.example.hechoamano.data.model.EmployeeModel
import com.example.hechoamano.data.model.EmployeeOrderModel
import com.example.hechoamano.data.model.InventoryOrderModel
import com.example.hechoamano.data.model.ProductModel
import com.example.hechoamano.data.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiClient {
    @GET(Constants.PRODUCTS_URL)
    suspend fun getAllProducts(): Response<List<ProductModel>>

    @GET(Constants.EMPLOYEES_URL)
    suspend fun getAllEmployees(): Response<List<EmployeeModel>>

    @GET(Constants.CLIENTS_URL)
    suspend fun getAllClients(): Response<List<ClientModel>>

    @POST(Constants.LOGIN_URL)
    fun login(@Header("Authorization") login: String): Call<LoginResponse>

    @POST(Constants.POST_CLIENT_ORDERS_URL)
    fun saveClientOrders(@Body body: ClientOrderModel)

    @POST(Constants.POST_EMPLOYEE_ORDERS_URL)
    fun saveEmployeeOrders(@Body body: EmployeeOrderModel)

    @POST(Constants.POST_INVENTORY_CONTROLS_URL)
    fun saveInventoryControls(@Body body: InventoryOrderModel)
}