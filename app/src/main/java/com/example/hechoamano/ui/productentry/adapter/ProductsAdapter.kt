package com.example.hechoamano.ui.productentry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.Product

class ProductsAdapter (private  var products: List<Product>)
    : RecyclerView.Adapter<ProductsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(layoutInflater.inflate(R.layout.item_product_list, parent, false))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = products[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = products.size

    fun filterList(filterList: List<Product>) {
        products = filterList
        notifyDataSetChanged()
    }
}