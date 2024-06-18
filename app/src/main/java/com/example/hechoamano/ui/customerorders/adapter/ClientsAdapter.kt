package com.example.hechoamano.ui.customerorders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.R
import com.example.hechoamano.domain.model.Client

class ClientsAdapter(private  var clients: List<Client>, private val onClickListener: (Client) -> Unit)
    : RecyclerView.Adapter<ClientsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClientsViewHolder(layoutInflater.inflate(R.layout.item_client_list, parent, false))
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val item = clients[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = clients.size

    fun filterList(filterList: List<Client>) {
        clients = filterList
        notifyDataSetChanged()
    }
}