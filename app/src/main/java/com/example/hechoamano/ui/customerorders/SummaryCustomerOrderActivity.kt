package com.example.hechoamano.ui.customerorders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityProductsCustomerOrderBinding
import com.example.hechoamano.databinding.ActivitySummaryCustomerOrderBinding
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.customerorders.adapter.ProductsAdapter
import com.example.hechoamano.ui.customerorders.adapter.SummaryProductsAdapter
import com.example.hechoamano.ui.home.HomeActivity
import com.example.hechoamano.ui.util.EmptyDataObserver
import java.text.NumberFormat
import java.util.Locale

class SummaryCustomerOrderActivity : BaseActionBarActivity() {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private lateinit var binding: ActivitySummaryCustomerOrderBinding
    private lateinit var adapter: SummaryProductsAdapter
    private var back: Boolean = false
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
        back = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle("Detalle de la orden")

        binding = ActivitySummaryCustomerOrderBinding.inflate(layoutInflater)
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