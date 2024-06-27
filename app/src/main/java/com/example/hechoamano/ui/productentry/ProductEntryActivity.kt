package com.example.hechoamano.ui.productentry

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityClientCustomerOrderBinding
import com.example.hechoamano.databinding.ActivityProductEntryBinding
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.domain.model.ClientOrder
import com.example.hechoamano.domain.model.EmployeeOrder
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.base.BaseActivity
import com.example.hechoamano.ui.customerorders.ClientCustomerOrderActivity
import com.example.hechoamano.ui.customerorders.ClientCustomerOrderViewModel
import com.example.hechoamano.ui.customerorders.CustomerOrderViewModel
import com.example.hechoamano.ui.customerorders.adapter.ClientOrdersAdapter
import com.example.hechoamano.ui.customerorders.adapter.ClientsAdapter
import com.example.hechoamano.ui.productentry.adapter.EmployeeOrdersAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ProductEntryActivity : BaseActionBarActivity() {

    private lateinit var binding: ActivityProductEntryBinding

    private val employeeViewModel: ProductEntryViewModel by viewModels()
    private lateinit var adapter: EmployeeOrdersAdapter
    private lateinit var employeeOrderArrayList: List<EmployeeOrder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle("Entrada de Productos")

        employeeViewModel.onCreate()

        employeeViewModel.employeeOrderModel.observe(this) {
            employeeOrderArrayList = it
            initRecyclerView()
        }

        employeeViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        binding.buttonCrearOrden.setOnClickListener {
            employeeViewModel.navigateToCreate.postValue(true)
        }

        employeeViewModel.navigateToCreate.observe(this, Observer { it ->
            if (it) {
                val intent = Intent(this, EmployeesProductEntryActivity::class.java)
                startActivity(intent)
            }
        })

        employeeViewModel.navigateToDetail.observe(this, Observer { it ->
            //startActivity(ClientCustomerOrderActivity.getStartIntent(this))
        })

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            employeeViewModel.onCreate()
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
        val filtered = ArrayList<EmployeeOrder>()

        for (item in employeeOrderArrayList) {
            if (item.employeeName.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filtered.add(item)
            }
        }
        adapter.filterList(filtered)
    }

    private fun initRecyclerView() {
        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeOrdersAdapter(employeeOrderArrayList) { onItemSelected(it) }
        binding.recyclerOrders.adapter = adapter
        val emptyDataObserver = EmptyDataObserver(binding.recyclerOrders, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun onItemSelected(clientOrder: EmployeeOrder) {
        //Go to products
        employeeViewModel.navigateToDetail.postValue(clientOrder)
    }
}