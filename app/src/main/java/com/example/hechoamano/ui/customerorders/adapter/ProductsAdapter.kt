package com.example.hechoamano.ui.customerorders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.Product

class ProductsAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductsViewHolder>() {

    private var remove = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(layoutInflater.inflate(R.layout.item_product_list, parent, false))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = products[position]
        holder.render(item, remove)
    }

    override fun getItemCount(): Int = products.size

    fun filterList(filterList: List<Product>) {
        products = filterList
        notifyDataSetChanged()
    }

    fun activeRemove(remove: Boolean) {
        this.remove = remove
    }
}