package com.example.hechoamano.data.network

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class ApiService @Inject constructor(private val api: ApiClient) {

    suspend fun getProducts(): List<ProductModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllProducts()
            response.body() ?: emptyList()
        }
    }

    suspend fun getEmployees(): List<EmployeeModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllEmployees()
            response.body() ?: emptyList()
        }
    }

    suspend fun getClients(): List<ClientModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllClients()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAllClientOrders(): List<ClientOrderModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllClientOrders()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAllClientOrderId(id: String): ClientOrderModel?{
        return  withContext(Dispatchers.IO){
            val response = api.getAllClientOrdersId(id)
            response.body()
        }
    }

    suspend fun getAllEmployeeOrders(): List<EmployeeOrderModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllEmployeeOrders()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAllEmployeeOrderId(id: String): EmployeeOrderModel?{
        return  withContext(Dispatchers.IO){
            val response = api.getAllEmployeeOrdersId(id)
            response.body()
        }
    }

    suspend fun getAllInventoryControls(): List<InventoryControlModel>{
        return  withContext(Dispatchers.IO){
            val response = api.getAllInventoryControls()
            response.body() ?: emptyList()
        }
    }

    suspend fun saveClientOrders(clientOrderModel : ClientOrderDTO){
        return  withContext(Dispatchers.IO){
            api.saveClientOrders(clientOrderModel)
        }
    }

    suspend fun saveEmployeeOrders(employeeOrderModel: EmployeeOrderDTO){
        return  withContext(Dispatchers.IO){
            api.saveEmployeeOrders(employeeOrderModel)
        }
    }

    suspend fun saveInventaryControls(inventoryOrderModel: InventoryOrderDTO){
        return  withContext(Dispatchers.IO){
            api.saveInventoryControls(inventoryOrderModel)
        }
    }
}