package com.example.hechoamano.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.hechoamano.R
import com.example.hechoamano.data.session.SessionManager
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class LoadInventoryActivity : BaseActivity() {

    private val loadInventoryViewModel: LoadInventoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_inventary)

        val sessionManager = SessionManager(this)
        loadInventoryViewModel.onCreate()

        loadInventoryViewModel.inventoryControlModel.observe(this) {
            sessionManager.savePreference(Constants.INVENTORY_CONTROLS_PREFERENCE, Json.encodeToString(it))
        }

        loadInventoryViewModel.clientOrderModel.observe(this) {
            sessionManager.savePreference(Constants.CLIENT_ORDERS_PREFERENCE, Json.encodeToString(it))
        }

        loadInventoryViewModel.employeeOrderModel.observe(this) {
            sessionManager.savePreference(Constants.EMPLOYEE_ORDERS_PREFERENCE, Json.encodeToString(it))
        }

        loadInventoryViewModel.isLoading.observe(this) { isLoading ->
            if (!isLoading) {
                startHomeActivity()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (loadInventoryViewModel.isLoading.value == true) {
                startHomeActivity()
            }
        }, 5000) // 5 seconds
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}