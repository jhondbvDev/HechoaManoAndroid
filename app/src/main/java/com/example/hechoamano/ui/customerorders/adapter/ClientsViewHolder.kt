package com.example.hechoamano.ui.customerorders.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemClientListBinding
import com.example.hechoamano.domain.model.Client

class ClientsViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val binding = ItemClientListBinding.bind(inflater)
    fun render(client: Client, onClickListener: (Client) -> Unit) {
        binding.name.text = client.name
        binding.shopName.text = client.shopName
        binding.city.text = client.city
        itemView.setOnClickListener { onClickListener(client) }
    }
}