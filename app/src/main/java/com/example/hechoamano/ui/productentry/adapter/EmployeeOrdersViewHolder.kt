package com.example.hechoamano.ui.productentry.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hechoamano.databinding.ItemOrdenListBinding
import com.example.hechoamano.domain.model.EmployeeOrder
import java.text.NumberFormat
import java.util.Locale

class EmployeeOrdersViewHolder(inflater: View) : RecyclerView.ViewHolder(inflater) {

    private val format = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private val binding = ItemOrdenListBinding.bind(inflater)
    fun render(employeeOrder: EmployeeOrder, onClickListener: (EmployeeOrder) -> Unit) {
        binding.name.text = employeeOrder.employeeName
        binding.shopName.visibility = View.GONE
        binding.createdDate.text = employeeOrder.createdDate?.substring(0, 10)
        binding.totalPrice.text = format.format(employeeOrder.totalPrice)
        itemView.setOnClickListener { onClickListener(employeeOrder) }
    }
}