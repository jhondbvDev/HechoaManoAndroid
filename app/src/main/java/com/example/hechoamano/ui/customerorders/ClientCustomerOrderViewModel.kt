package com.example.hechoamano.ui.customerorders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.domain.GetClients
import com.example.hechoamano.domain.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientCustomerOrderViewModel @Inject constructor(
    private val getClients: GetClients
) : ViewModel() {

    val clientModel = MutableLiveData<List<Client>>()
    val isLoading = MutableLiveData<Boolean>()
    val navigateToProducts = MutableLiveData<Client>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getClients()

            result?.let {
                clientModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}