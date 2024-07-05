package com.example.hechoamano.ui.productentry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.domain.GetClientOrderID
import com.example.hechoamano.domain.GetClientOrders
import com.example.hechoamano.domain.GetClients
import com.example.hechoamano.domain.GetEmployeeOrderID
import com.example.hechoamano.domain.GetEmployeeOrders
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.EmployeeOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductEntryViewModel @Inject constructor(
    private val getEmployeeOrders: GetEmployeeOrders,
    private val getEmployeeOrderID: GetEmployeeOrderID
) : ViewModel() {

    val employeeOrderModel = MutableLiveData<List<EmployeeOrder>>()
    val isLoading = MutableLiveData<Boolean>()
    val navigateToCreate = MutableLiveData<Boolean>()
    val navigateToDetail = MutableLiveData<EmployeeOrder>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getEmployeeOrders()

            result?.let {
                employeeOrderModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    fun onItemSelected(employeeOrder: EmployeeOrder){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getEmployeeOrderID(employeeOrder.id)

            result?.let {
                navigateToDetail.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}