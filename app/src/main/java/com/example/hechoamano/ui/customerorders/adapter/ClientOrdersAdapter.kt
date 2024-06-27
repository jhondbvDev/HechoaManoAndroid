package com.example.hechoamano.ui.customerorders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.ClientOrder

class ClientOrdersAdapter(private  var clientOrders: List<ClientOrder>, private val onClickListener: (ClientOrder) -> Unit)
    : RecyclerView.Adapter<ClientOrdersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientOrdersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClientOrdersViewHolder(layoutInflater.inflate(R.layout.item_orden_list, parent, false))
    }

    override fun onBindViewHolder(holder: ClientOrdersViewHolder, position: Int) {
        val item = clientOrders[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = clientOrders.size

    fun filterList(filterList: List<ClientOrder>) {
        clientOrders = filterList
        notifyDataSetChanged()
    }
}