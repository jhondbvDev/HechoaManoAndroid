package com.example.hechoamano.ui.stockcontrol.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemClientListBinding
import com.example.hechoamano.domain.model.InventoryControl
import java.text.NumberFormat
import java.util.Locale

class InventoryControlViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private val binding = ItemClientListBinding.bind(inflater)
    fun render(inventoryControl: InventoryControl, onClickListener: (InventoryControl) -> Unit) {
        binding.name.text = inventoryControl.employeeName
        binding.shopName.visibility = View.GONE
        binding.city.text = inventoryControl.createdDate.substring(0, 10)
        itemView.setOnClickListener { onClickListener(inventoryControl) }
    }
}