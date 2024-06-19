package com.example.hechoamano.ui.productentry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityProductsCustomerOrderBinding
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.productentry.adapter.ProductsAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProductsProductEntryActivity : BaseActionBarActivity() {

    private lateinit var binding: ActivityProductsCustomerOrderBinding

    private val productViewModel: ProductsProductEntryViewModel by viewModels()
    private lateinit var adapter: ProductsAdapter
    private lateinit var productArrayList: List<Product>

    companion object {
        lateinit var employee: Employee

        fun getStartIntent(context: Context, employee: Employee): Intent {
            ProductsProductEntryActivity.employee = employee
            return Intent(context, ProductsProductEntryActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsCustomerOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle("Productos")

        productViewModel.onCreate()

        productViewModel.productModel.observe(this) {
            productArrayList = it
            initRecyclerView()
        }

        productViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        productViewModel.navigateToSummary.observe(this, Observer { products ->
            startActivity(SummaryProductEntryActivity.getStartIntent(this, employee, products))
        })

        binding.swipeContainer.setOnRefreshListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Estás seguro de refrescar la lista? Se perderán los cambios realizados")
                .setCancelable(false)
                .setTitle("Refrescar resultados")
                .setPositiveButton("Sí") { dialog, id ->
                    binding.swipeContainer.isRefreshing = false
                    productViewModel.onCreate()
                }
                .setNegativeButton("No") { dialog, id ->
                    binding.swipeContainer.isRefreshing = false
                    dialog.dismiss()
                }
            builder.create().show()
        }

        binding.buttonAgregados.setOnClickListener {
            if(binding.buttonAgregados.text == "Ver todos"){
                binding.buttonAgregados.text = "Ver agregados"
                adapter.filterList(productArrayList)
            } else {
                binding.buttonAgregados.text = "Ver todos"
                adapter.filterList(productArrayList.filter { it.edited })
            }
        }

        binding.buttonSiguiente.setOnClickListener {
            if(productArrayList.none { it.edited }){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("No has agregado ningún producto a la orden")
                    .setCancelable(false)
                    .setTitle("Orden vacía")
                    .setPositiveButton("Ok") { dialog, id ->
                        dialog.dismiss()
                    }
                builder.create().show()
            } else {
                productViewModel.navigateToSummary.postValue(productArrayList.filter { it.edited })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.actionSearch)

        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        val filtered = ArrayList<Product>()

        for (item in productArrayList) {
            if (item.name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                || item.family.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                || item.subfamily.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                || item.type.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                || item.type.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                || item.size.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))){
                filtered.add(item)
            }
        }
        adapter.filterList(filtered)
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        adapter = ProductsAdapter(productArrayList)
        binding.recyclerProducts.adapter = adapter
        val emptyDataObserver = EmptyDataObserver(binding.recyclerProducts, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }
}