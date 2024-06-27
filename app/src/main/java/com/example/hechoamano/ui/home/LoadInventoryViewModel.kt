package com.example.hechoamano.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.data.session.SessionManager
import com.example.hechoamano.domain.GetClientOrders
import com.example.hechoamano.domain.GetEmployeeOrders
import com.example.hechoamano.domain.GetInventoryControl
import com.example.hechoamano.domain.GetProducts
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.EmployeeOrder
import com.example.hechoamano.domain.model.InventoryControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadInventoryViewModel @Inject constructor(
    private val getInventoryControl: GetInventoryControl,
    private val getEmployeeOrders: GetEmployeeOrders,
    private val getClientOrders: GetClientOrders
) : ViewModel()  {
    val inventoryControlModel = MutableLiveData<List<InventoryControl>>()
    val employeeOrderModel = MutableLiveData<List<EmployeeOrder>>()
    val clientOrderModel = MutableLiveData<List<ClientOrder>>()

    val isLoadingInventoryControl = MutableLiveData<Boolean>()
    val isLoadingEmployeeOrder = MutableLiveData<Boolean>()
    val isLoadingClientOrder = MutableLiveData<Boolean>()

    val isLoading = MediatorLiveData<Boolean>().apply {
        addSource(isLoadingInventoryControl) { value = it || isLoadingEmployeeOrder.value == true || isLoadingClientOrder.value == true }
        addSource(isLoadingEmployeeOrder) { value = it || isLoadingInventoryControl.value == true || isLoadingClientOrder.value == true }
        addSource(isLoadingClientOrder) { value = it || isLoadingInventoryControl.value == true || isLoadingEmployeeOrder.value == true }
    }

    fun onCreate() {
        viewModelScope.launch {
            isLoadingInventoryControl.postValue(true)
            isLoadingEmployeeOrder.postValue(true)
            isLoadingClientOrder.postValue(true)

            val resultInventoryControl = getInventoryControl()
            val resultEmployeeOrders = getEmployeeOrders()
            val resultClientOrders = getClientOrders()

            resultInventoryControl?.let {
                inventoryControlModel.postValue(it)
                isLoadingInventoryControl.postValue(false)
            }

            resultEmployeeOrders?.let {
                employeeOrderModel.postValue(it)
                isLoadingEmployeeOrder.postValue(false)
            }

            resultClientOrders?.let {
                clientOrderModel.postValue(it)
                isLoadingClientOrder.postValue(false)
            }
        }
    }
}