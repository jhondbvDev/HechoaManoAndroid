package com.example.hechoamano.ui.customerorders

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.core.di.NetworkModule
import com.example.hechoamano.data.authentication.LoginRequest
import com.example.hechoamano.data.authentication.LoginResponse
import com.example.hechoamano.data.dto.ClientOrderDTO
import com.example.hechoamano.data.dto.DetailOrderDTO
import com.example.hechoamano.data.network.ApiClient
import com.example.hechoamano.data.network.ApiService
import com.example.hechoamano.data.network.LoginClient
import com.example.hechoamano.data.session.SessionManager
import com.example.hechoamano.databinding.ActivityProductsCustomerOrderBinding
import com.example.hechoamano.databinding.ActivitySummaryCustomerOrderBinding
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.customerorders.adapter.ProductsAdapter
import com.example.hechoamano.ui.customerorders.adapter.SummaryProductsAdapter
import com.example.hechoamano.ui.home.HomeActivity
import com.example.hechoamano.ui.util.EmptyDataObserver
import okhttp3.Credentials
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset
import java.text.NumberFormat
import java.util.Locale

class SummaryCustomerOrderActivity : BaseActionBarActivity() {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private lateinit var binding: ActivitySummaryCustomerOrderBinding
    private lateinit var adapter: SummaryProductsAdapter

    companion object {
        lateinit var client: Client
        lateinit var products: List<Product>

        fun getStartIntent(context: Context, client: Client, products: List<Product>): Intent {
            SummaryCustomerOrderActivity.client = client
            SummaryCustomerOrderActivity.products = products
            return Intent(context, SummaryCustomerOrderActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle("Detalle de la orden")

        binding = ActivitySummaryCustomerOrderBinding.inflate(layoutInflater)
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
    }

    private fun loadInfo() {
        val subtotal = products.sumOf { it.salePrice * it.stockEdited.toDouble() }
        //val iva = subtotal * 0.19
        val discount = (subtotal * client.discount / 100)
        //val total = subtotal + iva - discount
        val total = subtotal - discount

        binding.subtotal.text = format.format(subtotal)
        //binding.iva.text = format.format(iva)
        binding.iva.visibility = View.GONE
        binding.labelIva.visibility = View.GONE

        binding.descuento.text = format.format(discount)
        binding.total.text = format.format(total)

        binding.clientName.text = client.name
        binding.clientCity.text = client.city
        binding.clientShopName.text = client.shopName
        binding.clientTotal.text = format.format(total)
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
                saveOrder()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun saveOrder() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Guardando orden...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        try {
            val apiService = NetworkModule.provideRetrofit(this).create(ApiClient::class.java)

            val clientOrderDTO = ClientOrderDTO(
                client.id,
                products.map { product ->
                    DetailOrderDTO(
                        productId = product.id,
                        quantity = product.stockEdited.toLong(),
                        price = product.salePrice
                    )
                },
                products.sumOf { it.salePrice * it.stockEdited.toDouble() },
            )

            apiService.saveClientOrders(clientOrderDTO).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.code() in 200..299) {
                        val builder = AlertDialog.Builder(this@SummaryCustomerOrderActivity)
                        builder.setMessage("Orden guardada exitosamente")
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