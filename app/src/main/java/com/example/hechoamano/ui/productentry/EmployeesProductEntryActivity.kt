package com.example.hechoamano.ui.productentry

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hechoamano.R
import com.example.hechoamano.databinding.ActivityEmployeesProductEntryBinding
import com.example.hechoamano.domain.model.Employee
import com.example.hechoamano.ui.base.BaseActionBarActivity
import com.example.hechoamano.ui.productentry.adapter.EmployeesAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class EmployeesProductEntryActivity : BaseActionBarActivity() {

    private lateinit var binding: ActivityEmployeesProductEntryBinding

    private val employeeViewModel: EmployeesProductEntryViewModel by viewModels()
    private lateinit var adapter: EmployeesAdapter
    private lateinit var employeeArrayList: List<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmployeesProductEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarTitle("Empleados")

        employeeViewModel.onCreate()

        employeeViewModel.employeeModel.observe(this) {
            employeeArrayList = it
            initRecyclerView()
        }

        employeeViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        employeeViewModel.navigateToProducts.observe(this, Observer { employee ->
            startActivity(ProductsProductEntryActivity.getStartIntent(this, employee))
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
        val filtered = ArrayList<Employee>()

        for (item in employeeArrayList) {
            if (item.name.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filtered.add(item)
            }
        }
        adapter.filterList(filtered)
    }

    private fun initRecyclerView() {
        binding.recyclerEmployees.layoutManager = LinearLayoutManager(this)
        adapter = EmployeesAdapter(employeeArrayList) { onItemSelected(it) }
        binding.recyclerEmployees.adapter = adapter
        val emptyDataObserver =
            EmptyDataObserver(binding.recyclerEmployees, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun onItemSelected(employee: Employee) {
        //Go to products
        employeeViewModel.navigateToProducts.postValue(employee)
    }
}