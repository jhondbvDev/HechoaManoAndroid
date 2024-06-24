package com.example.hechoamano.data.network

import com.example.hechoamano.data.authentication.LoginRequest
import com.example.hechoamano.data.model.ClientModel
import com.example.hechoamano.data.model.ClientOrderModel
import com.example.hechoamano.data.model.EmployeeModel
import com.example.hechoamano.data.model.EmployeeOrderModel
import com.example.hechoamano.data.model.InventoryOrderModel
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

    suspend fun saveClientOrders(clientOrderModel : ClientOrderModel){
        return  withContext(Dispatchers.IO){
            api.saveClientOrders(clientOrderModel)
        }
    }

    suspend fun saveEmployeeOrders(employeeOrderModel: EmployeeOrderModel){
        return  withContext(Dispatchers.IO){
            api.saveEmployeeOrders(employeeOrderModel)
        }
    }

    suspend fun saveInventaryControls(inventoryOrderModel: InventoryOrderModel){
        return  withContext(Dispatchers.IO){
            api.saveInventoryControls(inventoryOrderModel)
        }
    }
}