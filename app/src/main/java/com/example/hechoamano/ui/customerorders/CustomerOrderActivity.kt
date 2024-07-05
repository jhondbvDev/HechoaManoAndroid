package com.example.hechoamano.ui.customerorders

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityCustomerOrderBinding
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.domain.model.Product
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.customerorders.ProductsCustomerOrderActivity.Companion
import com.example.hechoamano.ui.customerorders.adapter.ClientOrdersAdapter
import com.example.hechoamano.ui.customerorders.adapter.ClientsAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class CustomerOrderActivity : BaseActionBarActivity() {

    private lateinit var binding: ActivityCustomerOrderBinding

    private val clientViewModel: CustomerOrderViewModel by viewModels()
    private lateinit var adapter: ClientOrdersAdapter
    private lateinit var clientOrderArrayList: List<ClientOrder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle("Ordenes de clientes")

        clientViewModel.onCreate()

        clientViewModel.clientOrderModel.observe(this) {
            clientOrderArrayList = it
            initRecyclerView()
        }

        clientViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        binding.buttonCrearOrden.setOnClickListener {
            clientViewModel.navigateToCreate.postValue(true)
        }

        clientViewModel.navigateToCreate.observe(this, Observer { it ->
            if (it) {
                startActivity(ClientCustomerOrderActivity.getStartIntent(this))
            }
        })

        clientViewModel.navigateToDetail.observe(this, Observer { clientOrder ->
            val client = Client(
                clientOrder.id,
                clientOrder.clientName,
                clientOrder.shopName,
                clientOrder.city!!,
                clientOrder.calculetedDiscount!!
            )
            val products = clientOrder.details?.map {
                Product(
                    id = it.productId,
                    stock = it.quantity.toInt(),
                    stockEdited = it.quantity.toInt(),
                    salePrice = it.price,
                    buyPrice = it.price,
                    name = it.productName,
                    family = it.productFamily,
                    region = it.productRegion,
                    subfamily = it.productSubFamily,
                    size = it.productSize,
                    type = it.productFamilyType,
                    edited = true
                )
            }?.toList()
            startActivity(
                SummaryCustomerOrderActivity.getStartIntent(
                    this,
                    client, products!!, true, clientOrder
                )
            )
        })

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            clientViewModel.onCreate()
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
        val filtered = ArrayList<ClientOrder>()

        for (item in clientOrderArrayList) {
            if (item.clientName.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
                || item.shopName.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filtered.add(item)
            }
        }
        adapter.filterList(filtered)
    }

    private fun initRecyclerView() {
        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        adapter = ClientOrdersAdapter(clientOrderArrayList) { clientViewModel.onItemSelected(it) }
        binding.recyclerOrders.adapter = adapter
        val emptyDataObserver =
            EmptyDataObserver(binding.recyclerOrders, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun onItemSelected(clientOrder: ClientOrder) {
        //Go to products
        //clientViewModel.
        clientViewModel.navigateToDetail.postValue(clientOrder)
    }
}