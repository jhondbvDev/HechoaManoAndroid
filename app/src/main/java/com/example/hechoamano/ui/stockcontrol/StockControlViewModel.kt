package com.example.hechoamano.ui.stockcontrol

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.domain.GetInventoryControl
import com.example.hechoamano.domain.model.InventoryControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockControlViewModel @Inject constructor(
    private val getInventoryControl: GetInventoryControl
) : ViewModel() {

    val employeeOrderModel = MutableLiveData<List<InventoryControl>>()
    val isLoading = MutableLiveData<Boolean>()
    val navigateToCreate = MutableLiveData<Boolean>()
    val navigateToDetail = MutableLiveData<InventoryControl>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getInventoryControl()

            result?.let {
                employeeOrderModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}