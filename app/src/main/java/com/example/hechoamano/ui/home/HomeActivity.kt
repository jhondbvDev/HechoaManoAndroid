package com.example.hechoamano.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import com.example.hechoamano.data.session.SessionManager
import com.example.hechoamano.databinding.ActivityHomeBinding
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.customerorders.CustomerOrderActivity
import com.example.hechoamano.ui.customerorders.EmptyCustomerOrderActivity
import com.example.hechoamano.ui.productentry.EmptyProductEntryActivity
import com.example.hechoamano.ui.productentry.ProductEntryActivity
import com.example.hechoamano.ui.stockcontrol.EmptyStockControlActivity
import com.example.hechoamano.ui.stockcontrol.StockControlActivity
import com.example.hechoamano.ui.util.Constants

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClientes.setOnClickListener {
            validateAndNavigateClientOrders()
        }

        binding.buttonProductos.setOnClickListener {
            validateAndNavigateEmployeeOrder()
        }

        binding.buttonInventario.setOnClickListener {
            validateAndNavigateStockControl()
        }
    }

    private fun validateAndNavigateClientOrders() {
        val sessionManager = SessionManager(this)
        if (sessionManager.getPreference(Constants.CLIENT_ORDERS_PREFERENCE).isNullOrEmpty()) {
            val intent = Intent(this, EmptyCustomerOrderActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, CustomerOrderActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateAndNavigateEmployeeOrder() {
        val sessionManager = SessionManager(this)
        if (sessionManager.getPreference(Constants.EMPLOYEE_ORDERS_PREFERENCE).isNullOrEmpty()) {
            val intent = Intent(this, EmptyProductEntryActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, ProductEntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateAndNavigateStockControl() {
        val sessionManager = SessionManager(this)
        if (sessionManager.getPreference(Constants.INVENTORY_CONTROLS_PREFERENCE).isNullOrEmpty()) {
            val intent = Intent(this, EmptyStockControlActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, StockControlActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de cerrar la sesión?")
            .setCancelable(false)
            .setTitle("Cerrar sesión")
            .setPositiveButton("Sí") { dialog, id ->
                super.onBackPressed()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}