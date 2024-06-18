package com.example.hechoamano.ui.customerorders

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
import com.example.hechoamano.databinding.ActivityClientCustomerOrderBinding
import com.example.hechoamano.databinding.ActivityEmptyCustomerOrderBinding
import com.example.hechoamano.domain.model.Client
import com.example.hechoamano.ui.customerorders.adapter.ClientsAdapter
import com.example.hechoamano.ui.util.EmptyDataObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ClientCustomerOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientCustomerOrderBinding

    private val clientViewModel: ClientCustomerOrderViewModel by viewModels()
    private lateinit var adapter: ClientsAdapter
    private lateinit var clientArrayList: List<Client>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientCustomerOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clientViewModel.onCreate()

        clientViewModel.clientModel.observe(this) {
            clientArrayList = it
            initRecyclerView()
        }

        clientViewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

        clientViewModel.navigateToProducts.observe(this, Observer { client ->
            //startActivity(EmptyCustomerOrderActivity.getStartIntent(this, client))
        })

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            clientViewModel.onCreate()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//
//        inflater.inflate(R.menu.search_menu, menu)
//
//        val searchItem = menu.findItem(R.id.actionSearch)
//
//        val searchView = searchItem.actionView as SearchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                filter(newText)
//                return false
//            }
//        })
        return true
    }

    private fun filter(text: String) {
//        val filtered = ArrayList<Client>()
//
//        for (item in clientArrayList) {
//            if (item.name.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
//                filtered.add(item)
//            }
//        }
//        adapter.filterList(filtered)
    }

    private fun initRecyclerView() {
        binding.recyclerClients.layoutManager = LinearLayoutManager(this)
        adapter = ClientsAdapter(clientArrayList) { onItemSelected(it) }
        binding.recyclerClients.adapter = adapter
        val emptyDataObserver = EmptyDataObserver(binding.recyclerClients, findViewById<View>(R.id.emptyDataParent))
        adapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun onItemSelected(client: Client) {
        //Go to products
        clientViewModel.navigateToProducts.postValue(client)
    }
}