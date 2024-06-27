package com.example.hechoamano.ui.stockcontrol

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityStockControlBinding
import com.example.hechoamano.domain.model.InventoryControl
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.stockcontrol.adapter.InventoryControlAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class StockControlActivity : BaseActionBarActivity() {

    private lateinit var binding: ActivityStockControlBinding

    private val inventoryControlViewModel: StockControlViewModel by viewModels()
    private lateinit var adapter: InventoryControlAdapter
    private lateinit var inventoryControlArrayList: List<InventoryControl>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStockControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle("Historial de Inventario")

        inventoryControlViewModel.onCreate()

        inventoryControlViewModel.employeeOrderModel.observe(this) {
            inventoryControlArrayList = it
            initRecyclerView()
        }

        inventoryControlViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        binding.buttonCrearOrden.setOnClickListener {
            inventoryControlViewModel.navigateToCreate.postValue(true)
        }

        inventoryControlViewModel.navigateToCreate.observe(this, Observer { it ->
            if (it) {
                val intent = Intent(this, EmployeesStockControlActivity::class.java)
                startActivity(intent)
            }
        })

        inventoryControlViewModel.navigateToDetail.observe(this, Observer { it ->
            //startActivity(ClientCustomerOrderActivity.getStartIntent(this))
        })

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            inventoryControlViewModel.onCreate()
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
        val filtered = ArrayList<InventoryControl>()

        for (item in inventoryControlArrayList) {
            if (item.employeeName.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filtered.add(item)
            }
        }
        adapter.filterList(filtered)
    }

    private fun initRecyclerView() {
        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        adapter = InventoryControlAdapter(inventoryControlArrayList) { onItemSelected(it) }
        binding.recyclerOrders.adapter = adapter
        val emptyDataObserver = EmptyDataObserver(binding.recyclerOrders, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun onItemSelected(inventoryControl: InventoryControl) {
        //Go to products
        inventoryControlViewModel.navigateToDetail.postValue(inventoryControl)
    }
}