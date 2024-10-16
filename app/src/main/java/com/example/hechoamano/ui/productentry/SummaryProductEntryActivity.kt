package com.example.hechoamano.ui.productentry

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.core.di.NetworkModule
import com.example.hechoamano.data.dto.DetailOrderDTO
import com.example.hechoamano.data.dto.EmployeeOrderDTO
import com.example.hechoamano.data.network.ApiClient
import com.example.hechoamano.databinding.ActivitySummaryProductEntryBinding
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.EmployeeOrder
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.customerorders.SummaryCustomerOrderActivity
import com.example.hechoamano.ui.home.HomeActivity
import com.example.hechoamano.ui.productentry.adapter.SummaryProductsAdapter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class SummaryProductEntryActivity : BaseActionBarActivity() {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private lateinit var binding: ActivitySummaryProductEntryBinding
    private lateinit var adapter: SummaryProductsAdapter

    companion object {
        lateinit var employee: Employee
        lateinit var products: List<Product>
        lateinit var employeeOrder: EmployeeOrder
        private var readOnly: Boolean = false

        fun getStartIntent(context: Context, employee: Employee, products: List<Product>, readOnly: Boolean = false, employeeOrder: EmployeeOrder? = null): Intent {
            SummaryProductEntryActivity.readOnly = readOnly
            SummaryProductEntryActivity.employee = employee
            SummaryProductEntryActivity.products = products
            employeeOrder?.let { SummaryProductEntryActivity.employeeOrder = it }
            return Intent(context, SummaryProductEntryActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle("Detalle de la entrada")

        binding = ActivitySummaryProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadInfo()
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

        binding.buttonEliminarOrden.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun loadInfo() {

        if(readOnly){
            binding.editProducts.visibility = View.GONE
            binding.buttonCrearOrden.visibility = View.GONE
            binding.buttonCancelar.visibility = View.GONE
            binding.buttonEliminarOrden.visibility = View.VISIBLE

            binding.total.text = format.format(employeeOrder.totalPrice)
            binding.employeeName.text = employeeOrder.employeeName
            binding.employeeTotal.text = format.format(employeeOrder.totalPrice)
        } else {
            binding.editProducts.visibility = View.VISIBLE
            binding.buttonCrearOrden.visibility = View.VISIBLE
            binding.buttonCancelar.visibility = View.VISIBLE
            binding.buttonEliminarOrden.visibility = View.GONE

            val total = products.sumOf { it.stockEdited * it.buyPrice }
            binding.total.text = format.format(total)

            binding.employeeName.text = employee.name
            binding.employeeTotal.text = format.format(total)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        adapter = SummaryProductsAdapter(products)
        binding.recyclerProducts.adapter = adapter
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea guardar la entrada?")
            .setCancelable(false)
            .setTitle("Confirmación")
            .setPositiveButton("Sí") { dialog, id ->
                saveOrder()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun saveOrder() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Guardando entrada...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        try {
            val apiService = NetworkModule.provideRetrofit(this).create(ApiClient::class.java)

            val employeeOrderDTO = EmployeeOrderDTO(
                employee.id,
                products.map { product ->
                    DetailOrderDTO(
                        productId = product.id,
                        quantity = product.stockEdited.toLong(),
                        price = product.buyPrice
                    )
                },
                products.sumOf { it.buyPrice * it.stockEdited.toDouble() },
            )

            apiService.saveEmployeeOrders(employeeOrderDTO).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.code() in 200..299) {
                        val builder = AlertDialog.Builder(this@SummaryProductEntryActivity)
                        builder.setMessage("Entrada guardada exitosamente")
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
        builder.setMessage("¿Estás seguro de salir y cancelar la entrada?")
            .setCancelable(false)
            .setTitle("Cancelar entrada")
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

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de eliminar la entrada?")
            .setCancelable(false)
            .setTitle("Eliminar entrada")
            .setPositiveButton("Sí") { dialog, id ->
                deleteOrder()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun deleteOrder() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Eliminando entrada...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        try {
            val apiService = NetworkModule.provideRetrofit(this).create(ApiClient::class.java)

            apiService.deleteEmployeeOrder(SummaryProductEntryActivity.employeeOrder.id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.code() in 200..299) {
                        val builder = AlertDialog.Builder(this@SummaryProductEntryActivity)
                        builder.setMessage("Entrada eliminada correctamente")
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
}