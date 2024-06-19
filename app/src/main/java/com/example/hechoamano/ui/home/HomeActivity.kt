package com.example.hechoamano.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hechoamano.databinding.ActivityHomeBinding
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.customerorders.CustomerOrderActivity
import com.example.hechoamano.ui.customerorders.EmptyCustomerOrderActivity
import com.example.hechoamano.ui.productentry.ProductEntryActivity
import com.example.hechoamano.ui.stockcontrol.StockControlActivity

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClientes.setOnClickListener {
            val intent = Intent(this, EmptyCustomerOrderActivity::class.java)
            startActivity(intent)
        }

        binding.buttonProductos.setOnClickListener {
            val intent = Intent(this, ProductEntryActivity::class.java)
            startActivity(intent)
        }

        binding.buttonInventario.setOnClickListener {
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