package com.example.hechoamano

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.domain.GetProducts
import com.example.hechoamano.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProducts: GetProducts
) : ViewModel() {

    val productModel = MutableLiveData<List<Product>>()

    fun onCreate() {
        viewModelScope.launch {
            val result = getProducts()

            result?.let {
                productModel.postValue(it)
            }
        }
    }
}