package com.example.hechoamano.ui.productentry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hechoamano.domain.GetEmployees
import com.example.hechoamano.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EmployeesProductEntryViewModel @Inject constructor(
    private val getEmployees: GetEmployees
) : ViewModel() {

    val employeeModel = MutableLiveData<List<Employee>>()
    val isLoading = MutableLiveData<Boolean>()
    val navigateToProducts = MutableLiveData<Employee>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getEmployees()

            result?.let {
                employeeModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}