package com.example.hechoamano.ui.stockcontrol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.Product

class SummaryProductsAdapter(private  var products: List<Product>)
    : RecyclerView.Adapter<SummaryProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SummaryProductsViewHolder(layoutInflater.inflate(R.layout.item_summary_stock_control_list, parent, false))
    }

    override fun onBindViewHolder(holder: SummaryProductsViewHolder, position: Int) {
        val item = products[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = products.size
}