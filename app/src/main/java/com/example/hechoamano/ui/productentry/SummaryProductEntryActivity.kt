package com.example.hechoamano.ui.productentry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.databinding.ActivitySummaryProductEntryBinding
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.home.HomeActivity
import com.example.hechoamano.ui.productentry.adapter.SummaryProductsAdapter
import java.text.NumberFormat
import java.util.Locale

class SummaryProductEntryActivity : BaseActionBarActivity() {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private lateinit var binding: ActivitySummaryProductEntryBinding
    private lateinit var adapter: SummaryProductsAdapter
    private var back: Boolean = false
    companion object {
        lateinit var employee: Employee
        lateinit var products: List<Product>

        fun getStartIntent(context: Context, employee: Employee, products: List<Product>): Intent {
            SummaryProductEntryActivity.employee = employee
            SummaryProductEntryActivity.products = products
            return Intent(context, SummaryProductEntryActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        back = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle("Detalle de la orden")

        binding = ActivitySummaryProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadInfo()
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

    private fun loadInfo() {
        val total = products.sumOf { it.stockEdited * it.buyPrice }
        binding.total.text = format.format(total)

        binding.employeeName.text = employee.name
        binding.employeeTotal.text = format.format(total)
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