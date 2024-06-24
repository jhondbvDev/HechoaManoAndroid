package com.example.hechoamano.ui.stockcontrol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.databinding.ActivitySummaryStockControlBinding
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.stockcontrol.adapter.SummaryProductsAdapter
import com.example.hechoamano.ui.home.HomeActivity
import java.text.NumberFormat
import java.util.Locale

class SummaryStockControlActivity : BaseActionBarActivity() {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private lateinit var binding: ActivitySummaryStockControlBinding
    private lateinit var adapter: SummaryProductsAdapter
    private var back: Boolean = false
    companion object {
        lateinit var employee: Employee
        lateinit var products: List<Product>

        fun getStartIntent(context: Context, employee: Employee, products: List<Product>): Intent {
            SummaryStockControlActivity.employee = employee
            SummaryStockControlActivity.products = products
            return Intent(context, SummaryStockControlActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        back = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle("Resumen Inventario")

        binding = ActivitySummaryStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.editProducts.setOnClickListener {
            back = true
            onBackPressed()
        }

        binding.buttonCancelar.setOnClickListener {
            onBackPressed()
        }

        binding.buttonCrearOrden.setOnClickListener {
            showConfirmationDialog()
        }
    }

    override fun onBackPressed() {
        if(back)
            super.onBackPressed()
        else{
            showCancelOrderDialog()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        adapter = SummaryProductsAdapter(products)
        binding.recyclerProducts.adapter = adapter
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea guardar la orden?")
            .setCancelable(false)
            .setTitle("Confirmación")
            .setPositiveButton("Sí") { dialog, id ->
                finish()
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
                finishAndRemoveTask()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun showCancelOrderDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de salir y cancelar la orden?")
            .setCancelable(false)
            .setTitle("Cancelar orden")
            .setPositiveButton("Sí") { dialog, id ->
                finish()
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
                finishAndRemoveTask()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}