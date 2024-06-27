package com.example.hechoamano.ui.stockcontrol.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.InventoryControl

class InventoryControlAdapter(private  var employeeOrders: List<InventoryControl>, private val onClickListener: (InventoryControl) -> Unit)
    : RecyclerView.Adapter<InventoryControlViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryControlViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InventoryControlViewHolder(layoutInflater.inflate(R.layout.item_client_list, parent, false))
    }

    override fun onBindViewHolder(holder: InventoryControlViewHolder, position: Int) {
        val item = employeeOrders[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = employeeOrders.size

    fun filterList(filterList: List<InventoryControl>) {
        employeeOrders = filterList
        notifyDataSetChanged()
    }
}