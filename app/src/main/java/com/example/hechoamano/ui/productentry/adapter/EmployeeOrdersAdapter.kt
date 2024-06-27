package com.example.hechoamano.ui.productentry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.EmployeeOrder

class EmployeeOrdersAdapter(private  var employeeOrders: List<EmployeeOrder>, private val onClickListener: (EmployeeOrder) -> Unit)
    : RecyclerView.Adapter<EmployeeOrdersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeOrdersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EmployeeOrdersViewHolder(layoutInflater.inflate(R.layout.item_orden_list, parent, false))
    }

    override fun onBindViewHolder(holder: EmployeeOrdersViewHolder, position: Int) {
        val item = employeeOrders[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = employeeOrders.size

    fun filterList(filterList: List<EmployeeOrder>) {
        employeeOrders = filterList
        notifyDataSetChanged()
    }
}