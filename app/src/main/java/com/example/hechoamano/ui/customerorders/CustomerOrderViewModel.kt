package com.example.hechoamano.ui.customerorders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.domain.GetClientOrders
import com.example.hechoamano.domain.GetClients
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerOrderViewModel @Inject constructor(
    private val getClientOrders: GetClientOrders
) : ViewModel() {

    val clientOrderModel = MutableLiveData<List<ClientOrder>>()
    val isLoading = MutableLiveData<Boolean>()
    val navigateToCreate = MutableLiveData<Boolean>()
    val navigateToDetail = MutableLiveData<ClientOrder>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getClientOrders()

            result?.let {
                clientOrderModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}