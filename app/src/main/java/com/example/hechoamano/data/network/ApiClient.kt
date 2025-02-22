package com.example.hechoamano.data.network

import com.example.hechoamano.data.authentication.LoginResponse
import com.example.hechoamano.data.model.ClientModel
import com.example.hechoamano.data.dto.ClientOrderDTO
import com.example.hechoamano.data.model.EmployeeModel
import com.example.hechoamano.data.dto.EmployeeOrderDTO
import com.example.hechoamano.data.dto.InventoryOrderDTO
import com.example.hechoamano.data.model.ClientOrderModel
import com.example.hechoamano.data.model.EmployeeOrderModel
import com.example.hechoamano.data.model.InventoryControlModel
import com.example.hechoamano.data.model.ProductModel
import com.example.hechoamano.data.util.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {
    @GET(Constants.PRODUCTS_URL)
    suspend fun getAllProducts(): Response<List<ProductModel>>

    @GET(Constants.EMPLOYEES_URL)
    suspend fun getAllEmployees(): Response<List<EmployeeModel>>

    @GET(Constants.CLIENTS_URL)
    suspend fun getAllClients(): Response<List<ClientModel>>

    @GET(Constants.CLIENT_ORDERS_URL)
    suspend fun getAllClientOrders(): Response<List<ClientOrderModel>>

    @GET(Constants.CLIENT_ORDERS_ID_URL)
    suspend fun getAllClientOrdersId(@Path("id") id: String): Response<ClientOrderModel>

    @GET(Constants.EMPLOYEE_ORDERS_URL)
    suspend fun getAllEmployeeOrders(): Response<List<EmployeeOrderModel>>

    @GET(Constants.EMPLOYEE_ORDERS_ID_URL)
    suspend fun getAllEmployeeOrdersId(@Path("id") id: String): Response<EmployeeOrderModel>

    @GET(Constants.INVENTORY_CONTROLS_URL)
    suspend fun getAllInventoryControls(): Response<List<InventoryControlModel>>

    @POST(Constants.LOGIN_URL)
    fun login(@Header("Authorization") login: String): Call<LoginResponse>

    @POST(Constants.POST_CLIENT_ORDERS_URL)
    fun saveClientOrders(@Body body: ClientOrderDTO): Call<ResponseBody>

    @POST(Constants.POST_EMPLOYEE_ORDERS_URL)
    fun saveEmployeeOrders(@Body body: EmployeeOrderDTO): Call<ResponseBody>

    @POST(Constants.POST_INVENTORY_CONTROLS_URL)
    fun saveInventoryControls(@Body body: InventoryOrderDTO): Call<ResponseBody>

    @DELETE(Constants.CLIENT_ORDERS_ID_URL)
    fun deleteClientOrder(@Path("id") id: String): Call<ResponseBody>

    @DELETE(Constants.EMPLOYEE_ORDERS_ID_URL)
    fun deleteEmployeeOrder(@Path("id") id: String): Call<ResponseBody>
}