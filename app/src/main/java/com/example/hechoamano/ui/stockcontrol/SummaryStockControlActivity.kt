package com.example.hechoamano.ui.stockcontrol

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.core.di.NetworkModule
import com.example.hechoamano.data.dto.DetailOrderDTO
import com.example.hechoamano.data.dto.EmployeeOrderDTO
import com.example.hechoamano.data.dto.InventoryOrderDTO
import com.example.hechoamano.data.dto.InventoryOrderDetailDTO
import com.example.hechoamano.data.network.ApiClient
import com.example.hechoamano.databinding.ActivitySummaryStockControlBinding
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.stockcontrol.adapter.SummaryProductsAdapter
import com.example.hechoamano.ui.home.HomeActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class SummaryStockControlActivity : BaseActionBarActivity() {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private lateinit var binding: ActivitySummaryStockControlBinding
    private lateinit var adapter: SummaryProductsAdapter

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle("Resumen Inventario")

        binding = ActivitySummaryStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.editProducts.setOnClickListener {
            onBackPressed()
        }

        binding.buttonCancelar.setOnClickListener {
            showCancelOrderDialog()
        }

        binding.buttonCrearOrden.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        adapter = SummaryProductsAdapter(products)
        binding.recyclerProducts.adapter = adapter
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea guardar el inventario?")
            .setCancelable(false)
            .setTitle("Confirmación")
            .setPositiveButton("Sí") { dialog, id ->
                saveStockControl()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun saveStockControl() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Guardando inventario...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        try {
            val apiService = NetworkModule.provideRetrofit(this).create(ApiClient::class.java)

            val employeeOrderDTO = InventoryOrderDTO(
                employee.id,
                products.map { product ->
                    InventoryOrderDetailDTO(
                        productId = product.id,
                        countedQuantity = product.stockEdited.toLong(),
                        systemQuantity = product.stock.toLong()
                    )
                }
            )

            apiService.saveInventoryControls(employeeOrderDTO).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.code() in 200..299) {
                        val builder = AlertDialog.Builder(this@SummaryStockControlActivity)
                        builder.setMessage("Inventario guardado exitosamente")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { dialog, id ->
                                progressDialog.hide()
                                finalizeOrder()
                            }
                        builder.create().show()
                    } else {
                        onUnknownError()
                        progressDialog.hide()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    onUnknownError()
                    progressDialog.hide()
                }
            })
        } catch (e: Exception) {
            onUnknownError()
            progressDialog.hide()
        }
    }

    private fun showCancelOrderDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de salir y cancelar la orden?")
            .setCancelable(false)
            .setTitle("Cancelar orden")
            .setPositiveButton("Sí") { dialog, id ->
                finalizeOrder()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun finalizeOrder() {
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
        finishAndRemoveTask()
    }
}