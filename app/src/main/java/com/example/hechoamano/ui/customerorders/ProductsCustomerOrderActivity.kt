package com.example.hechoamano.ui.customerorders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityProductsCustomerOrderBinding
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.customerorders.adapter.ProductsAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProductsCustomerOrderActivity : BaseActionBarActivity() {

    private lateinit var binding: ActivityProductsCustomerOrderBinding

    private val productViewModel: ProductsCustomerOrderViewModel by viewModels()
    private lateinit var adapter: ProductsAdapter
    private lateinit var productArrayList: List<Product>
    private lateinit var productEditedArrayList: List<Product>

    companion object {
        lateinit var client: Client

        fun getStartIntent(context: Context, client: Client): Intent {
            ProductsCustomerOrderActivity.client = client
            return Intent(context, ProductsCustomerOrderActivity::class.java)
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

        productViewModel.navigateToSummary.observe(this, Observer { product ->
            //startActivity(EmptyCustomerOrderActivity.getStartIntent(this, product))
        })

        productEditedArrayList = ArrayList()
        productViewModel.onClickItem.observe(this, Observer { product ->
            productEditedArrayList.plus(product)
        })

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            productViewModel.onCreate()
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
        adapter = ProductsAdapter(productArrayList) { onItemSelected(it) }
        binding.recyclerProducts.adapter = adapter
        val emptyDataObserver = EmptyDataObserver(binding.recyclerProducts, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun onItemSelected(product: Product) {
        productViewModel.onClickItem.postValue(product)
    }
}