package com.example.hechoamano.ui.customerorders.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemOrdenListBinding
import com.example.hechoamano.domain.model.ClientOrder
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ClientOrdersViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private val binding = ItemOrdenListBinding.bind(inflater)
    fun render(clientOrder: ClientOrder, onClickListener: (ClientOrder) -> Unit) {
        binding.name.text = clientOrder.clientName
        binding.shopName.text = clientOrder.shopName
        binding.createdDate.text = clientOrder.createdDate?.substring(0, 10)
        binding.totalPrice.text = format.format(clientOrder.totalPrice)
        itemView.setOnClickListener { onClickListener(clientOrder) }
    }
}